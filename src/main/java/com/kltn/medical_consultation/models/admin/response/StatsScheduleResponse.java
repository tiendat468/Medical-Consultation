package com.kltn.medical_consultation.models.admin.response;

import com.kltn.medical_consultation.models.admin.StatsSchedule;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StatsScheduleResponse {
    private String condition;
    private List<StatsSchedule> statsSchedules = new ArrayList<>();

}
