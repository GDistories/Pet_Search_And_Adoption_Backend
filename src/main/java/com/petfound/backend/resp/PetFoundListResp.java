package com.petfound.backend.resp;
import com.petfound.backend.Entity.PetFound.PetFoundFill;
import lombok.Data;

import java.util.List;

@Data
public class PetFoundListResp {

    private Integer curPageNum;

    private Integer totalPageNum;

    private Integer pageSize;

    private List<PetFoundFill> petList;

}
