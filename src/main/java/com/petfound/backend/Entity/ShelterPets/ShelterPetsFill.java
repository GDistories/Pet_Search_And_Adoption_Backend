package com.petfound.backend.Entity.ShelterPets;

import com.petfound.backend.Entity.Pet.Pet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShelterPetsFill {
    private Integer shelterPetsId;

    private Integer shelterId;

    private Pet pet;

    private Integer status;

    private String createTime;
}