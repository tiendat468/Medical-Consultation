package com.kltn.medical_consultation.services;

import com.kltn.medical_consultation.entities.database.Doctor;
import com.kltn.medical_consultation.models.ApiException;
import com.kltn.medical_consultation.models.BasePaginationResponse;
import com.kltn.medical_consultation.models.ERROR;
import com.kltn.medical_consultation.models.doctor.DoctorMessageCode;
import com.kltn.medical_consultation.models.doctor.request.ListDoctorScheduleRequest;
import com.kltn.medical_consultation.models.doctor.response.DoctorScheduleResponse;
import com.kltn.medical_consultation.repository.database.DoctorRepository;
import com.kltn.medical_consultation.repository.database.MedicalScheduleRepository;
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
    private DoctorRepository doctorRepository;
    @Autowired
    MedicalScheduleRepository scheduleRepository;

    public BasePaginationResponse<DoctorScheduleResponse> listSchedule(ListDoctorScheduleRequest request, Pageable pageable, HttpServletRequest httpServletRequest) {
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

        Page<DoctorScheduleResponse> doctorScheduleResponses = scheduleRepository.findAll(
                request.getSpecification(),
                pageable
        ).map(medicalSchedule -> {
            DoctorScheduleResponse doctorScheduleResponse = DoctorScheduleResponse.of(medicalSchedule);
            return doctorScheduleResponse;
        });
        return new BasePaginationResponse<>(doctorScheduleResponses);
    }

}
