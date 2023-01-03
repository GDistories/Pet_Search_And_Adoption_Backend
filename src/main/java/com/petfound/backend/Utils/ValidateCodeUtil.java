package com.petfound.backend.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.SecureRandom;
import java.util.Random;

public class ValidateCodeUtil {
    //Random 不是密码学安全的，加密相关的推荐使用 SecureRandom
    private static Random RANDOM = new SecureRandom();

    private static final String randomString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWSYZ";
    private final static String token = "MAILTOKEN";

    public String generateVerCode(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        char[] nonceChars = new char[6];
        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = randomString.charAt(RANDOM.nextInt(randomString.length()));
        }
        //移除之前的session中的验证码信息
        session.removeAttribute(token);
        //将验证码放入session
        session.setAttribute(token,new String(nonceChars));//设置token,参数token是要设置的具体值
        return new String(nonceChars);
    }

}

