package com.wan.sys.controller.file;

import com.wan.sys.pojo.ErrorCodeEnum;
import com.wan.sys.pojo.ResponseFail;
import com.wan.sys.pojo.ResponseHead;
import com.wan.sys.pojo.ResponseSuccess;
import com.wan.sys.service.file.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("cityApp/file")
public class FileController {

    @Autowired
    IFileService fileService;

    @ResponseBody
    @RequestMapping("upload")
    public ResponseHead upload(@RequestParam("files") MultipartFile[] files) {

        if (files != null || files.length > 0) {
            return new ResponseSuccess(fileService.uploadFiles(files));
        }
        return new ResponseFail(ErrorCodeEnum.FAIL_NULLFILE);
    }
}
