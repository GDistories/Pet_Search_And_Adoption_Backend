package com.petfound.backend.configuration;

import com.petfound.backend.Utils.JWTInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 注册拦截器
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptor())
                //拦截
                .addPathPatterns("/api/insertPet")
                .addPathPatterns("/api/updatePet")
                .addPathPatterns("/api/deletePet")
                .addPathPatterns("/api/userUpdate")
                .addPathPatterns("/api/userDelete")
                .addPathPatterns("/api/userListFilter")
                .addPathPatterns("/api/shelterUpdate")
                .addPathPatterns("/api/shelterDelete")
                .addPathPatterns("/api/addPetSearch")
                .addPathPatterns("/api/updatePetSearch")
                .addPathPatterns("/api/deletePetSearch")
                .addPathPatterns("/api/getPetSearchListByUsername")
                .addPathPatterns("/api/addPetFound")
                .addPathPatterns("/api/updatePetFound")
                .addPathPatterns("/api/deletePetFound")
                .addPathPatterns("/api/getFoundPetListByUsername")
                .addPathPatterns("/uploadImage")
                .addPathPatterns("/api/addShelterPet")
                .addPathPatterns("/api/deleteShelterPet")
                .addPathPatterns("/api/changeShelterAdoptionPetStatus");

                //放行
    }
}

