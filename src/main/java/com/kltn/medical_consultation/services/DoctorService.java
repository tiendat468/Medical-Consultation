package com.kltn.medical_consultation.services;

import com.kltn.medical_consultation.entities.database.Doctor;
import com.kltn.medical_consultation.entities.database.MedicalSchedule;
import com.kltn.medical_consultation.entities.database.PatientProfile;
import com.kltn.medical_consultation.models.ApiException;
import com.kltn.medical_consultation.models.BasePaginationResponse;
import com.kltn.medical_consultation.models.BaseResponse;
import com.kltn.medical_consultation.models.ERROR;
import com.kltn.medical_consultation.models.doctor.DoctorMessageCode;
import com.kltn.medical_consultation.models.doctor.request.SaveProfileRequest;
import com.kltn.medical_consultation.models.doctor.request.UpdateScheduleRequest;
import com.kltn.medical_consultation.models.doctor.response.DoctorProfileResponse;
import com.kltn.medical_consultation.models.doctor.request.DetailDoctorScheduleRequest;
import com.kltn.medical_consultation.models.doctor.request.ListDoctorScheduleRequest;
import com.kltn.medical_consultation.models.schedule.response.SchedulesResponse;
import com.kltn.medical_consultation.models.schedule.ScheduleMessageCode;
import com.kltn.medical_consultation.repository.database.DoctorRepository;
import com.kltn.medical_consultation.repository.database.MedicalScheduleRepository;
import com.kltn.medical_consultation.repository.database.PatientProfileRepository;
import com.kltn.medical_consultation.repository.database.PatientRepository;
import com.kltn.medical_consultation.utils.MessageUtils;
import com.kltn.medical_consultation.utils.TimeUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@Log4j2
public class DoctorService extends BaseService{
    @Autowired
    private PatientProfileRepository patientProfileRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    MedicalScheduleRepository scheduleRepository;
    @Autowired
    UserService userService;

    public BaseResponse<DoctorProfileResponse> getProfile(Long userId, HttpServletRequest httpServletRequest) throws ApiException {
        userService.validateUser(userId);
        Optional<Doctor> optionalDoctor = doctorRepository.findByUserId(userId);
        if (optionalDoctor.isEmpty()) {
            return new BaseResponse<>(DoctorMessageCode.DOCTOR_NOT_FOUND);
        }

        Doctor doctor = optionalDoctor.get();
        if (doctor.getIsDelete()) {
            return new BaseResponse<>(DoctorMessageCode.DOCTOR_EXIST);
        }

        return new BaseResponse<>(DoctorProfileResponse.of(doctor));
    }

    public BaseResponse<DoctorProfileResponse> updateProfile(SaveProfileRequest request, Long userId, HttpServletRequest httpServletRequest) throws ApiException{
        userService.validateUser(userId);
        if (request.getId() == null) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("Id"));
        }

        if (StringUtils.isEmpty(request.getFullName())) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("FullName"));
        }

        if (StringUtils.isEmpty(request.getSex())) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("Sex"));
        }

        if (StringUtils.isEmpty(request.getIdentityNumber())) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("IdentityNumber"));
        }

        if (StringUtils.isEmpty(request.getPhoneNumber())) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("PhoneNumber"));
        }

        Optional<Doctor> optionalDoctor = doctorRepository.findByUserId(userId);
        if (optionalDoctor.isEmpty()) {
            throw new ApiException(DoctorMessageCode.DOCTOR_NOT_FOUND);
        }
        Doctor doctor = optionalDoctor.get();

        doctor.setFullName(request.getFullName());
        doctor.setSex(request.getSex());
        doctor.setIdentityNumber(request.getIdentityNumber());
        doctor.setPhoneNumber(request.getPhoneNumber());
        doctor = doctorRepository.save(doctor);
        return new BaseResponse<>(DoctorProfileResponse.of(doctor));
    }

    public BasePaginationResponse<SchedulesResponse> listSchedule(ListDoctorScheduleRequest request, Pageable pageable, HttpServletRequest httpServletRequest) {
        if (request.getDoctorId() == null) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("DoctorId"));
        }

        Optional<Doctor> optionalDoctor = doctorRepository.findById(request.getDoctorId());
        if (optionalDoctor.isEmpty()) {
            throw new ApiException(DoctorMessageCode.DOCTOR_NOT_FOUND);
        }

        if (StringUtils.isNotEmpty(request.getMedicalDate())) {
            TimeUtils.validateDateFormat(request.getMedicalDate());
        }

        Page<SchedulesResponse> doctorScheduleResponses = scheduleRepository.findAll(
                request.getSpecification(),
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), request.getSortSchedule())
        ).map(medicalSchedule -> {
            SchedulesResponse schedulesResponse = SchedulesResponse.of(medicalSchedule);
            return schedulesResponse;
        });
        return new BasePaginationResponse<>(doctorScheduleResponses);
    }

    public BaseResponse<SchedulesResponse> detailSchedule(DetailDoctorScheduleRequest request, HttpServletRequest httpServletRequest) {
        if (request.getScheduleId() == null) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("ScheduleId"));
        }

        Optional<MedicalSchedule> optionalMedicalSchedule = scheduleRepository.findById(request.getScheduleId());
        if (optionalMedicalSchedule.isEmpty()) {
            throw new ApiException(ScheduleMessageCode.SCHEDULE_NOT_FOUND);
        }
        SchedulesResponse response = SchedulesResponse.of(optionalMedicalSchedule.get());
        return new BaseResponse<>(response);
    }

    public BaseResponse checkDone(UpdateScheduleRequest request, HttpServletRequest httpServletRequest) {
        if (request.getScheduleId() == null) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("ScheduleId"));
        }

        Optional<MedicalSchedule> optionalMedicalSchedule = scheduleRepository.findById(request.getScheduleId());
        if (optionalMedicalSchedule.isEmpty()) {
            throw new ApiException(ScheduleMessageCode.SCHEDULE_NOT_FOUND);
        }

        MedicalSchedule medicalSchedule = optionalMedicalSchedule.get();
        if (medicalSchedule.getPatientProfile() == null) {
            throw new ApiException(ScheduleMessageCode.SCHEDULE_INVALID);
        }

        PatientProfile patientProfile = medicalSchedule.getPatientProfile();
        patientProfile.setDiagnostic(request.getDiagnostic());
        patientProfileRepository.save(patientProfile);

        medicalSchedule.setIsDone(true);
        medicalSchedule.setIsPay(request.isPay());
        scheduleRepository.save(medicalSchedule);
        return new BaseResponse<>(ERROR.SUCCESS);
    }
}
