package com.petfound.backend.req;

import lombok.Data;

@Data
public class SearchReq<T> {
    private Integer curPageNum;
    private Integer pageSize;
    private T search;
}
