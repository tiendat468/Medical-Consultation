package com.kltn.medical_consultation.services;

import com.kltn.medical_consultation.entities.database.*;
import com.kltn.medical_consultation.models.ApiException;
import com.kltn.medical_consultation.models.BasePaginationResponse;
import com.kltn.medical_consultation.models.BaseResponse;
import com.kltn.medical_consultation.models.ERROR;
import com.kltn.medical_consultation.models.doctor.DoctorMessageCode;
import com.kltn.medical_consultation.models.doctor.MedicineDTO;
import com.kltn.medical_consultation.models.doctor.request.SaveProfileRequest;
import com.kltn.medical_consultation.models.doctor.request.MedicalPatientRequest;
import com.kltn.medical_consultation.models.doctor.response.DoctorProfileResponse;
import com.kltn.medical_consultation.models.doctor.request.DetailDoctorScheduleRequest;
import com.kltn.medical_consultation.models.doctor.request.ListDoctorScheduleRequest;
import com.kltn.medical_consultation.models.patient.PatientMessageCode;
import com.kltn.medical_consultation.models.schedule.response.SchedulesResponse;
import com.kltn.medical_consultation.models.schedule.ScheduleMessageCode;
import com.kltn.medical_consultation.repository.database.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class DoctorService extends BaseService{
    @Autowired
    private ProfileMedicineRepository profileMedicineRepository;
    @Autowired
    private MedicineRepository medicineRepository;
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

    public BaseResponse medicalPatient(MedicalPatientRequest request, HttpServletRequest httpServletRequest) {
        if (request.getPatientId() == null) {
            return new BaseResponse<>(PatientMessageCode.PATIENT_ID_IS_MANDATORY);
        }

        Optional<Patient> optionalPatient = patientRepository.findByIdAndIsDeleteFalse(request.getPatientId());
        if (optionalPatient.isEmpty()) {
            return new BaseResponse<>(PatientMessageCode.PATIENT_NOT_FOUND);
        }

        Patient patient = optionalPatient.get();
        PatientProfile patientProfile = new PatientProfile();
        patientProfile.setPatient(patient);

        patientProfile.setAnamnesis(request.getAnamnesis());
        patientProfile.setDiagnostic(request.getDiagnostic());
        patientProfile.setSymptom(request.getSymptom());
        patientProfile.setAdvice(request.getAdvice());
        patientProfile = patientProfileRepository.save(patientProfile);

        List<MedicineDTO> medicines = request.getMedicines();
        List<ProfileMedicine> profileMedicines = new ArrayList<>();
        for (MedicineDTO medicine : medicines) {
            Optional<Medicine> optionalMedicine = medicineRepository.findByIdAndIsDeleteFalse(medicine.getId());
            if (optionalMedicine.isPresent()) {
                ProfileMedicine profileMedicine = new ProfileMedicine();
                profileMedicine.setPatientProfile(patientProfile);
                profileMedicine.setMedicine(optionalMedicine.get());
                profileMedicine.setUnit(optionalMedicine.get().getUnit());
                profileMedicine.setQuantity(medicine.getQuantity());
                profileMedicine.setInstruction(medicine.getInstruction());
                profileMedicines.add(profileMedicine);
            }
        }
        profileMedicineRepository.saveAll(profileMedicines);
        return new BaseResponse<>();
    }
}
