package com.petfound.backend.resp;

import lombok.Data;

import java.util.List;

@Data
public class ListResp<T> {

    private Integer curPageNum;

    private Integer totalPageNum;

    private Integer pageSize;

    private List<T> list;
}
