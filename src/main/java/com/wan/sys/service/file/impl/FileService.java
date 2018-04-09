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

    @Value("${site.root}")
    private String siteRoot;

    @Value("${site.uploadpath}")
    private String uploadPath;

    @Override
    public UploadFile uploadFile(MultipartFile file) throws IOException{

        // 文件存放服务端的位置
        String s = File.separator;
        File dir = new File(siteRoot + uploadPath);
        if (!dir.exists())
            dir.mkdirs();
        // 写文件到服务器
        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String newFileName = UUID.randomUUID() + extension;
        File serverFile = new File(dir.getAbsolutePath() + s + newFileName);
        file.transferTo(serverFile);

        return new UploadFile(uploadPath + s + newFileName);
    }
}
