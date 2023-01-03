package com.petfound.backend.Entity.Pet;

import lombok.Data;

@Data
public class PetFilter {
    private String sex;

    private String breed;

    private String size;

    private String color;

    private Integer startAge;

    private Integer endAge;

    private Double startWeight;

    private Double endWeight;

}
