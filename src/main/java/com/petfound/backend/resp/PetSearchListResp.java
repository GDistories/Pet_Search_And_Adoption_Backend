package com.petfound.backend.resp;
import com.petfound.backend.Entity.PetSearch.PetSearchFill;
import lombok.Data;

import java.util.List;

@Data
public class PetSearchListResp {

    private Integer curPageNum;

    private Integer totalPageNum;

    private Integer pageSize;

    private List<PetSearchFill> petList;

}
