package com.petfound.backend.req;

import lombok.Data;

@Data
public class PageAndUsernameReq {
    private Integer curPageNum;
    private Integer pageSize;
    private String username;
}
