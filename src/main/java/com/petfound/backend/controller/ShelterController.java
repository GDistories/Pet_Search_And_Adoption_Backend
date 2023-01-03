package com.petfound.backend.controller;

import com.petfound.backend.Entity.Shelter.Shelter;
import com.petfound.backend.Entity.Shelter.ShelterFilter;
import com.petfound.backend.Service.ShelterService;
import com.petfound.backend.req.ListFilterReq;
import com.petfound.backend.req.LoginReq;
import com.petfound.backend.req.ResetPasswordReq;
import com.petfound.backend.req.SearchReq;
import com.petfound.backend.resp.CommonResp;
import com.petfound.backend.resp.ListResp;
import com.petfound.backend.resp.ShelterResp;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
public class ShelterController {
    @Resource
    private ShelterService shelterService;

    @PostMapping("/api/shelterLogin")
    public CommonResp<ShelterResp> shelterLogin(@RequestBody LoginReq loginReq) {
        return shelterService.login(loginReq.getUsername(), loginReq.getPassword());
    }

    @PostMapping("/api/shelterRegister")
    public CommonResp<String> shelterRegister(@RequestBody Shelter shelter) {
        return shelterService.register(shelter);
    }

    @PostMapping("/api/shelterUpdate")
    public CommonResp<String> shelterUpdate(@RequestBody Shelter shelter) {
        return shelterService.update(shelter);
    }

    @PostMapping("/api/shelterDelete")
    public CommonResp<String> shelterDelete(@RequestBody Shelter shelter) {
        return shelterService.delete(shelter.getUsername());
    }

    @PostMapping("/api/getShelterByShelterId")
    public CommonResp<ShelterResp> getShelterByShelterId(@RequestBody Shelter shelter) {
        return shelterService.getShelterByShelterId(shelter.getShelterId());
    }

    @PostMapping("/api/getShelterByUsername")
    public CommonResp<ShelterResp> getShelterByUsername(@RequestBody Shelter shelter) {
        return shelterService.getShelterByUsername(shelter.getUsername());
    }

    @PostMapping("/api/shelterListFilter")
    public CommonResp<ListResp<ShelterResp>> shelterListFilter(@RequestBody ListFilterReq<ShelterFilter> shelterFilter) {
        return shelterService.listFilter(shelterFilter);
    }

    @PostMapping("/api/shelterSearch")
    public CommonResp<ListResp<ShelterResp>> shelterSearch(@RequestBody SearchReq<String> searchReq) {
        return shelterService.getShelterBySearch(searchReq);
    }

    @PostMapping("/api/shelterResetPassword")
    public CommonResp<String> shelterResetPassword(@RequestBody ResetPasswordReq resetPasswordReq, HttpSession session) {
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
                resp = shelterService.resetPassword(resetPasswordReq);
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
