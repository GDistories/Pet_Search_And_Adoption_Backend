package com.petfound.backend.Entity.PetSearch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetSearch {
    private Integer petSearchId;

    private Integer petId;

    private String username;

    private Integer countyId;

    private String createTime;
}