package com.kltn.medical_consultation.services;

import com.kltn.medical_consultation.entities.database.*;
import com.kltn.medical_consultation.enumeration.UserType;
import com.kltn.medical_consultation.models.ShareConstant;
import com.kltn.medical_consultation.repository.database.*;
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

    @Value("${mc.u_email:mc_user@gmail.com}")
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
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    PatientRepository patientRepository;

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
            admin.setType(UserType.ADMIN.getType());
            admin.setIsActive(Boolean.TRUE);
            admin.setPassword(passwordEncoder.encode(superPassword));
            userRepository.save(admin);

            User user = new User();
            user.setName("USER");
            user.setEmail(userEmail);
            user.setType(UserType.PATIENT.getType());
            user.setIsActive(Boolean.TRUE);
            user.setPassword(passwordEncoder.encode(superPassword));
            user = userRepository.save(user);

            Patient patient = new Patient();
            patient.setUser(user);
            patient.setUserId(user.getId());
            patient.setPhoneNumber(superPhone);
            patient.setFullName("USER");
            patient.setSex(ShareConstant.SEX.MALE);
            patient.setBirthday("2000-01-01");
            patientRepository.save(patient);
        }
    }

    @Transactional
    public void initDepartment(){
        if (departmentRepository.count() == 0) {
            Symptom symptom = new Symptom();
            Department department = new Department();

            department = new Department();
            department.setName("Nhi");
            department.setSymbol("NHI");
            department.setPrice(200000.0);
            department = departmentRepository.save(department);

            initDoctor(department);

//            department = new Department();
//            department.setName("Nha Khoa");
//            department.setSymbol("NK");
//            department.setPrice(200000.0);
//            department = departmentRepository.save(department);
//
//            initDoctor(department);

//            symptom = new Symptom();
//            symptom.setName("Răng vàng");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Hôi miệng");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Đau răng");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Nhức răng");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Xưng nướu");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Răng lung lay");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Mọc răng khôn");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            department = new Department();
//            department.setName("Da liễu");
//            department.setSymbol("DL");
//            department.setPrice(200000.0);
//            department = departmentRepository.save(department);
//
//            initDoctor(department);

//            symptom = new Symptom();
//            symptom.setName("Da khô bong tróc");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Da dầu");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Da nổi nốt ruồi");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Đỏ mặt");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Mụn");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Vảy nến");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Nổi mề đay");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Da bị châm chích");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Ngứa");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Lở miệng");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Nhiệt miệng");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            department = new Department();
//            department.setName("Mắt");
//            department.setSymbol("MAT");
//            department.setPrice(200000.0);
//            department = departmentRepository.save(department);
//
//            initDoctor(department);

//            symptom = new Symptom();
//            symptom.setName("Đau mắt đỏ");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Mắt mờ");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Mắt khô");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Nhức mỏi mắt");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Không phân biệt màu sắc");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Lóa mắt");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Xưng mắt");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Phù nề vùng góc mắt");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            department = new Department();
//            department.setName("Tai mũi họng");
//            department.setSymbol("TMH");
//            department.setPrice(200000.0);
//            department = departmentRepository.save(department);
//
//            initDoctor(department);

//            symptom = new Symptom();
//            symptom.setName("Ù tai");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Chảy máu mũi");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Chảy máu tai");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Đau rát tai");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Đau rát mũi");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Đau rát họng");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Sổ mũi");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Nghẹt mũi");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Đau nhức quanh mũi");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Đau họng");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Ngứa họng");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Viêm amidan");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Ho khan");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Ho có đờm");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            department = new Department();
//            department.setName("Tiêu hóa");
//            department.setSymbol("TH");
//            department.setPrice(200000.0);
//            department = departmentRepository.save(department);
//
//            initDoctor(department);

//            symptom = new Symptom();
//            symptom.setName("Đau bụng");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Chướng bụng");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Đầy hơi");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Đại tiện bất thường");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Buồn nôn, ói");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Khó tiêu");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Giảm cân không rõ nguyên nhân");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            department = new Department();
//            department.setName("Chấn thương chỉnh hình");
//            department.setSymbol("CTCN");
//            department.setPrice(200000.0);
//            department = departmentRepository.save(department);
//
//            initDoctor(department);

//            symptom = new Symptom();
//            symptom.setName("Đau nhức xương khớp");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Đau thắt lưng");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Bong gân");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Gãy tay");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Viêm khớp");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Viêm khớp dạng thấp");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Thoái hóa khớp");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Chấn thương ở xương khớp");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);

//            symptom = new Symptom();
//            symptom.setName("Gãy chân");
//            symptom.setDepartment(department);
//            symptom.setDepartmentId(department.getId());
//            symptomRepository.save(symptom);
        }
    }

    @Transactional
    public void initDoctor(Department department){
        User doctor1 = new User();
        doctor1.setName("DOCTOR 1" + department.getSymbol());
        String e1 = "doctor1_" + department.getSymbol() + "@gmail.com";
        doctor1.setEmail(e1);
        doctor1.setType(UserType.DOCTOR.getType());
        doctor1.setIsActive(Boolean.TRUE);
        doctor1.setPassword(passwordEncoder.encode(superPassword));
        doctor1 = userRepository.save(doctor1);

        Doctor d1 = new Doctor();
        d1.setFullName(doctor1.getName());
        d1.setDepartment(department);
        d1.setDepartmentId(department.getId());
        d1.setSex("F");
        d1.setUser(doctor1);
        d1.setUserId(doctor1.getId());
        doctorRepository.save(d1);

        User doctor2 = new User();
        doctor2.setName("DOCTOR 2" + department.getSymbol());
        String e2 = "doctor2_" + department.getSymbol() + "@gmail.com";
        doctor2.setEmail(e2);
        doctor2.setType(UserType.DOCTOR.getType());
        doctor2.setIsActive(Boolean.TRUE);
        doctor2.setPassword(passwordEncoder.encode(superPassword));
        doctor2 = userRepository.save(doctor2);

        Doctor d2 = new Doctor();
        d2.setFullName(doctor2.getName());
        d2.setSex("M");
        d2.setDepartment(department);
        d2.setDepartmentId(department.getId());
        d2.setUser(doctor2);
        d2.setUserId(doctor2.getId());
        doctorRepository.save(d2);
    }
}
