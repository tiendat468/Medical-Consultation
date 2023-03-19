package com.kltn.medical_consultation.services;

import com.kltn.medical_consultation.entities.database.Patient;
import com.kltn.medical_consultation.entities.database.PatientProfile;
import com.kltn.medical_consultation.entities.database.User;
import com.kltn.medical_consultation.models.*;
import com.kltn.medical_consultation.models.patient.*;
import com.kltn.medical_consultation.repository.database.PatientProfileRepository;
import com.kltn.medical_consultation.repository.database.PatientRepository;
import com.kltn.medical_consultation.repository.database.UserRepository;
import com.kltn.medical_consultation.utils.MessageUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
@Log4j2
public class PatientService extends BaseService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    PatientProfileRepository patientProfileRepository;

    @Autowired
    PatientRepository patientRepository;

    public BaseResponse createPatientProfile(CreatePatientProfileRequest request, Long userId, HttpServletRequest httpServletRequest) throws ApiException{
        Patient currentPatient = checkPatient(userId);
        if (currentPatient == null) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.notFound("User"));
        }

        if (StringUtils.isBlank(request.getSymptom())) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramInvalid("Symptom"));
        }

        PatientProfile patientProfile = new PatientProfile();
        patientProfile.setPatient(currentPatient);
        patientProfile.setPatientId(currentPatient.getId());
        patientProfile.setSymptom(request.getSymptom());
        patientProfile = patientProfileRepository.save(patientProfile);
        return new BaseResponse<>(ERROR.SUCCESS.getMessage());
    }

    public Patient checkPatient(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return null;
        }

        Optional<Patient> optionalPatient = patientRepository.findByUser_Id(userId);
        if (optionalPatient.isEmpty()) {
            return null;
        }

        return optionalPatient.get();
    }
//
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
//
//    public BaseResponse<PatientProfileResponse> detailProfile(DetailProfileRequest request, Long userId, HttpServletRequest httpServletRequest) throws ApiException {
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
//        return new BaseResponse<>(PatientProfileResponse.of(optionalProfile.get()));
//    }
//
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
