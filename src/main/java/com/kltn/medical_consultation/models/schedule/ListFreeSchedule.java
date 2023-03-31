package com.kltn.medical_consultation.models.schedule;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class ListFreeSchedule {

    private Long departmentId;
    private String departmentName;
    private Long patientProfileId;
    private String medicalDate;
    private List<DetailSchedule> detailSchedules;

    public ListFreeSchedule() {
        List<DetailSchedule> detailScheduleList = new ArrayList<>();
        DetailSchedule detailSchedule = new DetailSchedule();
        detailSchedule.setScheduleTime("7");
        detailScheduleList.add(detailSchedule);

        detailSchedule = new DetailSchedule();
        detailSchedule.setScheduleTime("8");
        detailScheduleList.add(detailSchedule);

        detailSchedule = new DetailSchedule();
        detailSchedule.setScheduleTime("9");
        detailScheduleList.add(detailSchedule);

        detailSchedule = new DetailSchedule();
        detailSchedule.setScheduleTime("10");
        detailScheduleList.add(detailSchedule);

        detailSchedule = new DetailSchedule();
        detailSchedule.setScheduleTime("13");
        detailScheduleList.add(detailSchedule);

        detailSchedule = new DetailSchedule();
        detailSchedule.setScheduleTime("14");
        detailScheduleList.add(detailSchedule);

        detailSchedule = new DetailSchedule();
        detailSchedule.setScheduleTime("15");
        detailScheduleList.add(detailSchedule);

        detailSchedule = new DetailSchedule();
        detailSchedule.setScheduleTime("16");
        detailScheduleList.add(detailSchedule);

        this.detailSchedules = detailScheduleList;
    }

    @Data
    public static class DetailSchedule {
        private Long doctorId;
        private String scheduleTime;
        private Double price = 0.0;
    }
}
