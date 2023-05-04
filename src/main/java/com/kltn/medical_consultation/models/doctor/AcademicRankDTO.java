package com.kltn.medical_consultation.models.doctor;

import com.kltn.medical_consultation.entities.database.AcademicRank;
import lombok.Data;

@Data
public class AcademicRankDTO {
    private Long id;
    private String name;
    private String symbol;
    private int priority;

    public static AcademicRankDTO of(AcademicRank academicRank) {
        AcademicRankDTO academicRankDTO = new AcademicRankDTO();
        academicRankDTO.setId(academicRank.getId());
        academicRankDTO.setName(academicRank.getName());
        academicRankDTO.setSymbol(academicRank.getSymbol());
        academicRankDTO.setPriority(academicRank.getPriority());
        return academicRankDTO;
    }
}
