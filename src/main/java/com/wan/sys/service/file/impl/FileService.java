package com.wan.sys.service.file.impl;

import com.wan.sys.entity.file.UploadFile;
import com.wan.sys.service.file.IFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileService implements IFileService {

    @Value("${site.uploadpath}")
    private String uploadPath;

    @Override
    public UploadFile uploadFile(MultipartFile file) throws IOException{

        String s = File.separator;
        File dir = new File(uploadPath);
        if (!dir.exists()) { dir.mkdirs(); }
        String originalFileName = file.getOriginalFilename();

        //保留文件原后缀名
        int index = originalFileName.lastIndexOf(".");
        String newFileName;
        if (index > 0) {
            String extension = originalFileName.substring(index);
            newFileName = UUID.randomUUID() + extension;
        } else {
            newFileName = UUID.randomUUID().toString();
        }

        File serverFile = new File(dir.getAbsolutePath() + s + newFileName);
        file.transferTo(serverFile);

        return new UploadFile(uploadPath + s + newFileName);
    }
}
