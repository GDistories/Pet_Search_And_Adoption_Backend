package com.petfound.backend.req;

import lombok.Data;

@Data
public class ResetPasswordReq {
    private String username;
    private String newPassword;
    private String verifyCode;
}
