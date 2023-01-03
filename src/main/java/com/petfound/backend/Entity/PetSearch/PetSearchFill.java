package com.petfound.backend.Entity.PetSearch;

import com.petfound.backend.Entity.Pet.Pet;
import com.petfound.backend.Entity.User.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetSearchFill {
    private Integer petSearchId;

    private Pet pet;

    private User user;

    private Integer countyId;

    private String createTime;
}