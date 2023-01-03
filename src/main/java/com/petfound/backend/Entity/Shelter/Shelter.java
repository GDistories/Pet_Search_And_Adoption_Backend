package com.petfound.backend.Entity.Shelter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shelter {
    private Integer shelterId;

    private String username;

    private String password;

    private String shelterName;

    private String address;

    private String phone;

    private String email;

    private String profilePhoto;

    private String location;

    private Integer countyId;

    private String registerCode;
}