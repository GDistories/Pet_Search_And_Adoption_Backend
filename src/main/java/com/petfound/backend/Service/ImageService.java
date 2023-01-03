package com.petfound.backend.Service;

import com.petfound.backend.resp.CommonResp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class ImageService {
    @Value(("${web.upload-path}"))
    private String uploadPath;

    public CommonResp<String> uploadImage(MultipartFile file) {
        CommonResp<String> resp = new CommonResp<String>();
        if (file.isEmpty()) {
            resp.setSuccess(false);
            resp.setMessage("File is empty");
            return resp;
        }
        String originalFilename = file.getOriginalFilename();
        String fileName = null;
        if (originalFilename != null) {
            fileName = java.util.UUID.randomUUID().toString() + "." + originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        }
        String visibleUri="/image/"+fileName;
        String saveUri=uploadPath+"/image/"+fileName;

        File saveFile = new File(saveUri);

        if (!saveFile.exists()){
            Boolean createDir = saveFile.mkdirs();
        }

        try {
            file.transferTo(saveFile);
            resp.setMessage("Upload image successfully");
            resp.setContent(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccess(false);
            resp.setMessage("Upload image failed");
        }

        return resp;
    }
}
