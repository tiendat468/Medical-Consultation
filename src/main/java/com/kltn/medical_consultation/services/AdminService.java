package com.kltn.medical_consultation.services;

import com.kltn.medical_consultation.entities.database.User;
import com.kltn.medical_consultation.enumeration.UserType;
import com.kltn.medical_consultation.models.ApiException;
import com.kltn.medical_consultation.models.BaseResponse;
import com.kltn.medical_consultation.models.ERROR;
import com.kltn.medical_consultation.models.admin.AdminMessageCode;
import com.kltn.medical_consultation.models.admin.request.SaveUserRequest;
import com.kltn.medical_consultation.repository.database.UserRepository;
import com.kltn.medical_consultation.utils.ICheckBCryptPasswordEncoder;
import com.kltn.medical_consultation.utils.MessageUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@Log4j2
public class AdminService extends BaseService {
    @Autowired
    private UserRepository userRepository;

    @Value("${mc.default_password:123456}")
    String defaultPassword;

    @Autowired
    ICheckBCryptPasswordEncoder passwordEncoder;


    public void listDoctors() {

    }

    public void listPatients() {

    }

    public BaseResponse saveUser(SaveUserRequest saveUserRequest, HttpServletRequest httpServletRequest) {
        if (StringUtils.isBlank(saveUserRequest.getName())) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("Name"));
        }
        if (StringUtils.isBlank(saveUserRequest.getEmail())) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("Email"));
        }

        UserType userType = UserType.from(saveUserRequest.getType());
        if (userType == null || userType.getType() == UserType.ADMIN.getType()) {
            throw new ApiException(AdminMessageCode.USER_TYPE_INVALID);
        }

        User user = new User();
        user.setName(saveUserRequest.getName());
        user.setEmail(saveUserRequest.getEmail());
        user.setType(userType.getType());
        user.setIsActive(true);
        user.setPassword(passwordEncoder.encode(defaultPassword));
        userRepository.save(user);

        return new BaseResponse<>();
    }

    public void activate() {

    }

}
