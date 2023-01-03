package com.petfound.backend.Entity.User;

import lombok.Data;

@Data
public class UserFilter {
    private String gender;

    private Integer startDob;

    private Integer endDob;
}
