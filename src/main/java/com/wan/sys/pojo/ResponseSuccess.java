package com.wan.sys.pojo;

public class ResponseSuccess extends ResponseHead{

    private volatile static ResponseSuccess singleton;

    private ResponseSuccess() {}

    public static ResponseSuccess Instance() {

        //双重校验锁实现单例模式
        if (singleton == null) {
            synchronized (ResponseSuccess.class) {
                if (singleton == null) {
                    singleton = new ResponseSuccess();
                    singleton.setData("Success");
                }
            }
        }

        return singleton;
    }
}
