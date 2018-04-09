package com.wan.sys.service.file;

import com.wan.sys.entity.file.UploadFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFileService {

    UploadFile uploadFile(MultipartFile file) throws IOException;
}
