package com.kltn.medical_consultation.services;

import com.kltn.medical_consultation.entities.database.User;
import com.kltn.medical_consultation.entities.redis.TokenDTO;
import com.kltn.medical_consultation.enumeration.UserType;
import com.kltn.medical_consultation.models.ApiException;
import com.kltn.medical_consultation.models.BaseResponse;
import com.kltn.medical_consultation.models.ERROR;
import com.kltn.medical_consultation.models.auth.AuthMessageCode;
import com.kltn.medical_consultation.models.auth.LoginRequest;
import com.kltn.medical_consultation.models.auth.LoginResponse;
import com.kltn.medical_consultation.models.auth.UserProfileResponse;
import com.kltn.medical_consultation.repository.database.UserRepository;
import com.kltn.medical_consultation.repository.redis.RedisTokenRepository;
import com.kltn.medical_consultation.utils.MessageUtils;
import com.kltn.medical_consultation.utils.RestUtils;
import com.kltn.medical_consultation.utils.TimeUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Component
@Log4j2
public class AuthService extends BaseService{

    @Value("${mode}")
    private String mode;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RedisTokenRepository tokenRepository;

    public BaseResponse<LoginResponse> login(LoginRequest request) {
        if (StringUtils.isBlank(request.getPhoneNumber())) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramInvalid("Phone number"));
        }

        if (StringUtils.isBlank(request.getPassword())) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramInvalid("Password"));
        }

        User existedUser = userRepository.findByPhoneNumber(request.getPhoneNumber()).orElse(null);
        if (existedUser == null){
            throw new ApiException(AuthMessageCode.AUTH_1_0);
        }

        if (!existedUser.isActive()){
            throw new ApiException(AuthMessageCode.AUTH_5_0_INACTIVE);
        }
        if (!passwordEncoder.matches(request.getPassword(), existedUser.getPassword())){
            throw new ApiException(AuthMessageCode.AUTH_1_0);
        }

        UserType userType =  UserType.from(existedUser.getType());
        String token  = UUID.randomUUID().toString();
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setToken(token);
        tokenDTO.setPhoneNumber(request.getPhoneNumber());
        tokenDTO.setUserId(existedUser.getId());

        if (userType != null) {
            tokenDTO.setType(userType.getType());
            tokenDTO.setRoleCode(userType.getCode());
        }

        long currentTime = System.currentTimeMillis();


        tokenDTO.setExpiredAt(1440L); // fix 1 request co expired 1 ngay


        tokenRepository.save(tokenDTO);

        LoginResponse response = new LoginResponse();

        response.setToken(token);
        response.setExpiredAt(TimeUtils.dateToStringSimpleDateFormat(currentTime + tokenDTO.getExpiredAt() * 60 * 1000));

        return new BaseResponse<>(AuthMessageCode.AUTH_1_1, response);
    }

    public BaseResponse logout(HttpServletRequest httpServletRequest) {
        tokenRepository.deleteById(RestUtils.getTokenFromHeader(httpServletRequest));
        return new BaseResponse(AuthMessageCode.AUTH_2_1);
    }

    public BaseResponse<UserProfileResponse> getProfileUser(HttpServletRequest httpServletRequest) {
        TokenDTO tokenDTO = tokenRepository.findById(RestUtils.getTokenFromHeader(httpServletRequest)).get();
        User user = userRepository.findByPhoneNumber(tokenDTO.getPhoneNumber()).orElse(null);

        UserProfileResponse userProfileResponse = new UserProfileResponse();
        userProfileResponse.setName(user.getName());
        userProfileResponse.setEmail(user.getEmail());
        userProfileResponse.setPhone(user.getPhoneNumber());
        userProfileResponse.setType(UserType.from(user.getType()).getCode());
        return new BaseResponse<>(userProfileResponse);
    }
}
