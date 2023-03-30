package com.kltn.medical_consultation.models.schedule;

import lombok.Data;

import java.util.List;

@Data
public class ListFreeSchedule {

    private Long departmentId;
    private String departmentName;
    private Long patientProfileId;
    private List<DetailSchedule> detailSchedules;

    @Data
    public static class DetailSchedule {
        private Long doctorId;
        private String scheduleTime;
    }
}
