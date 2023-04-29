package com.kltn.medical_consultation.services;

import com.kltn.medical_consultation.entities.database.Doctor;
import com.kltn.medical_consultation.entities.database.MedicalSchedule;
import com.kltn.medical_consultation.models.ApiException;
import com.kltn.medical_consultation.models.BasePaginationResponse;
import com.kltn.medical_consultation.models.BaseResponse;
import com.kltn.medical_consultation.models.ERROR;
import com.kltn.medical_consultation.models.doctor.DoctorMessageCode;
import com.kltn.medical_consultation.models.doctor.PatientProfileDTO;
import com.kltn.medical_consultation.models.doctor.request.DetailDoctorScheduleRequest;
import com.kltn.medical_consultation.models.doctor.request.ListDoctorScheduleRequest;
import com.kltn.medical_consultation.models.doctor.response.DetailDoctorScheduleResponse;
import com.kltn.medical_consultation.models.doctor.response.SchedulesResponse;
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
                pageable
        ).map(medicalSchedule -> {
            SchedulesResponse schedulesResponse = SchedulesResponse.of(medicalSchedule);
            return schedulesResponse;
        });
        return new BasePaginationResponse<>(doctorScheduleResponses);
    }

    public BaseResponse<DetailDoctorScheduleResponse> detailSchedule(DetailDoctorScheduleRequest request, HttpServletRequest httpServletRequest) {
        if (request.getScheduleId() == null) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("ScheduleId"));
        }

        Optional<MedicalSchedule> optionalMedicalSchedule = scheduleRepository.findById(request.getScheduleId());
        if (optionalMedicalSchedule.isEmpty()) {
            throw new ApiException(ScheduleMessageCode.SCHEDULE_NOT_FOUND);
        }

        MedicalSchedule medicalSchedule = optionalMedicalSchedule.get();
        PatientProfileDTO patientProfileDTO = PatientProfileDTO.of(medicalSchedule.getPatientProfile());

        DetailDoctorScheduleResponse response = DetailDoctorScheduleResponse.of(medicalSchedule, patientProfileDTO);
        return new BaseResponse<DetailDoctorScheduleResponse>(response);
    }

    public BaseResponse checkDone(Long scheduleId, Long userId, HttpServletRequest httpServletRequest) {
        if (scheduleId == null) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("ScheduleId"));
        }

        Optional<MedicalSchedule> optionalMedicalSchedule = scheduleRepository.findById(scheduleId);
        if (optionalMedicalSchedule.isEmpty()) {
            throw new ApiException(ScheduleMessageCode.SCHEDULE_NOT_FOUND);
        }

        MedicalSchedule medicalSchedule = optionalMedicalSchedule.get();
        if (medicalSchedule.getDoctorId() != userId) {
            throw new ApiException(ScheduleMessageCode.SCHEDULE_NOT_BELONG);
        }

        medicalSchedule.setIsDone(true);
        scheduleRepository.save(medicalSchedule);
        return new BaseResponse<>(ERROR.SUCCESS);
    }
}
