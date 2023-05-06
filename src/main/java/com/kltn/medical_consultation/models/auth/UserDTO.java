package com.kltn.medical_consultation.models.auth;

import com.kltn.medical_consultation.entities.database.User;
import com.kltn.medical_consultation.enumeration.UserType;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String type;
    private Boolean isActive = false;

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.type = UserType.from(user.getType()).getCode();
        this.isActive = user.getIsActive();
    }
}
