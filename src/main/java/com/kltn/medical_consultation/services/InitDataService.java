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

    @Value("${mc.admin_email:mc_admin@gmail.com}")
    String superEmail;

    @Value("${mc.dc_email:mc_doctor@gmail.com}")
    String doctorEmail;

    @Value("${mc.u_email:nc_user@gmail.com}")
    String userEmail;

    @Value("${mc.super_password:mc@123456}")
    String superPassword;

    @Value("${mc.super_phone:0999999999}")
    String superPhone;

    @Value("${mc.dc_phone:0999999998}")
    String dcPhone;

    @Value("${mc.user_phone:0999999997}")
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

            User doctor = new User();
            doctor.setName("DOCTOR");
            doctor.setEmail(doctorEmail);
            doctor.setPhoneNumber(superPhone);
            doctor.setType(UserType.DOCTOR.getType());
            doctor.setIsActive(Boolean.TRUE);
            doctor.setPassword(passwordEncoder.encode(superPassword));
            userRepository.save(doctor);

            User user = new User();
            user.setName("USER");
            user.setEmail(userEmail);
            user.setPhoneNumber(superPhone);
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

            department = new Department();
            department.setName("Nha Khoa");
            department.setSymbol("NK");
            department = departmentRepository.save(department);

            symptom.setName("Răng vàng");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom.setName("Hôi miệng");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom.setName("Đau răng");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom.setName("Nhức răng");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom.setName("Xưng nướu");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom.setName("Răng lung lay");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom.setName("Mọc răng khôn");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            department = new Department();
            department.setName("Da liễu");
            department.setSymbol("DL");
            department = departmentRepository.save(department);

            symptom.setName("Da khô bong tróc");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom.setName("Da dầu");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom.setName("Da nổi nốt ruồi");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom.setName("Đỏ mặt");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom.setName("Mụn");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom.setName("Vảy nến");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom.setName("Nổi mề đay");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom.setName("Da bị châm chích");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom.setName("Ngứa");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom.setName("Lở miệng");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom.setName("Nhiệt miệng");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

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
            symptom.setName("Mắt khô");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom = new Symptom();
            symptom.setName("Nhức mỏi mắt");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom = new Symptom();
            symptom.setName("Không phân biệt màu sắc");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom = new Symptom();
            symptom.setName("Lóa mắt");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom = new Symptom();
            symptom.setName("Xưng mắt");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom = new Symptom();
            symptom.setName("Phù nề vùng góc mắt");
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
            symptom.setName("Chảy máu mũi");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom = new Symptom();
            symptom.setName("Chảy máu tai");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom = new Symptom();
            symptom.setName("Đau rát tai");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom = new Symptom();
            symptom.setName("Đau rát mũi");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom = new Symptom();
            symptom.setName("Đau rát họng");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom = new Symptom();
            symptom.setName("Sổ mũi");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom = new Symptom();
            symptom.setName("Nghẹt mũi");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom = new Symptom();
            symptom.setName("Đau nhức quanh mũi");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom = new Symptom();
            symptom.setName("Đau họng");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom = new Symptom();
            symptom.setName("Ngứa họng");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom = new Symptom();
            symptom.setName("Viêm amidan");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom = new Symptom();
            symptom.setName("Ho khan");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom = new Symptom();
            symptom.setName("Ho có đờm");
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
            department.setName("Chấn thương chỉnh hình");
            department.setSymbol("CTCN");
            department = departmentRepository.save(department);

            symptom = new Symptom();
            symptom.setName("Đau nhức xương khớp");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom = new Symptom();
            symptom.setName("Đau thắt lưng");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom = new Symptom();
            symptom.setName("Bong gân");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom = new Symptom();
            symptom.setName("Gãy tay");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom = new Symptom();
            symptom.setName("Viêm khớp");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom = new Symptom();
            symptom.setName("Viêm khớp dạng thấp");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom = new Symptom();
            symptom.setName("Thoái hóa khớp");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom = new Symptom();
            symptom.setName("Chấn thương ở xương khớp");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);

            symptom = new Symptom();
            symptom.setName("Gãy chân");
            symptom.setDepartment(department);
            symptom.setDepartmentId(department.getId());
            symptomRepository.save(symptom);
        }

    }
}
