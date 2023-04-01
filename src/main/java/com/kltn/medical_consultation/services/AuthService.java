package com.kltn.medical_consultation.services;

import com.kltn.medical_consultation.domains.MailDomain;
import com.kltn.medical_consultation.entities.database.Patient;
import com.kltn.medical_consultation.entities.database.RegisterActivity;
import com.kltn.medical_consultation.entities.database.User;
import com.kltn.medical_consultation.entities.redis.EmailOtpDTO;
import com.kltn.medical_consultation.entities.redis.TokenDTO;
import com.kltn.medical_consultation.enumeration.UserType;
import com.kltn.medical_consultation.models.ApiException;
import com.kltn.medical_consultation.models.BaseResponse;
import com.kltn.medical_consultation.models.ERROR;
import com.kltn.medical_consultation.models.ShareConstant;
import com.kltn.medical_consultation.models.auth.AuthMessageCode;
import com.kltn.medical_consultation.models.auth.request.RegisterResendOTPRequest;
import com.kltn.medical_consultation.models.auth.request.VerifyOtpRequest;
import com.kltn.medical_consultation.models.auth.request.LoginRequest;
import com.kltn.medical_consultation.models.auth.request.RegisterRequest;
import com.kltn.medical_consultation.models.auth.response.LoginResponse;
import com.kltn.medical_consultation.models.auth.response.UserProfileResponse;
import com.kltn.medical_consultation.repository.database.PatientRepository;
import com.kltn.medical_consultation.repository.database.RegisterActivityRepository;
import com.kltn.medical_consultation.repository.database.UserRepository;
import com.kltn.medical_consultation.repository.redis.MailOtpRepository;
import com.kltn.medical_consultation.repository.redis.RedisTokenRepository;
import com.kltn.medical_consultation.utils.*;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

@Service
@Log4j2
public class AuthService extends BaseService{
    @Autowired
    private RegisterActivityRepository registerActivityRepository;
    @Autowired
    private PatientRepository patientRepository;

    @Value("${mode:}")
    private String mode;

    @Value("${server.domain:}")
    private String domain;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RedisTokenRepository tokenRepository;

    @Autowired
    MailOtpRepository mailOtpRepository;

    @Autowired
    MailDomain emailDomain;

    public BaseResponse<LoginResponse> login(LoginRequest request) {
        if (StringUtils.isBlank(request.getEmail())) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramInvalid("Email"));
        }

        if (StringUtils.isBlank(request.getPassword())) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramInvalid("Password"));
        }

        User existedUser;
        if (request.getLoginWithDoctor()) {
            existedUser = userRepository.findByEmailAndType(request.getEmail(), UserType.DOCTOR.getType()).orElse(null);
        } else {
            existedUser = userRepository.findByEmailAndType(request.getEmail(), UserType.PATIENT.getType()).orElse(null);
        }

        if (existedUser == null){
            throw new ApiException(AuthMessageCode.AUTH_5_0_NOT_FOUND);
        }

        if (existedUser.getIsDelete()) {
            throw new ApiException(AuthMessageCode.AUTH_5_0_NOT_EXIST);
        }

        if (!existedUser.getIsActive()) {
            throw new ApiException(AuthMessageCode.AUTH_5_0_INACTIVE);
        }

        if (!passwordEncoder.matches(request.getPassword(), existedUser.getPassword())){
            throw new ApiException(AuthMessageCode.AUTH_1_0);
        }

        UserType userType =  UserType.from(existedUser.getType());
        String token = UUID.randomUUID().toString();
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setToken(token);
        tokenDTO.setEmail(request.getEmail());
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

    public BaseResponse logout(HttpServletRequest httpServletRequest) throws ApiException {
        tokenRepository.deleteById(RestUtils.getTokenFromHeader(httpServletRequest));
        return new BaseResponse(AuthMessageCode.AUTH_2_1);
    }

    public BaseResponse<UserProfileResponse> getProfileUser(HttpServletRequest httpServletRequest) throws ApiException {
        TokenDTO tokenDTO = tokenRepository.findById(RestUtils.getTokenFromHeader(httpServletRequest)).get();
//        TokenDTO tokenDTO = checkExpiredToken(httpServletRequest);
//        if (tokenDTO == null) {
//            return null;
//        }
        User user = userRepository.findByEmail(tokenDTO.getEmail()).orElse(null);

        UserProfileResponse userProfileResponse = new UserProfileResponse();
        userProfileResponse.setName(user.getName());
        userProfileResponse.setEmail(user.getEmail());
        userProfileResponse.setPhone(user.getPhoneNumber());
        userProfileResponse.setType(UserType.from(user.getType()).getCode());
        return new BaseResponse<>(userProfileResponse);
    }

    public BaseResponse register(RegisterRequest request) {
        if (StringUtils.isEmpty(request.getEmail())) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramInvalid("Email"));
        }

        if (StringUtils.isEmpty(request.getFullName())) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramInvalid("FullName"));
        }

        if (StringUtils.isEmpty(request.getPhone())) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramInvalid("Phone"));
        }

        if (StringUtils.isEmpty(request.getBirthday())) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramInvalid("Birthday"));
        }

        if (StringUtils.isEmpty(request.getSex())) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramInvalid("Sex"));
        }

        if (StringUtils.isEmpty(request.getPassword())) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramInvalid("Password"));
        }

        if (!CustomStringUtils.emailValidate(request.getEmail())) {
            throw new ApiException(ERROR.INVALID_EMAIL);
        }

        Optional<User> optionalUser = userRepository.findByEmailAndType(request.getEmail(), UserType.PATIENT.getType());
        if (optionalUser.isPresent()) {
            throw new ApiException(AuthMessageCode.AUTH_5_0_EXIST);
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getFullName());
        user.setPhoneNumber(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setType(UserType.PATIENT.getType());
        user = userRepository.save(user);

        if (user != null) {
            Patient patient = new Patient();
            patient.setUser(user);
            patient.setUserId(user.getId());
            patient.setFullName(request.getFullName());
            patient.setSex(request.getSex());
            patient.setBirthday(request.getBirthday());
            patientRepository.save(patient);
        }

        //Send verify Email
        sendVerifyEmail(user.getId(), user.getEmail());
        return new BaseResponse(AuthMessageCode.AUTH_3_1);
    }

    public BaseResponse verifyOTP(VerifyOtpRequest request) {
        if (StringUtils.isEmpty(request.getVerifyCode())) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramInvalid("Email"));
        }

        if (StringUtils.isEmpty(request.getOtp())) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramInvalid("OTP"));
        }

        Optional<EmailOtpDTO> optionalMailOtpDTO = mailOtpRepository.findById(request.getVerifyCode());
        if (optionalMailOtpDTO.isEmpty()) {
            throw new ApiException(AuthMessageCode.AUTH_3_0_OTP_EXPIRED);
        }

        EmailOtpDTO emailOtpDTO = optionalMailOtpDTO.get();
        if (!request.getOtp().equals(emailOtpDTO.getOtp())) {
            throw new ApiException(AuthMessageCode.AUTH_3_0_OTP);
        }

        Optional<User> optionalUser = userRepository.findByEmail(emailOtpDTO.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setIsActive(true);
            userRepository.save(user);
        }

        mailOtpRepository.delete(emailOtpDTO);
        return new BaseResponse(AuthMessageCode.AUTH_3_1_OTP);
    }

    public BaseResponse resendOTP(RegisterResendOTPRequest request) {
        //Send OTP to Email
        EmailOtpDTO emailOtpDTO = generateOTP(request.getEmail());
        emailDomain.sendOtpEmail(emailOtpDTO);
        return new BaseResponse(ERROR.SUCCESS);
    }

    public EmailOtpDTO generateOTP(String email) {
        String token  = UUID.randomUUID().toString();
        String otp = StringGenerator.randomDigits(ShareConstant.OTP.LENGTH);
//        if (mode.equalsIgnoreCase("DEVELOP")){
//            otp = "9999";
//        }

        EmailOtpDTO emailOtpDTO = new EmailOtpDTO();
        emailOtpDTO.setToken(token);
        emailOtpDTO.setEmail(email);
        emailOtpDTO.setOtp(otp);
        emailOtpDTO.setExpiredAt(15L);
        mailOtpRepository.save(emailOtpDTO);
        return emailOtpDTO;
    }

    public void sendVerifyEmail(Long userId, String email) {
        String code = UUID.randomUUID().toString();
        RegisterActivity registerActivity = new RegisterActivity();
        registerActivity.setUserId(userId);
        registerActivity.setEmail(email);
        registerActivity.setCode(code);
        registerActivityRepository.save(registerActivity);

        String verifyLink = domain + "/auth/verify-email?code=" + code + "&email=" + email;
        emailDomain.sendVerifyEmail(email, verifyLink);
    }

    public Boolean verifyEmail(String code) {
        Optional<RegisterActivity> registerActivity = registerActivityRepository.findAllByCode(code);
        if (registerActivity.isPresent()) {
            Optional<User> optionalUser = userRepository.findByEmailAndType(registerActivity.get().getEmail(), UserType.PATIENT.getType());
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                user.setIsActive(true);
                userRepository.save(user);
                registerActivityRepository.delete(registerActivity.get());
                return true;
            }
        }
        return false;
    }
}
