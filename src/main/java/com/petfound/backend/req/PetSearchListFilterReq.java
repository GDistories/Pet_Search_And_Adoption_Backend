package com.petfound.backend.req;

import com.petfound.backend.Entity.PetSearch.PetSearchFilter;
import lombok.Data;

@Data
public class PetSearchListFilterReq {
    Integer curPageNum;
    Integer pageSize;
    PetSearchFilter petSearchFilter;

    @Override
    public String toString() {
        return "PetSearchListFilterReq{" +
                "curPageNum=" + curPageNum +
                ", pageSize=" + pageSize +
                ", petSearchFilter=" + petSearchFilter +
                '}';
    }
}
