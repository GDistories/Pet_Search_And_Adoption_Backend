package com.petfound.backend.Service;

import com.petfound.backend.Entity.Admin;
import com.petfound.backend.Mapper.AdminMapper;
import com.petfound.backend.Utils.JWTUtils;
import com.petfound.backend.resp.CommonResp;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminService {

    @Resource
    private AdminMapper adminMapper;

    public CommonResp<String> login(String username, String password) {
        CommonResp<String> resp = new CommonResp<>();
        Admin admin = adminMapper.selectByUsernameAndPassword(username, password);
        if (admin == null) {
            resp.setSuccess(false);
            resp.setMessage("Username or Password is Wrong!");
        } else {
            String token = JWTUtils.getToken(username);
            resp.setMessage(token);
        }
        return resp;
    }

    public Boolean isAdminExist(String username) {
        return adminMapper.selectByUsername(username) != null;
    }
}
