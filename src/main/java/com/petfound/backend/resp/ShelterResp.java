package com.petfound.backend.resp;

import lombok.Data;

@Data
public class ShelterResp {
    private Integer shelterId;

    private String username;

    private String shelterName;

    private String address;

    private String phone;

    private String email;

    private String profilePhoto;

    private String location;

    private Integer countyId;

}
