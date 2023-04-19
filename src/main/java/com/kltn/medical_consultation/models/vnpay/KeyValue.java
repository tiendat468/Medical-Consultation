package com.kltn.medical_consultation.models.vnpay;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KeyValue {
    private String key;
    private String value;

    public KeyValue(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
