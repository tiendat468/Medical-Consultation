package com.kltn.medical_consultation.services;

import com.kltn.medical_consultation.entities.database.Patient;
import com.kltn.medical_consultation.entities.database.PatientProfile;
import com.kltn.medical_consultation.models.*;
import com.kltn.medical_consultation.models.patient.*;
import com.kltn.medical_consultation.models.patient.request.CreatePatientProfileRequest;
import com.kltn.medical_consultation.models.patient.request.DetailProfileRequest;
import com.kltn.medical_consultation.models.patient.request.SavePatientRequest;
import com.kltn.medical_consultation.models.patient.response.PatientProfileResponse;
import com.kltn.medical_consultation.models.patient.response.PatientResponse;
import com.kltn.medical_consultation.repository.database.PatientProfileRepository;
import com.kltn.medical_consultation.repository.database.PatientRepository;
import com.kltn.medical_consultation.repository.database.UserRepository;
import com.kltn.medical_consultation.utils.CustomStringUtils;
import com.kltn.medical_consultation.utils.MessageUtils;
import com.kltn.medical_consultation.utils.TimeUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@Log4j2
public class PatientService extends BaseService{
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
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramInvalid("FullName"));
        }

        TimeUtils.validateBirthday(request.getBirthday());

        if (StringUtils.isEmpty(request.getSex())) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramInvalid("Sex"));
        }

        if (StringUtils.isEmpty(request.getAddress())) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramInvalid("Address"));
        }

        if (!CustomStringUtils.isNotEmptyWithCondition(request.getAddress(), 255)) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramInvalid("Address"));
        }

        if (StringUtils.isEmpty(request.getJob())) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramInvalid("Job"));
        }

        if (!CustomStringUtils.isNotEmptyWithCondition(request.getJob(), 255)) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramInvalid("Job"));
        }

        if (StringUtils.isEmpty(request.getIdentityNumber())) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramInvalid("IdentityNumber"));
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
        patientRepository.save(patient);
        PatientResponse patientResponse = PatientResponse.of(patient);
        return new BaseResponse<>(patientResponse);
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
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramInvalid("Symptom"));
        }

        PatientProfile patientProfile = new PatientProfile();
        patientProfile.setPatient(patient);
        patientProfile.setPatientId(patient.getId());
        patientProfile.setSymptom(request.getSymptom());
        patientProfile = patientProfileRepository.save(patientProfile);
        return new BaseResponse<>(PatientProfileResponse.of(patientProfile));
    }

    public BaseResponse<PatientProfileResponse> detailPatientProfile(DetailProfileRequest request, Long userId, HttpServletRequest httpServletRequest) {
        if (request.getId() == null) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramInvalid("Id"));
        }

        Optional<PatientProfile> optionalPatientProfile = patientProfileRepository.findById(request.getId());
        if (optionalPatientProfile.isEmpty()) {
            throw new ApiException(PatientMessageCode.PATIENT_PROFILE_NOT_FOUND);
        }
        PatientProfile patientProfile = optionalPatientProfile.get();
        if (!validatePatientProfile(userId, patientProfile)) {
            throw new ApiException(PatientMessageCode.PROFILE_NOT_BELONG_PATIENT);
        }

        return new BaseResponse<>(PatientProfileResponse.of(patientProfile));
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
