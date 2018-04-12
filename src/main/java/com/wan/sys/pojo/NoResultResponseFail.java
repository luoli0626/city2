package com.wan.sys.pojo;

import java.util.HashMap;

public class NoResultResponseFail extends ResponseHead {

    private volatile static NoResultResponseFail singleton;

    private NoResultResponseFail() {}

    public static NoResultResponseFail Instance() {

        //双重校验锁实现单例模式
        if (singleton == null) {
            synchronized (NoResultResponseFail.class) {
                if (singleton == null) {
                    singleton = new NoResultResponseFail();
                    singleton.setErrmsg(ErrorCodeEnum.FAIL_NORESULT.getValue());
                    singleton.setErrcode(ErrorCodeEnum.FAIL_NORESULT.getCode());
                    singleton.setData(new HashMap<String, String>() {});
                }
            }
        }

        return singleton;
    }
}
