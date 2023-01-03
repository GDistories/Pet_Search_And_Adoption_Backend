package com.petfound.backend.Utils;

import com.alibaba.fastjson2.JSONObject;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petfound.backend.Service.AdminService;
import com.petfound.backend.Service.ShelterService;
import com.petfound.backend.Service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/*
 * 配置拦截器
 */
@Component
public class JWTInterceptor implements HandlerInterceptor {
    public static JWTInterceptor jwtInterceptor;
    @Resource
    private AdminService adminService;
    @Resource
    private ShelterService shelterService;
    @Resource
    private UserService userService;

    @PostConstruct
     public void init() {
        jwtInterceptor = this;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        Map<String, Object> map = new HashMap<>();

        //获取请求头中的令牌
        String token = request.getHeader("token");

        if (token == null) {
            map.put("success", false);
            map.put("message", "The token is null");
            map.put("content", null);
            String json = new ObjectMapper().writeValueAsString(map);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(json);
            return false;
        }

        //TODO: Debug Code
        if (token.equals("debug")){
            return true;
        }


        List<String> allURI = new ArrayList<>();
        List<String> adminURI = new ArrayList<>();
        List<String> userURI = new ArrayList<>();
        List<String> shelterURI = new ArrayList<>();

        /////////////////////////All/////////////////////////
        allURI.add("/api/insertPet");
        allURI.add("/api/updatePet");
        allURI.add("/api/deletePet");
        allURI.add("/api/addPetSearch");
        allURI.add("/api/updatePetSearch");
        allURI.add("/api/deletePetSearch");
        allURI.add("/api/addPetFound");
        allURI.add("/api/updatePetFound");
        allURI.add("/api/deletePetFound");
        allURI.add("/uploadImage");
        allURI.add("/api/getPetSearchListByUsername");
        allURI.add("/api/getFoundPetListByUsername");

        /////////////////////////user/////////////////////////
        userURI.add("/api/userUpdate");

        /////////////////////////shelter/////////////////////////
        shelterURI.add("/api/shelterUpdate");
        shelterURI.add("/api/addShelterPet");
        shelterURI.add("/api/deleteShelterPet");
        shelterURI.add("/api/changeShelterAdoptionPetStatus");

        /////////////////////////admin/////////////////////////
        adminURI.add("/api/userDelete");
        adminURI.add("/api/userListFilter");
        adminURI.add("/api/shelterDelete");



        try {
            //验证令牌
            DecodedJWT verify = JWTUtils.verify(token);
            String username = verify.getClaim("username").asString();

            if (isAdmin(username)){
                return true;
            }

            //如果请求的是注册用户
            if (allURI.contains(request.getRequestURI())){
                if (isRegistered(username)){
                    return true;
                } else {
                    System.out.println(username + " is not registered");
                    map.put("success",false);
                    map.put("message", "Method Not Allowed");
                    map.put("content",null);
                }
            }

            //如果请求的是用户接口
            else if (userURI.contains(request.getRequestURI())){
                if (isUser(username)){
                    JSONObject requestBody = JSONObject.parseObject(getOpenApiRequestData(request));
                    if (requestBody != null && requestBody.getString("username").equals(username)){
                        return true;
                    } else {
                        System.out.println(username + " is not the user request");
                        map.put("success",false);
                        map.put("message", "Method Not Allowed");
                        map.put("content",null);
                    }
                } else {
                    System.out.println(username + " is not the user");
                    map.put("success",false);
                    map.put("message", "Method Not Allowed");
                    map.put("content",null);
                }
            }

            //如果请求的是收容所接口
            else if (shelterURI.contains(request.getRequestURI())){
                if (isShelter(username)){
                    JSONObject requestBody = JSONObject.parseObject(getOpenApiRequestData(request));
                    if (requestBody != null && requestBody.getString("username").equals(username)){
                        return true;
                    } else {
                        System.out.println(username + " is not the shelter request");
                        map.put("success",false);
                        map.put("message", "Method Not Allowed");
                        map.put("content",null);
                    }
                } else {
                    System.out.println(username + " is not the shelter");
                    map.put("success",false);
                    map.put("message", "You are not a shelter");
                    map.put("content",null);
                }
            }
            //如果请求的是管理员专用接口
            else if (adminURI.contains(request.getRequestURI())){
                if (isAdmin(username)) {
                    return true;
                } else {
                    map.put("success",false);
                    map.put("message", "You are not admin!");
                    map.put("content",null);
                }
            }

            String json = new ObjectMapper().writeValueAsString(map);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(json);
            return false;

        } catch (SignatureVerificationException e){
            map.put("message","Signature Invalid");
        } catch (TokenExpiredException e){
            map.put("message","Token expired");
        } catch (AlgorithmMismatchException e){
            map.put("message","Token algorithm mismatch");
        } catch (Exception e){
            e.printStackTrace();
            map.put("message","Token invalid");
        }
        map.put("success",false);
        map.put("content",null);



        //将map转为json
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }

    public boolean isRegistered(String username){
        return jwtInterceptor.adminService.isAdminExist(username)
                || jwtInterceptor.shelterService.isShelterExist(username)
                || jwtInterceptor.userService.isUserExist(username);
    }

    public boolean isShelter(String username){
        return jwtInterceptor.shelterService.isShelterExist(username) || isAdmin(username);
    }

    public boolean isUser(String username){
        return jwtInterceptor.userService.isUserExist(username) || isAdmin(username);
    }

    public boolean isAdmin(String username){
        return jwtInterceptor.adminService.isAdminExist(username);
    }

    public static String getOpenApiRequestData(HttpServletRequest request){
        try {

            int contentLength = request.getContentLength();
            if (contentLength < 0) {
                return null;
            }
            byte[] buffer = new byte[contentLength];
            for (int i = 0; i < contentLength;) {

                int readlen = request.getInputStream().read(buffer, i, contentLength - i);
                if (readlen == -1) {
                    break;
                }
                i += readlen;
            }

            String charEncoding = request.getCharacterEncoding();
            if (charEncoding == null) {
                charEncoding = "UTF-8";
            }
            return new String(buffer, charEncoding);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}

