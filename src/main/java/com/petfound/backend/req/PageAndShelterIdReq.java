package com.petfound.backend.req;

import lombok.Data;

@Data
public class PageAndShelterIdReq {
    private Integer curPageNum;
    private Integer pageSize;
    private Integer shelterId;
}
