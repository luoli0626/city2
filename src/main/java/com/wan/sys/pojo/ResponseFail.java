package com.wan.sys.pojo;

public class ResponseFail extends ResponseHead {

    public ResponseFail(String errCode, String errMsg) {
        this.setErrmsg(errMsg);
        this.setErrcode(errCode);
    }

    public ResponseFail(ErrorCodeEnum e) {
        this(e.getCode(), e.getValue());
    }
}
