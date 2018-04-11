package com.wan.sys.entity.file;

import com.wan.sys.pojo.ErrorCodeEnum;

public class UploadSuccess extends UploadFileResult {

    public UploadSuccess(String path) {
        this.setErrcode(ErrorCodeEnum.SUCCESS.getCode());
        this.setErrmsg(ErrorCodeEnum.SUCCESS.getValue());
        this.setPath(path);
    }
}
