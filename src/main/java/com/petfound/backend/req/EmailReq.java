package com.petfound.backend.req;

import lombok.Data;

@Data
public class EmailReq {
    private String emailAddress;
    private String usage;
    private String code;
}
