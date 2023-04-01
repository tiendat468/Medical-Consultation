package com.kltn.medical_consultation.services;

import com.kltn.medical_consultation.entities.database.Department;
import com.kltn.medical_consultation.entities.database.PatientProfile;
import com.kltn.medical_consultation.entities.database.Symptom;
import com.kltn.medical_consultation.models.ApiException;
import com.kltn.medical_consultation.models.BaseResponse;
import com.kltn.medical_consultation.models.department.response.DepartmentPercent;
import com.kltn.medical_consultation.models.patient.PatientMessageCode;
import com.kltn.medical_consultation.models.schedule.ListFreeSchedule;
import com.kltn.medical_consultation.models.department.request.FetchDepartmentRequest;
import com.kltn.medical_consultation.repository.database.DepartmentRepository;
import com.kltn.medical_consultation.repository.database.PatientProfileRepository;
import com.kltn.medical_consultation.repository.database.SymptomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

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

    public BaseResponse<ListFreeSchedule> fetchDepartment(FetchDepartmentRequest request, HttpServletRequest httpServletRequest) {
        String[] patientSymptoms = request.getSymptom().split(",");
        int totalPatientSymptom = patientSymptoms.length;
        List<DepartmentPercent> departmentPercents = new ArrayList<>();
        List<Department> departments = getAllDepartments();
        for (Department department : departments) {
            Double countSymptom = 0.0;
            Double percent = 0.0;
            List<Symptom> depSymptoms = fetchSymptoms(department.getId());
            for (Symptom depSymptom : depSymptoms) {
                for (String patSymptom : patientSymptoms) {
                    if (depSymptom.getName().toLowerCase().contains(patSymptom.trim().toLowerCase())
                            || patSymptom.trim().toLowerCase().contains(depSymptom.getName().toLowerCase())) {
                        countSymptom++;
                    }
                }
            }
            percent = (countSymptom / totalPatientSymptom) * 100;
            DepartmentPercent departmentPercent = new DepartmentPercent(department.getSymbol(), roundDouble(percent));
            departmentPercents.add(departmentPercent);
        }

        sortByPercent(departmentPercents);
        ListFreeSchedule listFreeSchedule = scheduleService.fetchSchedule(departmentPercents, request.getScheduleDate());
        listFreeSchedule.setMedicalDate(request.getScheduleDate());
        return new BaseResponse<>(listFreeSchedule);
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public List<Symptom> fetchSymptoms(Long departmentId) {
        return symptomRepository.findByDepartmentId(departmentId);
    }

    public Double roundDouble(Double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public void sortByPercent(List<DepartmentPercent> departmentPercents) {
        Collections.sort(departmentPercents, new Comparator<DepartmentPercent>() {
            @Override
            public int compare(DepartmentPercent o1, DepartmentPercent o2) {
                return o2.getPercent().compareTo(o1.getPercent());
            }
        });
    }

}
