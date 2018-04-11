package com.wan.sys.service.file;

import com.wan.sys.entity.file.UploadFileResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IFileService {

    List<UploadFileResult> uploadFiles(MultipartFile[] files);
}
