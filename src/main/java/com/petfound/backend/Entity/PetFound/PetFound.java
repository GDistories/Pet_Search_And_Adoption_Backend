package com.petfound.backend.Entity.PetFound;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetFound {
    private Integer petFoundId;

    private Integer petId;

    private String username;

    private Integer countyId;

    private Integer createTime;

    private Integer status;

    private Integer shelterId;
}