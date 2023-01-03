package com.petfound.backend.Entity.PetFound;

import lombok.Data;

@Data
public class PetFoundFilter {

    private String sex;

    private String breed;

    private String size;

    private String color;

    private Integer countyId;

    private Integer status;

    private Integer startAge;

    private Integer endAge;

    private Double startWeight;

    private Double endWeight;

    private Integer startCreateTime;

    private Integer endCreateTime;
}
