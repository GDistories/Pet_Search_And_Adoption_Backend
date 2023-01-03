package com.petfound.backend.req;

import lombok.Data;

@Data
public class LoginReq {
    private String username;
    private String password;
}
