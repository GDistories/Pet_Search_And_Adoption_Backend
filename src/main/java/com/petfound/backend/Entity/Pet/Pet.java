package com.petfound.backend.Entity.Pet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pet {
    private Integer petId;

    private String nickname;

    private String sex;

    private Integer age;

    private String breed;

    private String size;

    private String color;

    private String picture;

    private String feature;

    private String petIdNum;

    private Double weight;

    private Integer identity;

    private Integer isFound;
}