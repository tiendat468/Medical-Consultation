package com.kltn.medical_consultation.services;

import com.kltn.medical_consultation.models.schedule.ListFreeSchedule;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Log4j2
public class MedicalScheduleService extends BaseService{

    public ListFreeSchedule fetchSchedule(Long departmentId, Date scheduleData) {
        ListFreeSchedule listFreeSchedule = new ListFreeSchedule();

        return listFreeSchedule;
    }
}
