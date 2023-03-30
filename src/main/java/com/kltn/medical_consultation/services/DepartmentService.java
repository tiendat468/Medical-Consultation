package com.kltn.medical_consultation.services;

import com.kltn.medical_consultation.entities.database.Department;
import com.kltn.medical_consultation.entities.database.PatientProfile;
import com.kltn.medical_consultation.entities.database.Symptom;
import com.kltn.medical_consultation.models.ApiException;
import com.kltn.medical_consultation.models.BaseResponse;
import com.kltn.medical_consultation.models.Value;
import com.kltn.medical_consultation.models.department.FreeDepartment;
import com.kltn.medical_consultation.models.patient.PatientMessageCode;
import com.kltn.medical_consultation.models.schedule.ListFreeSchedule;
import com.kltn.medical_consultation.models.schedule.request.FetchDepartmentRequest;
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

        Optional<PatientProfile> optionalPatientProfile = patientProfileRepository.findById(request.getPatientProfileId());
        if (optionalPatientProfile.isEmpty()) {
            throw new ApiException(PatientMessageCode.PATIENT_PROFILE_NOT_FOUND);
        }

        PatientProfile patientProfile = optionalPatientProfile.get();
        HashMap<String, Double> hashMapDepartment = new HashMap<>();
        String[] patientSymptoms = patientProfile.getSymptom().trim().split(",");
        int totalPatientSymptom = patientSymptoms.length;
        List<Value> departmentPercents = new ArrayList<>();
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
            Value departmentPercent = new Value(department.getSymbol(), roundDouble(percent));
            departmentPercents.add(departmentPercent);
        }

        Collections.sort(departmentPercents, new Comparator<Value>() {
            @Override
            public int compare(Value o1, Value o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        ListFreeSchedule listFreeSchedule = new ListFreeSchedule();
        listFreeSchedule = scheduleService.fetchSchedule(departmentPercents, request.getScheduleDate());
        listFreeSchedule.setPatientProfileId(request.getPatientProfileId());
        return new BaseResponse<ListFreeSchedule>(listFreeSchedule);
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

    public HashMap<String, Double> sortByValue(HashMap<String, Double> hashMap)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Double> > list =
                new LinkedList<Map.Entry<String, Double> >(hashMap.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Double> >() {
            public int compare(Map.Entry<String, Double> o1,
                               Map.Entry<String, Double> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Double> temp = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> entry : list) {
            temp.put(entry.getKey(), entry.getValue());
        }
        return temp;
    }
}
