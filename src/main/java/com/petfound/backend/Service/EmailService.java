package com.petfound.backend.Service;

// Importing required classes
import com.petfound.backend.Entity.Email;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Arrays;

// Annotation
@Service
// Class
// Implementing EmailService interface
public class EmailService{

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String sender;

    @Value("${spring.mail.nickname}")
    private String nickname;

    // To send a simple email
    public Exception sendSimpleMail(Email details)
    {

        // Try block to check for exceptions
        try {
            // Creating a simple mail message
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            String VerifyCode = details.getMsgBody();
            // Setting up necessary details
            helper.setFrom(new InternetAddress(MimeUtility.encodeText(nickname)+" <"+sender+">").toString());
            helper.setTo(details.getRecipient());
            helper.setSubject(details.getSubject());

            Context context = new Context();
            context.setVariable("usage", details.getUsage());
            context.setVariable("verifyCode", Arrays.asList(VerifyCode.split("")));
            String process = templateEngine.process("email.html", context);
            helper.setText(process,true);
            javaMailSender.send(mimeMessage);
            return null;
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            e.printStackTrace();
            return e;
        }
    }
}