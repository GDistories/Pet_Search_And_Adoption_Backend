package com.petfound.backend.Utils;

import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;

@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public class JWTUtils {
    private static final String SING = "PetFoundAndAdoption";

    public static String getToken(String username) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 7);
        JWTCreator.Builder builder = com.auth0.jwt.JWT.create();
        builder.withClaim("username", username);
        builder.withExpiresAt(instance.getTime());
        return builder.sign(com.auth0.jwt.algorithms.Algorithm.HMAC256(SING));
    }

    public static DecodedJWT verify(String token) {
        return com.auth0.jwt.JWT.require(com.auth0.jwt.algorithms.Algorithm.HMAC256(SING)).build().verify(token);
    }
}
