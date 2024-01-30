package com.kltn.medical_consultation.services;

import com.kltn.medical_consultation.entities.database.MedicalSchedule;
import com.kltn.medical_consultation.entities.database.Parent;
import com.kltn.medical_consultation.entities.database.Patient;
import com.kltn.medical_consultation.entities.database.PatientProfile;
import com.kltn.medical_consultation.models.*;
import com.kltn.medical_consultation.models.doctor.request.DetailDoctorScheduleRequest;
import com.kltn.medical_consultation.models.patient.response.CreatePatientResponse;
import com.kltn.medical_consultation.models.patient.response.SearchPatientResponse;
import com.kltn.medical_consultation.models.schedule.ScheduleMessageCode;
import com.kltn.medical_consultation.models.schedule.response.SchedulesResponse;
import com.kltn.medical_consultation.models.patient.*;
import com.kltn.medical_consultation.models.patient.request.*;
import com.kltn.medical_consultation.models.patient.response.PatientProfileResponse;
import com.kltn.medical_consultation.models.patient.response.PatientResponse;
import com.kltn.medical_consultation.repository.database.*;
import com.kltn.medical_consultation.utils.CustomStringUtils;
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
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class PatientService extends BaseService{
    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private MedicalScheduleRepository scheduleRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    PatientProfileRepository patientProfileRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    UserService userService;

    public BaseResponse<PatientResponse> savePatient(SavePatientRequest request, Long userId, HttpServletRequest httpServletRequest) throws ApiException{
        userService.validateUser(userId);
        if (StringUtils.isEmpty(request.getFullName())) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("FullName"));
        }

        TimeUtils.validateBirthday(request.getBirthday());

        if (StringUtils.isEmpty(request.getSex())) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("Sex"));
        }

        if (StringUtils.isEmpty(request.getAddress())) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("Address"));
        }

        if (!CustomStringUtils.isNotEmptyWithCondition(request.getAddress(), 255)) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("Address"));
        }

        if (StringUtils.isEmpty(request.getJob())) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("Job"));
        }

        if (!CustomStringUtils.isNotEmptyWithCondition(request.getJob(), 255)) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("Job"));
        }

        if (StringUtils.isEmpty(request.getIdentityNumber())) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("IdentityNumber"));
        }

        if (StringUtils.isEmpty(request.getPhoneNumber())) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("PhoneNumber"));
        }

        Patient patient;
        if (request.getId() != null) {
            Optional<Patient> optionalPatient = patientRepository.findById(request.getId());
            if (optionalPatient.isEmpty()) {
                throw new ApiException(PatientMessageCode.PATIENT_NOT_FOUND);
            }
            patient = optionalPatient.get();
        } else {
            patient = new Patient();
        }

        patient.setFullName(request.getFullName());
        patient.setBirthday(request.getBirthday());
        patient.setSex(request.getSex());
        patient.setJob(request.getJob());
        patient.setAddress(request.getAddress());
        patient.setIdentityNumber(request.getIdentityNumber());
        patient.setPhoneNumber(request.getPhoneNumber());
        patientRepository.save(patient);
        PatientResponse patientResponse = PatientResponse.of(patient);
        return new BaseResponse<>(patientResponse);
    }

    public BaseResponse createPatient(CreatePatientRequest request, Long userId, HttpServletRequest httpServletRequest) throws ApiException{
//        userService.validateUser(userId);
        if (StringUtils.isEmpty(request.getFullName())) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("FullName"));
        }

        if (StringUtils.isEmpty(request.getAddress())) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("Address"));
        }

        if (!CustomStringUtils.isNotEmptyWithCondition(request.getAddress(), 255)) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("Address"));
        }

        if (StringUtils.isEmpty(request.getPhoneNumber())) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("PhoneNumber"));
        }

        Optional<Parent> optionalParent = parentRepository.findByPhoneNumber(request.getPhoneNumber());
        Parent parent = new Parent();
        if (optionalParent.isPresent()) {
            parent = optionalParent.get();
        }
        parent.setPhoneNumber(request.getPhoneNumber());
        parent.setFullName(request.getFullName());
        parent.setAddress(request.getAddress());
        parent = parentRepository.save(parent);

        PatientDetail patientDetail = request.getPatientDetail();
        Patient patient = new Patient();
        if (patientDetail.getId() != null) {
            Optional<Patient> optionalPatient = patientRepository.findById(request.getPatientDetail().getId());
            if (optionalPatient.isPresent()) {
                patient = optionalPatient.get();
            }
        }

        patient.setFullName(patientDetail.getFullName());
        patient.setBirthday(patientDetail.getBirthday());
        patient.setSex(patientDetail.getSex());
        patient.setWeight(patientDetail.getWeight());
        patient.setParent(parent);
        patient = patientRepository.save(patient);
        CreatePatientResponse createPatientResponse = new CreatePatientResponse(parent, patient);
        return new BaseResponse<>(createPatientResponse);
    }

    public BaseResponse searchPatient(String phone) {
        if (StringUtils.isEmpty(phone)) {
//            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("PhoneNumber"));
            return new BaseResponse<>(ShareConstant.ResultMessage.PHONE_IS_MANDATORY);
        }

        Optional<Parent> optionalParent = parentRepository.findByPhoneNumber(phone);
        if (optionalParent.isEmpty()) {
//            throw new ApiException(ShareConstant.ResultMessage.PATIENT_NOT_FOUND);
            return new BaseResponse<>(ShareConstant.ResultMessage.PATIENT_NOT_FOUND);
        }
        SearchPatientResponse searchPatientResponse = new SearchPatientResponse(optionalParent.get());
        BaseResponse baseResponse = new BaseResponse<>();
        baseResponse.setData(searchPatientResponse);
        return baseResponse;
    }

//    public BasePaginationResponse<PatientProfileResponse> listProfile(Long userId, Pageable pageable, HttpServletRequest httpServletRequest) throws ApiException{
//        Optional<User> optionalUser = userRepository.findById(userId);
//        if (optionalUser.isEmpty()) {
//            throw new ApiException(ERROR.USER_NOT_FOUND);
//        }
//        User currentUser = optionalUser.get();
//
//        ListPatientProfileRequest request = new ListPatientProfileRequest();
//
//        Page<PatientProfileResponse> pageResult = patientRepository.findAll(
//                request.getSpecification(currentUser.getId()),
//                pageable).map(profile -> PatientProfileResponse.of(profile));
//        return new BasePaginationResponse<>(pageResult.getContent(), pageable.getPageNumber(), pageable.getPageSize(), pageResult.getTotalElements());
//    }

    public BaseResponse<PatientResponse> detailPatient(Long userId, HttpServletRequest httpServletRequest) throws ApiException {
        userService.validateUser(userId);
        Optional<Patient> optionalPatient = patientRepository.findByUserId(userId);
        if (optionalPatient.isEmpty()) {
            return new BaseResponse<>(PatientMessageCode.PATIENT_NOT_FOUND);
        }

        Patient patient = optionalPatient.get();
        if (patient.getIsDelete()) {
            return new BaseResponse<>(PatientMessageCode.PATIENT_NOT_EXIST);
        }

        return new BaseResponse<>(PatientResponse.of(patient));
    }

    public BaseResponse<PatientProfileResponse> createPatientProfile(CreatePatientProfileRequest request, Long userId, HttpServletRequest httpServletRequest) throws ApiException{
        userService.validateUser(userId);
        Optional<Patient> optionalPatient = patientRepository.findByUserId(userId);
        if (optionalPatient.isEmpty()) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.notFound("Patient"));
        }

        Patient patient = optionalPatient.get();
        if (patient.getIsDelete()) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.notExist("Patient"));
        }

        if (StringUtils.isBlank(request.getSymptom())) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("Symptom"));
        }

        PatientProfile patientProfile = new PatientProfile();
        patientProfile.setPatient(patient);
        patientProfile.setPatientId(patient.getId());
        patientProfile.setSymptom(request.getSymptom());
        patientProfile = patientProfileRepository.save(patientProfile);
        return new BaseResponse<>(PatientProfileResponse.of(patientProfile));
    }

    public BaseResponse<PatientProfileResponse> detailPatientProfile(DetailProfileRequest request, HttpServletRequest httpServletRequest) {
        if (request.getProfileId() == null) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("ProfileId"));
        }

        Optional<PatientProfile> optionalPatientProfile = patientProfileRepository.findById(request.getProfileId());
        if (optionalPatientProfile.isEmpty()) {
            throw new ApiException(PatientMessageCode.PATIENT_PROFILE_NOT_FOUND);
        }
        return new BaseResponse<>(PatientProfileResponse.of(optionalPatientProfile.get()));
    }

    public BaseResponse<PatientProfileResponse> updatePatientProfile(UpdateProfileRequest request, HttpServletRequest httpServletRequest) {
        if (request.getProfileId() == null) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("ProfileId"));
        }
        if (request.getDiagnostic() != null && CustomStringUtils.isLessLength(request.getDiagnostic(), 255)) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.outOfRange("Diagnostic", 255));
        }

        Optional<PatientProfile> optionalPatientProfile = patientProfileRepository.findById(request.getProfileId());
        if (optionalPatientProfile.isEmpty()) {
            throw new ApiException(PatientMessageCode.PATIENT_PROFILE_NOT_FOUND);
        }

        PatientProfile patientProfile = optionalPatientProfile.get();
        patientProfile.setDiagnostic(request.getDiagnostic());
        patientProfileRepository.save(patientProfile);
        return new BaseResponse<>(ERROR.SUCCESS);
    }

    public boolean validatePatientProfile(Long userId, PatientProfile patientProfile) {
        try {
            userService.validateUser(userId);
        }catch (Exception e) {
            return false;
        }
        Optional<Patient> optionalPatient = patientRepository.findByUserId(userId);
        if (optionalPatient.isPresent()) {
            if (patientProfile.getPatientId() == optionalPatient.get().getId()) {
                return true;
            }
        }
        return false;
    }

    public BasePaginationResponse<SchedulesResponse> getSchedules(ListPatientScheduleRequest request, Pageable pageable, HttpServletRequest httpServletRequest) {
        if (request.getPatientId() == null) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("PatientId"));
        }

        Optional<Patient> optionalPatient = patientRepository.findById(request.getPatientId());
        if (optionalPatient.isEmpty()) {
            throw new ApiException(PatientMessageCode.PATIENT_NOT_FOUND);
        }

        if (StringUtils.isNotEmpty(request.getMedicalDate())) {
            TimeUtils.validateDateFormat(request.getMedicalDate());
        }

        Page<SchedulesResponse> schedulesResponses = scheduleRepository.findAll(
                request.getSpecification(),
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), request.getSortSchedule())
        ).map(medicalSchedule -> {
            SchedulesResponse schedulesResponse = SchedulesResponse.of(medicalSchedule);
            return schedulesResponse;
        });
        return new BasePaginationResponse<>(schedulesResponses);
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

//    public BaseResponse<PatientProfileResponse> editPatientProfile(EditPatientProfileRequest request, Long userId, HttpServletRequest httpServletRequest) throws ApiException{
//        if (StringUtils.isBlank(request.getFullName())) {
//            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramInvalid("fullName"));
//        }
//
//        if (StringUtils.isBlank(request.getBirthday())) {
//            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramInvalid("birthday"));
//        }
//
//        if (StringUtils.isBlank(request.getSex())) {
//            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramInvalid("sex"));
//        }
//
//        if (StringUtils.isBlank(request.getPhoneNumber())) {
//            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramInvalid("phoneNumber"));
//        }
//
//        if (StringUtils.isBlank(request.getJob())) {
//            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramInvalid("job"));
//        }
//
//        if (StringUtils.isBlank(request.getAddress())) {
//            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramInvalid("address"));
//        }
//
//        Optional<User> optionalUser = userRepository.findById(userId);
//        if (optionalUser.isEmpty()) {
//            throw new ApiException(ERROR.USER_NOT_FOUND);
//        }
//        User currentUser = optionalUser.get();
//
//        Optional<PatientProfile> optionalProfile = patientRepository.findByIdAndUserId(request.getId(), currentUser.getId());
//        if (optionalProfile.isEmpty()) {
//            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.notFound("PatientProfile"));
//        }
//
//        PatientProfile profile = optionalProfile.get();
//        profile.setFullName(request.getFullName());
//        profile.setBirthday(request.getBirthday());
//        profile.setPhoneNumber(request.getPhoneNumber());
//        profile.setSex(request.getSex());
//        profile.setJob(request.getJob());
//        profile.setEmail(request.getEmail());
//        profile.setIdentityNumber(request.getIdentityNumber());
//        profile.setEthnic(request.getEthnic());
//        profile.setAddress(request.getAddress());
//        profile = patientRepository.save(profile);
//        return new BaseResponse<>(PatientProfileResponse.of(profile));
//    }
//
//    public BaseResponse deleteProfile(DetailProfileRequest request, Long userId, HttpServletRequest httpServletRequest) throws ApiException {
//        Optional<User> optionalUser = userRepository.findById(userId);
//        if (optionalUser.isEmpty()) {
//            throw new ApiException(ERROR.USER_NOT_FOUND);
//        }
//        User currentUser = optionalUser.get();
//
//        Optional<PatientProfile> optionalProfile = patientRepository.findByIdAndUserId(request.getId(), currentUser.getId());
//        if (optionalProfile.isEmpty()) {
//            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.notFound("PatientProfile"));
//        }
//
//        PatientProfile patientProfile = optionalProfile.get();
//        patientProfile.setIsDelete(true);
//        patientRepository.save(patientProfile);
//
//        return new BaseResponse<>(ERROR.SUCCESS.getMessage());
//    }
}
