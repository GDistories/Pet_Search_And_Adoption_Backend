package com.petfound.backend.Entity.PetFound;

import com.petfound.backend.Entity.Pet.Pet;
import com.petfound.backend.Entity.User.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetFoundFill {
    private Integer petFoundId;

    private Pet pet;

    private User user;

    private Integer countyId;

    private Integer createTime;

    private Integer status;

    private Integer shelterId;
}
