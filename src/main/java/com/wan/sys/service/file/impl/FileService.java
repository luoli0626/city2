package com.wan.sys.service.file.impl;

import com.wan.sys.entity.file.UploadFailed;
import com.wan.sys.entity.file.UploadFileResult;
import com.wan.sys.entity.file.UploadSuccess;
import com.wan.sys.pojo.ErrorCodeEnum;
import com.wan.sys.service.file.IFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileService implements IFileService {

    @Value("${site.upload.path}")
    private String path;

    @Value("${site.upload.location}")
    private String location;

    private String sep = File.separator;

    @Override
    public List<UploadFileResult> uploadFiles(MultipartFile[] files) {

        List<UploadFileResult> results = new ArrayList<UploadFileResult>();
        if (files != null && files.length > 0) {
            for (int i=0; i < files.length; i++) {
                MultipartFile file = files[i];
                UploadFileResult result = saveFile(file);
                result.setIndex(i);
                results.add(result);
            }
        }

        return results;
    }

    /**
     * 保存文件
     * @param file
     * @return
     */
    private UploadFileResult saveFile(MultipartFile file) {

        if (file != null && !file.isEmpty()) {
            File dir = new File(location);
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

            File serverFile = new File(dir.getAbsolutePath() + sep + newFileName);
            try {
                file.transferTo(serverFile);
                return new UploadSuccess(path + sep + newFileName);
            } catch (IOException ex) {
                return new UploadFailed(ErrorCodeEnum.FAIL_IOERR);
            }
        }

        return new UploadFailed(ErrorCodeEnum.FAIL_NULLFILE);
    }
}
