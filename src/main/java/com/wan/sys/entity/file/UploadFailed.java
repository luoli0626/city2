package com.wan.sys.entity.file;

import com.wan.sys.pojo.ErrorCodeEnum;
import org.apache.commons.lang.StringUtils;

public class UploadFailed extends UploadFileResult {

    public UploadFailed(ErrorCodeEnum error) {
        this.setErrcode(error.getCode());
        this.setErrmsg(error.getValue());
        this.setPath(StringUtils.EMPTY);
    }
}
