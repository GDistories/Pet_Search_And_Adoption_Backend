package com.petfound.backend.Entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer userId;

    private String username;

    private String password;

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