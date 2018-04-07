package com.wan.sys.controller.file;

import com.wan.sys.pojo.ResponseHead;
import com.wan.sys.service.file.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("cityApp")
public class FileController {

    @Autowired
    IFileService fileService;

    @ResponseBody
    @RequestMapping("upload")
    public ResponseHead upload(@RequestParam("file") MultipartFile file) {

        ResponseHead r = new ResponseHead();

        if (file == null || file.isEmpty()) {
            r.setErrmsg("File can not be null or empty.");
            return r;
        }

        try {
            r.setData(fileService.uploadFile(file));
        } catch (IOException ex) {
            r.setErrmsg(ex.getMessage());
        }

        return r;
    }
}
