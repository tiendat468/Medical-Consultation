package com.kltn.medical_consultation.services;

import com.kltn.medical_consultation.entities.database.User;
import com.kltn.medical_consultation.enumeration.UserType;
import com.kltn.medical_consultation.repository.database.UserRepository;
import com.kltn.medical_consultation.utils.ICheckBCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class InitDataService implements CommandLineRunner {

    @Value("${mc.admin_email:tangtiendat911@gmail.com}")
    String superEmail;

    @Value("${mc.admin_password:mc@123456}")
    String superPassword;

    @Value("${mc.admin_phone:0123456789}")
    String superPhone;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ICheckBCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args){
        initAdmin();
    }

    @Transactional
    public void initAdmin(){
        if (userRepository.count() == 0){
            User admin = new User();
            admin.setName("ADMIN");
            admin.setEmail(superEmail);
            admin.setPhoneNumber(superPhone);
            admin.setType(UserType.ADMIN.getType());
            admin.setActive(Boolean.TRUE);
            admin.setPassword(passwordEncoder.encode(superPassword));
            userRepository.save(admin);
        }
    }
}
