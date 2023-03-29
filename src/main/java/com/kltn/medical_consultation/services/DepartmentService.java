package com.kltn.medical_consultation.services;

import com.kltn.medical_consultation.entities.database.Department;
import com.kltn.medical_consultation.entities.database.MedicalSchedule;
import com.kltn.medical_consultation.entities.database.PatientProfile;
import com.kltn.medical_consultation.entities.database.Symptom;
import com.kltn.medical_consultation.models.ApiException;
import com.kltn.medical_consultation.models.BaseResponse;
import com.kltn.medical_consultation.models.department.FreeDepartment;
import com.kltn.medical_consultation.models.patient.PatientMessageCode;
import com.kltn.medical_consultation.models.schedule.ListFreeSchedule;
import com.kltn.medical_consultation.models.schedule.request.FetchDepartmentRequest;
import com.kltn.medical_consultation.repository.database.DepartmentRepository;
import com.kltn.medical_consultation.repository.database.PatientProfileRepository;
import com.kltn.medical_consultation.repository.database.SymptomRepository;
import org.elasticsearch.client.license.LicensesStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService extends BaseService{
    @Autowired
    PatientProfileRepository patientProfileRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    SymptomRepository symptomRepository;

    @Autowired
    MedicalScheduleService scheduleService;

    public BaseResponse<FreeDepartment> fetchDepartment(FetchDepartmentRequest request, HttpServletRequest httpServletRequest) {

        Optional<PatientProfile> optionalPatientProfile = patientProfileRepository.findById(request.getPatientProfileId());
        if (optionalPatientProfile.isEmpty()) {
            throw new ApiException(PatientMessageCode.PATIENT_PROFILE_NOT_FOUND);
        }

        PatientProfile patientProfile = optionalPatientProfile.get();
        HashMap<String, Double> hashMapDepartment = new HashMap<>();
        String[] patientSymptoms = patientProfile.getSymptom().trim().split(",");
        List<Department> departments = getAllDepartments();
        for (Department department : departments) {
            int countSymptom = 0;
            double percent = 0.0;
            List<Symptom> depSymptoms = fetchSymptoms(department.getId());
            for (Symptom depSymptom : depSymptoms) {
                for (String patSymptom : patientSymptoms) {
                    if (depSymptom.getName().contains(patSymptom) || patSymptom.contains(depSymptom.getName())) {
                        countSymptom++;
                    }
                }
            }
            percent = (countSymptom / patientSymptoms.length) * 100;
            hashMapDepartment.put(department.getSymbol(), percent);
        }

//        ListFreeSchedule listFreeSchedule = scheduleService.fetchSchedule(1L, request.getScheduleDate())

        return null;
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public List<Symptom> fetchSymptoms(Long departmentId) {
        return symptomRepository.findByDepartment_Id(departmentId);
    }
}
