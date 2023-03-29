package com.kltn.medical_consultation.services;

import com.kltn.medical_consultation.entities.database.Doctor;
import com.kltn.medical_consultation.entities.database.MedicalSchedule;
import com.kltn.medical_consultation.models.schedule.ListFreeSchedule;
import com.kltn.medical_consultation.repository.database.DoctorRepository;
import com.kltn.medical_consultation.repository.database.MedicalScheduleRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.*;

@Service
@Log4j2
public class MedicalScheduleService extends BaseService{
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    MedicalScheduleRepository scheduleRepository;

    public MedicalScheduleService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public ListFreeSchedule fetchSchedule(Long departmentId, OffsetDateTime scheduleData) {
        ListFreeSchedule listFreeSchedule = new ListFreeSchedule();

        ListFreeSchedule.DetailSchedule detailSchedule = new ListFreeSchedule.DetailSchedule();
        List<ListFreeSchedule.DetailSchedule> detailScheduleList = new ArrayList<>();
        List<ListFreeSchedule.DetailSchedule> detailScheduleListRS = new ArrayList<>();
        detailSchedule.setScheduleTime("7");
        detailScheduleList.add(detailSchedule);
        detailSchedule.setScheduleTime("8");
        detailScheduleList.add(detailSchedule);
        detailSchedule.setScheduleTime("9");
        detailScheduleList.add(detailSchedule);
        detailSchedule.setScheduleTime("10");
        detailScheduleList.add(detailSchedule);
        int count = 0;
        List<Doctor> doctorList = doctorRepository.findByDepartment_Id(departmentId);
        for (Doctor doctor: doctorList) {
            List<MedicalSchedule> scheduleList = scheduleRepository.findByDoctorIdAndMedicalDate(doctor.getId(),scheduleData);
            for (int j = 0; j < detailScheduleList.size(); j++) {
                if(detailScheduleList.get(j).getDoctorId() == null) {
                    for (int i = 0; i < scheduleList.size(); i++) {
                        if (String.valueOf(scheduleList.get(i).getMedicalDate().getHour()).equalsIgnoreCase(detailScheduleList.get(j).getScheduleTime())) {
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
        listFreeSchedule.setDetailSchedules(detailScheduleList);
        return listFreeSchedule;
    }
}
