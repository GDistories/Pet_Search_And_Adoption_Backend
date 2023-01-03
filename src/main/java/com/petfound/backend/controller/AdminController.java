package com.petfound.backend.controller;

import com.petfound.backend.Service.AdminService;
import com.petfound.backend.req.LoginReq;
import com.petfound.backend.resp.CommonResp;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

@RestController
public class AdminController {

    @Resource
    private AdminService adminService;

    @PostMapping("/api/adminLogin")
    public CommonResp<String> adminLogin(@RequestBody LoginReq loginReq) {
        return adminService.login(loginReq.getUsername(), loginReq.getPassword());
    }
}
