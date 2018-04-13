package com.wan.sys.pojo;

public class ResponseSuccess extends ResponseHead {

    public ResponseSuccess(Object data) {
        this.setData(data);
        this.setErrmsg(ErrorCodeEnum.SUCCESS.getValue());
        this.setErrcode(ErrorCodeEnum.SUCCESS.getCode());
    }
}
