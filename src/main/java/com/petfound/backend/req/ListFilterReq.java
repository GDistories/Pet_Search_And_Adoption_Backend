package com.petfound.backend.req;

import lombok.Data;

@Data
public class ListFilterReq<T> {
    private Integer curPageNum;
    private Integer pageSize;
    private T filter;
}
