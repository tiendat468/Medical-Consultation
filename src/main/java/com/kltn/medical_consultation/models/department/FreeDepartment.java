package com.kltn.medical_consultation.models.department;

import com.kltn.medical_consultation.models.schedule.ListFreeSchedule;
import lombok.Data;

@Data
public class FreeDepartment {
    private Long departmentId;
    private Long departmentName;
    private Long patientProfileId;
    private ListFreeSchedule listFreeSchedule;
}
