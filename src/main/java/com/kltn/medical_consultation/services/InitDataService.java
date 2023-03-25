package com.kltn.medical_consultation.services;

import com.kltn.medical_consultation.entities.database.Department;
import com.kltn.medical_consultation.entities.database.Symptom;
import com.kltn.medical_consultation.entities.database.User;
import com.kltn.medical_consultation.enumeration.UserType;
import com.kltn.medical_consultation.repository.database.DepartmentRepository;
import com.kltn.medical_consultation.repository.database.SymptomRepository;
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

    @Value("${mc.admin_email:tangtiendat@gmail.com}")
    String userEmail;

    @Value("${mc.admin_password:mc@123456}")
    String superPassword;

    @Value("${mc.admin_phone:0123456789}")
    String superPhone;

    @Value("${mc.admin_phone:0123456799}")
    String userPhone;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ICheckBCryptPasswordEncoder passwordEncoder;

    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    SymptomRepository symptomRepository;

    @Override
    public void run(String... args){
        initUser();
        initDepartment();
    }

    @Transactional
    public void initUser(){
        if (userRepository.count() == 0){
            User admin = new User();
            admin.setName("ADMIN");
            admin.setEmail(superEmail);
            admin.setPhoneNumber(superPhone);
            admin.setType(UserType.ADMIN.getType());
            admin.setIsActive(Boolean.TRUE);
            admin.setPassword(passwordEncoder.encode(superPassword));
            userRepository.save(admin);

            User user = new User();
            user.setName("USER");
            user.setEmail(userEmail);
            user.setPhoneNumber(userPhone);
            user.setType(UserType.PATIENT.getType());
            user.setIsActive(Boolean.TRUE);
            user.setPassword(passwordEncoder.encode(superPassword));
            userRepository.save(user);
        }
    }

    public void initDepartment(){
        if (departmentRepository.count() == 0) {
            Symptom symptom = new Symptom();
            Department department = new Department();

            department.setName("Tổng quát");
            department.setSymbol("TQ");
            department = departmentRepository.save(department);

            department = new Department();
            department.setName("Mắt");
            department.setSymbol("MAT");
            department = departmentRepository.save(department);

            symptom.setName("Đau mắt đỏ");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom = new Symptom();
            symptom.setName("Mắt mờ");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom = new Symptom();
            symptom.setName("Trấn thương mắt");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            department = new Department();
            department.setName("Tai mũi họng");
            department.setSymbol("TMH");
            department = departmentRepository.save(department);

            symptom = new Symptom();
            symptom.setName("Ù tai");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom = new Symptom();
            symptom.setName("Chảy máu mũi, tai");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom = new Symptom();
            symptom.setName(" Đau rát tai, mũi, họng");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom = new Symptom();
            symptom.setName("Các trấn thương liên quan");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            department = new Department();
            department.setName("Tiêu hóa");
            department.setSymbol("TH");
            department = departmentRepository.save(department);

            symptom = new Symptom();
            symptom.setName("Đau bụng");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom = new Symptom();
            symptom.setName("Chướng bụng");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom = new Symptom();
            symptom.setName("Đầy hơi");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom = new Symptom();
            symptom.setName("Đại tiện bất thường");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom = new Symptom();
            symptom.setName("Buồn nôn, ói");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom = new Symptom();
            symptom.setName("Khó tiêu");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom = new Symptom();
            symptom.setName("Giảm cân không rõ nguyên nhân");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            department = new Department();
            department.setName("Tiết niệu");
            department.setSymbol("TN");
            department = departmentRepository.save(department);

            symptom = new Symptom();
            symptom.setName("Đi tiểu ra máu, mủ, màu đục");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom = new Symptom();
            symptom.setName("Đi tiểu có cảm giác đau buốt");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom = new Symptom();
            symptom.setName("Đau buốt khi quan hệ");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom = new Symptom();
            symptom.setName("Đau lưng");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom = new Symptom();
            symptom.setName("Sốt cao");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom = new Symptom();
            symptom.setName("Nôn ói");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom = new Symptom();
            symptom.setName("Dau vùng hạ sườn");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom = new Symptom();
            symptom.setName("Mệt mỏi");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);
        }

    }
}
