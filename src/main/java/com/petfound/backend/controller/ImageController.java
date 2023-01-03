package com.petfound.backend.controller;

import com.petfound.backend.Service.ImageService;
import com.petfound.backend.resp.CommonResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/uploadImage")
    public CommonResp<String> uploadImage(@RequestParam("file") MultipartFile file) {
        return imageService.uploadImage(file);
    }
}
