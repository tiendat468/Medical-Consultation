package com.kltn.medical_consultation.services;

import com.kltn.medical_consultation.entities.database.*;
import com.kltn.medical_consultation.models.ApiException;
import com.kltn.medical_consultation.models.BaseResponse;
import com.kltn.medical_consultation.models.ERROR;
import com.kltn.medical_consultation.models.department.DepartmentMessageCode;
import com.kltn.medical_consultation.models.department.response.DepartmentPercent;
import com.kltn.medical_consultation.models.patient.PatientMessageCode;
import com.kltn.medical_consultation.models.schedule.ListFreeSchedule;
import com.kltn.medical_consultation.models.schedule.ScheduleMessageCode;
import com.kltn.medical_consultation.models.schedule.request.DetailScheduleRequest;
import com.kltn.medical_consultation.models.schedule.request.SaveScheduleRequest;
import com.kltn.medical_consultation.models.schedule.response.DetailScheduleResponse;
import com.kltn.medical_consultation.repository.database.*;
import com.kltn.medical_consultation.utils.MessageUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
@Log4j2
public class MedicalScheduleService extends BaseService{
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    PatientProfileRepository patientProfileRepository;
    @Autowired
    MedicalScheduleRepository scheduleRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    UserService userService;

    @Transactional
    public BaseResponse<DetailScheduleResponse> save(SaveScheduleRequest request, Long userId, HttpServletRequest httpServletRequest) {
        userService.validateUser(userId);
        if (request.getDepartmentId() == null) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("departmentId"));
        }
        if (request.getDoctorId() == null) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("doctorId"));
        }
        if (StringUtils.isEmpty(request.getSymptom())) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("symptom"));
        }
        if (StringUtils.isEmpty(request.getMedicalDate())) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("medicalDate"));
        }
        if (StringUtils.isEmpty(request.getHours())) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("hours"));
        }

        Optional<Department> optionalDepartment = departmentRepository.findById(request.getDepartmentId());
        if (optionalDepartment.isEmpty()) {
            throw new ApiException(DepartmentMessageCode.DEPARTMENT_NOT_FOUND);
        }
        Department department = optionalDepartment.get();

        Optional<Doctor> optionalDoctor = doctorRepository.findById(request.getDoctorId());
        if (optionalDoctor.isEmpty()) {
            throw new ApiException(DepartmentMessageCode.DOCTOR_NOT_FOUND);
        }
        Doctor doctor = optionalDoctor.get();

        if (doctor.getDepartmentId() != department.getId()) {
            throw new ApiException(DepartmentMessageCode.DOCTOR_NOT_BELONG);
        }

        if (!checkScheduleDoctor(doctor.getId(), request.getMedicalDate(), request.getHours())) {
            throw new ApiException(DepartmentMessageCode.DOCTOR_BUSY);
        }

        Patient patient = userService.fetchPatient(userId);
        if (patient == null) {
            throw new ApiException(PatientMessageCode.PATIENT_NOT_FOUND);
        }

        // create patient profile
        PatientProfile patientProfile = new PatientProfile();
        patientProfile.setPatient(patient);
        patientProfile.setPatientId(patient.getId());
        patientProfile.setSymptom(request.getSymptom());
        patientProfile = patientProfileRepository.save(patientProfile);

        // create patient profile
        MedicalSchedule medicalSchedule = new MedicalSchedule();
        if (patientProfile != null) {
            medicalSchedule.setMedicalDate(request.getMedicalDate());
            medicalSchedule.setHours(request.getHours());
            medicalSchedule.setDoctor(doctor);
            medicalSchedule.setDoctorId(doctor.getId());
            medicalSchedule.setPatientProfile(patientProfile);
            medicalSchedule.setPatientProfileId(patientProfile.getId());
            medicalSchedule.setPrice(department.getPrice());
            medicalSchedule = scheduleRepository.save(medicalSchedule);
        }

        DetailScheduleResponse detailScheduleResponse = DetailScheduleResponse.of(medicalSchedule, department);
        return new BaseResponse<>(detailScheduleResponse);
    }

    public BaseResponse<DetailScheduleResponse> detail(DetailScheduleRequest request, Long userId, HttpServletRequest httpServletRequest) {
        if (request.getPatientProfileId() == null) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("patientProfileId"));
        }

        if (request.getScheduleId() == null) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("scheduleId"));
        }

        Optional<PatientProfile> optionalPatientProfile = patientProfileRepository.findById(request.getPatientProfileId());
        if (optionalPatientProfile.isEmpty()) {
            throw new ApiException(PatientMessageCode.PATIENT_PROFILE_NOT_FOUND);
        }

        PatientProfile patientProfile = optionalPatientProfile.get();
        if (patientProfile.getPatient().getUserId() != userId) {
            throw new ApiException(PatientMessageCode.PATIENT_PROFILE_INVALID);
        }

        Optional<MedicalSchedule> optionalSchedule = scheduleRepository.findById(request.getScheduleId());
        if (optionalSchedule.isEmpty()) {
            throw new ApiException(ScheduleMessageCode.SCHEDULE_NOT_FOUND);
        }

        MedicalSchedule schedule = optionalSchedule.get();
        if (schedule.getPatientProfileId() != patientProfile.getId()) {
            throw new ApiException(ScheduleMessageCode.SCHEDULE_NOT_FOUND);
        }

        DetailScheduleResponse detailScheduleResponse = DetailScheduleResponse.of(schedule);
        return new BaseResponse<>(detailScheduleResponse);
    }

    public ListFreeSchedule fetchSchedule(List<DepartmentPercent> departmentPercents, String scheduleDate) {
        ListFreeSchedule listFreeSchedule = new ListFreeSchedule();
        Department department = findDepartmentBySymbol(departmentPercents.get(0).getSymbol());
        List<ListFreeSchedule.DetailSchedule> detailScheduleList = listFreeSchedule.getDetailSchedules();

        int count = 0;
        List<Doctor> doctorList = doctorRepository.findByDepartmentId(department.getId());
        for (Doctor doctor: doctorList) {
            List<MedicalSchedule> scheduleList = findScheduleByDoctorAndMedicalDate(doctor.getId(), scheduleDate);
            for (ListFreeSchedule.DetailSchedule schedule : detailScheduleList) {
                if(schedule.getDoctorId() == null) {
                    if (scheduleList.size() > 0) {
                        for (MedicalSchedule medicalSchedule : scheduleList) {
                            if (medicalSchedule.getHours().equalsIgnoreCase(schedule.getScheduleTime())) {
                                break;
                            }
                            schedule.setDoctorId(doctor.getId());
                            schedule.setPrice(department.getPrice());
                            count++;
                        }
                    } else {
                        schedule.setDoctorId(doctor.getId());
                        schedule.setPrice(department.getPrice());
                        count++;
                    }
                }
            }
            if(count == 4){
                break;
            }
        }

        listFreeSchedule.setDepartmentId(department.getId());
        listFreeSchedule.setDepartmentName(department.getName());
        listFreeSchedule.setDetailSchedules(detailScheduleList);
        return listFreeSchedule;
    }

    public Department findDepartmentBySymbol(String symbol) {
        Optional<Department> optionalDepartment = departmentRepository.findBySymbol(symbol);
        if (optionalDepartment.isPresent()) {
            return optionalDepartment.get();
        }
        return null;
    }

    public boolean checkScheduleDoctor(Long doctorId, String medicalDate, String hours) {
        List<MedicalSchedule> scheduleList = findScheduleByDoctorAndMedicalDate(doctorId, medicalDate);
        if (scheduleList.isEmpty()) {
            return true;
        } else {
            for (MedicalSchedule medicalSchedule : scheduleList) {
                if (medicalSchedule.getHours().equalsIgnoreCase(hours)) {
                    return false;
                }
            }
        }

        return true;
    }

    public List<MedicalSchedule> findScheduleByDoctorAndMedicalDate(Long doctorId, String medicalDate) {
        List<MedicalSchedule> scheduleList = scheduleRepository.findByDoctorIdAndMedicalDate(doctorId, medicalDate);
        if (scheduleList.isEmpty()) {
            return new ArrayList<>();
        }
        return scheduleList;
    }

    public boolean checkPatientProfile(Long patientProfileId) {
        List<MedicalSchedule> scheduleList = scheduleRepository.findByPatientProfileId(patientProfileId);
        if (scheduleList.isEmpty()) {
            return true;
        }
        return false;
    }
}
