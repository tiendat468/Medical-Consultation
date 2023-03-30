package com.kltn.medical_consultation.services;

import com.kltn.medical_consultation.entities.database.Department;
import com.kltn.medical_consultation.entities.database.Doctor;
import com.kltn.medical_consultation.entities.database.MedicalSchedule;
import com.kltn.medical_consultation.models.Value;
import com.kltn.medical_consultation.models.schedule.ListFreeSchedule;
import com.kltn.medical_consultation.repository.database.DepartmentRepository;
import com.kltn.medical_consultation.repository.database.DoctorRepository;
import com.kltn.medical_consultation.repository.database.MedicalScheduleRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Log4j2
public class MedicalScheduleService extends BaseService{
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    MedicalScheduleRepository scheduleRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    public MedicalScheduleService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public ListFreeSchedule fetchSchedule(List<Value> departmentPercents, String scheduleData) {
        ListFreeSchedule listFreeSchedule = new ListFreeSchedule();
        Department department = findDepartmentBySymbol(departmentPercents.get(0).getKey());

        List<ListFreeSchedule.DetailSchedule> detailScheduleList = new ArrayList<>();
        ListFreeSchedule.DetailSchedule detailSchedule = new ListFreeSchedule.DetailSchedule();
        detailSchedule.setScheduleTime("7");
        detailScheduleList.add(detailSchedule);
        detailSchedule = new ListFreeSchedule.DetailSchedule();
        detailSchedule.setScheduleTime("8");
        detailScheduleList.add(detailSchedule);
        detailSchedule = new ListFreeSchedule.DetailSchedule();
        detailSchedule.setScheduleTime("9");
        detailScheduleList.add(detailSchedule);
        detailSchedule = new ListFreeSchedule.DetailSchedule();
        detailSchedule.setScheduleTime("10");
        detailScheduleList.add(detailSchedule);

        int count = 0;
        List<Doctor> doctorList = doctorRepository.findByDepartmentId(department.getId());
        for (Doctor doctor: doctorList) {
            List<MedicalSchedule> scheduleList = scheduleRepository.findByDoctorIdAndMedicalDate(doctor.getId(), scheduleData);
            for (int j = 0; j < detailScheduleList.size(); j++) {
                if(detailScheduleList.get(j).getDoctorId() == null) {
                    for (int i = 0; i < scheduleList.size(); i++) {
                        if (scheduleList.get(i).getHours().equalsIgnoreCase(detailScheduleList.get(j).getScheduleTime())) {
                            break;
                        }
                        if (i == scheduleList.size() - 1) {
                            detailScheduleList.get(j).setDoctorId(doctor.getId());
                            count++;
                        }
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
}
