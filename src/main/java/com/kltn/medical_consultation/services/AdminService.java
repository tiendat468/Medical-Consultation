package com.kltn.medical_consultation.services;

import com.kltn.medical_consultation.entities.database.Department;
import com.kltn.medical_consultation.entities.database.Doctor;
import com.kltn.medical_consultation.entities.database.User;
import com.kltn.medical_consultation.enumeration.UserType;
import com.kltn.medical_consultation.models.ApiException;
import com.kltn.medical_consultation.models.BasePaginationResponse;
import com.kltn.medical_consultation.models.BaseResponse;
import com.kltn.medical_consultation.models.ERROR;
import com.kltn.medical_consultation.models.admin.AdminMessageCode;
import com.kltn.medical_consultation.models.admin.request.ListDoctorRequest;
import com.kltn.medical_consultation.models.admin.request.SaveUserRequest;
import com.kltn.medical_consultation.models.admin.response.DoctorResponse;
import com.kltn.medical_consultation.models.department.DepartmentMessageCode;
import com.kltn.medical_consultation.models.doctor.DoctorMessageCode;
import com.kltn.medical_consultation.repository.database.DepartmentRepository;
import com.kltn.medical_consultation.repository.database.DoctorRepository;
import com.kltn.medical_consultation.repository.database.UserRepository;
import com.kltn.medical_consultation.utils.ICheckBCryptPasswordEncoder;
import com.kltn.medical_consultation.utils.MessageUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@Log4j2
public class AdminService extends BaseService {
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
