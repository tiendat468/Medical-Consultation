package com.kltn.medical_consultation.models.patient;

import com.kltn.medical_consultation.entities.database.Parent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParentDetail {
    private Long id;
    private String phoneNumber;
    private String fullName;
    private String address;

    public ParentDetail(Parent parent) {
        this.id = parent.getId();
        this.phoneNumber = parent.getPhoneNumber();
        this.fullName = parent.getFullName();
        this.address = parent.getAddress();
    }
}
