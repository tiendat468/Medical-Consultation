package com.kltn.medical_consultation.models.vnpay;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Values {
    private String value;

    public String getValue() {
        return value;
    }
    public Values(String value) {
        this.value = value;
    }
}
