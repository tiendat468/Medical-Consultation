package com.kltn.medical_consultation.services;

import com.kltn.medical_consultation.entities.database.*;
import com.kltn.medical_consultation.enumeration.UserType;
import com.kltn.medical_consultation.models.ApiException;
import com.kltn.medical_consultation.models.BasePaginationResponse;
import com.kltn.medical_consultation.models.BaseResponse;
import com.kltn.medical_consultation.models.ERROR;
import com.kltn.medical_consultation.models.admin.AdminMessageCode;
import com.kltn.medical_consultation.models.admin.StatsSchedule;
import com.kltn.medical_consultation.models.admin.request.*;
import com.kltn.medical_consultation.models.admin.response.DoctorResponse;
import com.kltn.medical_consultation.models.admin.response.PatientResponse;
import com.kltn.medical_consultation.models.admin.response.StatsRevenueResponse;
import com.kltn.medical_consultation.models.admin.response.StatsScheduleResponse;
import com.kltn.medical_consultation.models.department.DepartmentDTO;
import com.kltn.medical_consultation.models.department.DepartmentMessageCode;
import com.kltn.medical_consultation.models.doctor.DoctorMessageCode;
import com.kltn.medical_consultation.models.patient.PatientMessageCode;
import com.kltn.medical_consultation.repository.database.*;
import com.kltn.medical_consultation.utils.CustomStringUtils;
import com.kltn.medical_consultation.utils.ICheckBCryptPasswordEncoder;
import com.kltn.medical_consultation.utils.MessageUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class AdminService extends BaseService {
    @Autowired
    private MedicalScheduleRepository scheduleRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Value("${mc.default_password:123456}")
    String defaultPassword;
    @Autowired
    ICheckBCryptPasswordEncoder passwordEncoder;

    public BasePaginationResponse<DoctorResponse> listDoctors(ListDoctorRequest request, Pageable pageable) {
        if (request.getDepartmentId() != null) {
            Optional<Department> optionalDepartment = departmentRepository.findById(request.getDepartmentId());
            if (optionalDepartment.isEmpty()) {
                throw new ApiException(DepartmentMessageCode.DEPARTMENT_NOT_FOUND);
            }
        }

        Page<DoctorResponse> doctorResponses = doctorRepository.findAll(
                request.getSpecification(),
//                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), request.getSortDoctor())
                pageable
        ).map(doctor -> {
            DoctorResponse doctorResponse = DoctorResponse.of(doctor);
            return doctorResponse;
        });
        return new BasePaginationResponse<>(doctorResponses);
    }

    public BaseResponse<DoctorResponse> getDoctorById(Long doctorId) {
        if (doctorId == null) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("DoctorId"));
        }

        Optional<Doctor> optionalDoctor = doctorRepository.findById(doctorId);
        if (optionalDoctor.isEmpty()) {
            throw new ApiException(DoctorMessageCode.DOCTOR_NOT_FOUND);
        }
        return new BaseResponse<>(DoctorResponse.of(optionalDoctor.get()));
    }

    public BaseResponse<DoctorResponse> updateDoctor(UpdateDoctorRequest request) {
        if (request.getId() == null) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("Id"));
        }

        Optional<Doctor> optionalDoctor = doctorRepository.findById(request.getId());
        if (optionalDoctor.isEmpty()) {
            throw new ApiException(DoctorMessageCode.DOCTOR_NOT_FOUND);
        }

        Doctor doctor = optionalDoctor.get();
        doctor.setFullName(request.getName());
        doctor.setSex(request.getSex());
        doctor.setIdentityNumber(request.getIdentityNumber());
        doctor.setPhoneNumber(request.getPhoneNumber());
        doctor = doctorRepository.save(doctor);

        return new BaseResponse<>(DoctorResponse.of(doctor));
    }

    public BasePaginationResponse<PatientResponse> listPatients(ListPatientRequest request, Pageable pageable) {
        Page<PatientResponse> patientResponses = patientRepository.findAll(
                request.getSpecification(),
                pageable
        ).map(patient -> {
            PatientResponse patientResponse = PatientResponse.of(patient);
            return patientResponse;
        });
        return new BasePaginationResponse<>(patientResponses);
    }



    public BaseResponse<PatientResponse> getPatientById(Long patientId) {
        if (patientId == null) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("PatientId"));
        }

        Optional<Patient> optionalPatient = patientRepository.findById(patientId);
        if (optionalPatient.isEmpty()) {
            throw new ApiException(PatientMessageCode.PATIENT_NOT_FOUND);
        }
        return new BaseResponse<>(PatientResponse.of(optionalPatient.get()));
    }

    public BaseResponse addUser(AddUserRequest addUserRequest) {
        if (StringUtils.isBlank(addUserRequest.getName())) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("Name"));
        }
        if (StringUtils.isBlank(addUserRequest.getEmail())) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("Email"));
        }

        if (!CustomStringUtils.emailValidate(addUserRequest.getEmail())) {
            throw new ApiException(ERROR.INVALID_EMAIL, MessageUtils.wrongFormatEmail("Email"));
        }

        if (StringUtils.isNoneBlank(addUserRequest.getPhoneNumber())) {
            if (!CustomStringUtils.phoneNumberValidate(addUserRequest.getPhoneNumber())) {
                throw new ApiException(ERROR.INVALID_EMAIL, MessageUtils.wrongFormatPhoneNumber("PhoneNumber"));
            }
        }

        UserType userType = UserType.from(addUserRequest.getType());
        if (userType == null || userType.getType() == UserType.ADMIN.getType()) {
            throw new ApiException(AdminMessageCode.USER_TYPE_INVALID);
        }

        Optional<User> optionalUser = userRepository.findByEmailAndType(addUserRequest.getEmail(), userType.getType());
        if (optionalUser.isPresent()) {
            throw new ApiException(AdminMessageCode.USER_EXIST);
        }

        Department department = new Department();
        if (userType.equals(UserType.DOCTOR)) {
            if (addUserRequest.getDepartmentId() == null) {
                throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("DepartmentId"));
            }

            Optional<Department> optionalDepartment = departmentRepository.findById(addUserRequest.getDepartmentId());
            if (optionalDepartment.isEmpty()) {
                throw new ApiException(DepartmentMessageCode.DEPARTMENT_NOT_FOUND);
            }
            department = optionalDepartment.get();
        }

        User user = new User();
        user.setName(addUserRequest.getName());
        user.setEmail(addUserRequest.getEmail());
        user.setType(userType.getType());
        user.setIsActive(true);
        user.setPassword(passwordEncoder.encode(defaultPassword));
        user = userRepository.save(user);

        BaseResponse baseResponse = new BaseResponse();
        switch (userType) {
            case DOCTOR:
                Doctor doctor = new Doctor();
                doctor.setSex(addUserRequest.getSex());
                doctor.setFullName(addUserRequest.getName());
                doctor.setIdentityNumber(addUserRequest.getIdentityNumber());
                doctor.setPhoneNumber(addUserRequest.getPhoneNumber());
                doctor.setDepartment(department);
                doctor.setDepartmentId(department.getId());
                doctor.setUser(user);
                doctor.setUserId(user.getId());
                doctor = doctorRepository.save(doctor);
                DoctorResponse doctorResponse = DoctorResponse.of(doctor, user);
                baseResponse.setData(doctorResponse);
                break;
            case PATIENT:
                Patient patient = new Patient();
                patient.setSex(addUserRequest.getSex());
                patient.setFullName(addUserRequest.getName());
                patient.setIdentityNumber(addUserRequest.getIdentityNumber());
                patient.setPhoneNumber(addUserRequest.getPhoneNumber());
                patient.setUser(user);
                patient.setUserId(user.getId());
                patient = patientRepository.save(patient);
                PatientResponse patientResponse = PatientResponse.of(patient, user);
                baseResponse.setData(patientResponse);
                break;
            default:
                break;
        }
        return baseResponse;
    }

    public BaseResponse activate(ActivateUserRequest request) {
        if (request.getUserId() == null) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("UserId"));
        }

        Optional<User> optionalUser = userRepository.findById(request.getUserId());
        if (optionalUser.isEmpty()) {
            throw new ApiException(AdminMessageCode.USER_NOT_FOUND);
        }

        User user = optionalUser.get();
        user.setIsActive(request.getIsActive());
        userRepository.save(user);

        return new BaseResponse<>();
    }

    public BaseResponse<StatsRevenueResponse> statsRevenue(StatsRevenueRequest statsRevenueRequest) {
        StatsRevenueResponse statsRevenueResponse = new StatsRevenueResponse();

        if (statsRevenueRequest.getYear() == null) {
            Date currentDate = new Date();
            int currentYear = currentDate.getYear() + 1900;
            statsRevenueRequest.setYear(currentYear);
        }

        statsRevenueResponse.setYear(statsRevenueRequest.getYear());
        List<MedicalSchedule> medicalSchedules = scheduleRepository.findAll(statsRevenueRequest.getSpecification());
        for (int i = 1; i < 13; i++) {
            Double revenue = 0.0;
            for (MedicalSchedule medicalSchedule : medicalSchedules) {
                try {
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(medicalSchedule.getMedicalDate());
                    if (i == (date.getMonth() + 1)) {
                        revenue += medicalSchedule.getPrice();
                    }
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
            StatsRevenueResponse.StatsRevenue statsRevenue = new StatsRevenueResponse.StatsRevenue();
            statsRevenue.setMonth(i);
            statsRevenue.setRevenues(revenue);
            statsRevenueResponse.getStatsRevenues().add(statsRevenue);
        }

        return new BaseResponse<>(statsRevenueResponse);
    }

    public BaseResponse<StatsScheduleResponse> statsSchedule(StatsScheduleRequest request) {
        StatsScheduleResponse statsScheduleResponse = new StatsScheduleResponse();
        List<Department> departments = new ArrayList<>();
        if (request.getDepartmentId() != null ) {
            Optional<Department> optionalDepartment = departmentRepository.findById(request.getDepartmentId());
            if (optionalDepartment.isEmpty()) {
                throw new ApiException(DepartmentMessageCode.DEPARTMENT_NOT_FOUND);
            }

            departments.add(optionalDepartment.get());
        } else {
            departments = departmentRepository.findAll();
        }

        if (StringUtils.isEmpty(request.getCondition())) {
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = dateFormat.format(currentDate);
            String[] string = dateString.split("-");
            String medicalDate =  (string[0]) + "-" + (string[1]);
            request.setCondition(medicalDate);
        }
        String condition = "%" + request.getCondition() + "%";
        statsScheduleResponse.setCondition(request.getCondition());

        for (Department department : departments) {
            StatsSchedule.ScheduleRevenue scheduleRevenue = new StatsSchedule.ScheduleRevenue();
            scheduleRevenue.setTotalSchedule(scheduleRepository.countTotalSchedule(condition, department.getId()));
            scheduleRevenue.setTotalDone(scheduleRepository.countTotalScheduleDone(condition, department.getId()));
            scheduleRevenue.setTotalNotDone(scheduleRepository.countTotalScheduleInProgress(condition, department.getId()));
            scheduleRevenue.setTotalPay(scheduleRepository.countTotalSchedulePay(condition, department.getId()));
            scheduleRevenue.setTotalNotPay(scheduleRepository.countTotalScheduleInProgressPay(condition, department.getId()));

            StatsSchedule statsSchedule = new StatsSchedule();
            statsSchedule.setDepartment(new DepartmentDTO(department));
            statsSchedule.setScheduleRevenue(scheduleRevenue);
            statsScheduleResponse.getStatsSchedules().add(statsSchedule);
        }

        return new BaseResponse<>(statsScheduleResponse);
    }

}
