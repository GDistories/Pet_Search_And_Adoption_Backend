package com.petfound.backend.Entity.ShelterPets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShelterPets {
    private Integer shelterPetsId;

    private Integer shelterId;

    private Integer petId;

    private Integer status;

    private String createTime;
}