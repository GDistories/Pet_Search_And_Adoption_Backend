package com.petfound.backend.resp;

import lombok.Data;

@Data
public class UserResp {
    private Integer userId;

    private String username;

    private String email;

    private String phone;

    private Integer identity;

    private String dob;

    private String gender;

    private String firstName;

    private String lastName;

    private String address;

    private String profilePhoto;
}
