package com.petfound.backend.Entity.PetSearch;

import lombok.Data;

@Data
public class PetSearchFilter {

    private String sex;

    private String breed;

    private String size;

    private String color;

    private Integer countyId;

    private Integer startAge;

    private Integer endAge;

    private Double startWeight;

    private Double endWeight;

    private Integer startCreateTime;

    private Integer endCreateTime;
}
