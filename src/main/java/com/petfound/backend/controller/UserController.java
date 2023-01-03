package com.petfound.backend.controller;

import com.petfound.backend.Entity.User.User;
import com.petfound.backend.Entity.User.UserFilter;
import com.petfound.backend.Service.UserService;
import com.petfound.backend.req.ListFilterReq;
import com.petfound.backend.req.LoginReq;
import com.petfound.backend.req.ResetPasswordReq;
import com.petfound.backend.resp.CommonResp;
import com.petfound.backend.resp.ListResp;
import com.petfound.backend.resp.UserResp;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/api/userLogin")
    public CommonResp<UserResp> userLogin(@RequestBody LoginReq loginReq) {
        return userService.login(loginReq.getUsername(), loginReq.getPassword());
    }
    @PostMapping("/api/userRegister")
    public CommonResp<String> userRegister(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/api/userUpdate")
    public CommonResp<String> userUpdate(@RequestBody User user) {
        return userService.update(user);
    }

    @PostMapping("/api/userDelete")
    public CommonResp<String> userDelete(@RequestBody User user) {
        return userService.delete(user.getUsername());
    }

    @PostMapping("/api/userListFilter")
    public CommonResp<ListResp<UserResp>> userListFilter(@RequestBody ListFilterReq<UserFilter> userFilter) {
        return userService.listFilter(userFilter);
    }

    @PostMapping("/api/userResetPassword")
    public CommonResp<String> userResetPassword(@RequestBody ResetPasswordReq resetPasswordReq, HttpSession session) {
        String code = resetPasswordReq.getVerifyCode();
        CommonResp<String> resp = new CommonResp<>();
        try {
            // Obtain the verification code toLowerCase() stored in the session domain
            // Verify the verification code regardless of case
            String sessionCode= String.valueOf(session.getAttribute("MAILTOKEN")).toLowerCase();
            System.out.println("Verify In Session: "+sessionCode);
            String receivedCode=code.toLowerCase();
            System.out.println("Verify Code that User Key In: "+receivedCode);
            if (!sessionCode.equals("") && !receivedCode.equals("") && sessionCode.equals(receivedCode))
            {
                resp = userService.resetPassword(resetPasswordReq);
            }
            else
            {
                resp.setSuccess(false);
                resp.setMessage("Verify failed!");
            }
        } catch (Exception e) {
            resp.setSuccess(false);
            resp.setMessage("Verify failed!");
        }
        return resp;
    }

}
