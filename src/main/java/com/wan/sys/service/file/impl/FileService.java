package com.wan.sys.service.file.impl;

import com.wan.sys.entity.file.UploadFailed;
import com.wan.sys.entity.file.UploadFileResult;
import com.wan.sys.entity.file.UploadSuccess;
import com.wan.sys.pojo.ErrorCodeEnum;
import com.wan.sys.service.file.IFileService;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.poi.hpsf.Thumbnail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

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
            String sub = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
            String loc = location + sep + sub;
            File dir = new File(loc);
            if (!dir.exists()) { dir.mkdirs(); }
            String originalFileName = file.getOriginalFilename();

            //保留文件原后缀名
            int index = originalFileName.lastIndexOf(".");
            String newFileName;
            //处理无后缀文件和有后缀文件名
            if (index > 0) {
                String extension = originalFileName.substring(index);
                newFileName = UUID.randomUUID() + extension;
            } else {
                newFileName = UUID.randomUUID().toString();
            }

            String absFilePath = dir.getAbsolutePath() + sep + newFileName;
            File serverFile = new File(absFilePath);
            try {
                file.transferTo(serverFile);
                //同时保存一份缩略图
                Thumbnails.of(serverFile).forceSize(50, 50).outputFormat("jpg").toFile(absFilePath + "_50x50");
                return new UploadSuccess(path + sep + sub + sep + newFileName);
            } catch (IOException ex) {
                return new UploadFailed(ErrorCodeEnum.FAIL_IOERR);
            }
        }
        return new UploadFailed(ErrorCodeEnum.FAIL_NULLFILE);
    }
}
