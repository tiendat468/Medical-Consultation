package com.kltn.medical_consultation.services;

import com.kltn.medical_consultation.entities.database.User;
import com.kltn.medical_consultation.models.ApiException;
import com.kltn.medical_consultation.models.ERROR;
import com.kltn.medical_consultation.models.auth.AuthMessageCode;
import com.kltn.medical_consultation.repository.database.UserRepository;
import com.kltn.medical_consultation.utils.MessageUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class UserService extends BaseService{

    @Autowired
    UserRepository userRepository;

    public void validateUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new ApiException(AuthMessageCode.AUTH_5_0_NOT_FOUND);
        }

        User user = optionalUser.get();
        if (user.getIsDelete()) {
            throw new ApiException(AuthMessageCode.AUTH_5_0_NOT_EXIST);
        }
        if (!user.getIsActive()) {
            throw new ApiException(AuthMessageCode.AUTH_5_0_INACTIVE);
        }
    }
}
