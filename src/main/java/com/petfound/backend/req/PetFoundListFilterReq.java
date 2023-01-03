package com.petfound.backend.req;

import com.petfound.backend.Entity.PetFound.PetFoundFilter;
import lombok.Data;

@Data
public class PetFoundListFilterReq {

    Integer curPageNum;
    Integer pageSize;
    PetFoundFilter petFoundFilter;

    @Override
    public String toString() {
        return "PetSearchListFilterReq{" +
                "curPageNum=" + curPageNum +
                ", pageSize=" + pageSize +
                ", petFoundFilter=" + petFoundFilter +
                '}';
    }
}
