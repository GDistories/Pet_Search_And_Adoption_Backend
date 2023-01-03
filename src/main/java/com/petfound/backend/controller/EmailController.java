// Java Program to Create Rest Controller that
// Defines various API for Sending Mail

package com.petfound.backend.controller;

// Importing required classes
import com.petfound.backend.Entity.Email;
import com.petfound.backend.Service.EmailService;
import com.petfound.backend.Utils.ValidateCodeUtil;
import com.petfound.backend.req.EmailReq;
import com.petfound.backend.resp.CommonResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// Annotation
@RestController
// Class
public class EmailController {

    @Autowired
    EmailService emailService;

    @PostMapping("/api/sendMail")
    public CommonResp<String> sendEmail(@RequestBody EmailReq req, HttpServletRequest request,
                                        HttpServletResponse response, HttpSession session) {
        String emailAddress = req.getEmailAddress();
        String usage = req.getUsage();
        CommonResp<String> resp = new CommonResp<>();
        try {
            Email email = new Email();
            ValidateCodeUtil validateCode = new ValidateCodeUtil();
            email.setRecipient(emailAddress);
            email.setSubject("Verify Code");
            email.setUsage(usage);
            email.setMsgBody(validateCode.generateVerCode(request,response));
            Exception exception = emailService.sendSimpleMail(email);
            if (exception == null) {
                //Obtain the verification code stored in the session domain
                String sessionCode = String.valueOf(session.getAttribute("MAILTOKEN")).toLowerCase();
                System.out.println("Verify In Session: " + sessionCode);
                resp.setMessage("Send email successfully");
            } else {
                resp.setMessage("The mail server is abnormal, please try again later");
                resp.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setSuccess(false);
            resp.setMessage("Send email failed");
        }
        return resp;
    }

    @PostMapping("/api/emailCaptcha")
    public CommonResp<String> getCheckCaptcha(@RequestBody EmailReq req, HttpSession session) {
        String code = req.getCode();
        CommonResp<String> resp = new CommonResp<String>();
        try {
            // Obtain the verification code toLowerCase() stored in the session domain
            // Verify the verification code regardless of case
            String sessionCode= String.valueOf(session.getAttribute("MAILTOKEN")).toLowerCase();
            System.out.println("Verify In Session: "+sessionCode);
            String receivedCode=code.toLowerCase();
            System.out.println("Verify Code that User Key In: "+receivedCode);
            if (!sessionCode.equals("") && !receivedCode.equals("") && sessionCode.equals(receivedCode))
            {
                resp.setMessage("Verify successfully!");
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
