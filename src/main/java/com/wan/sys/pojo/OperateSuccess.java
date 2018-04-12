package com.wan.sys.pojo;

import java.util.HashMap;

/**
 * @author J
 * 操作成功单例类
 */
public class OperateSuccess extends ResponseHead{

    private volatile static OperateSuccess singleton;

    private OperateSuccess() {}

    public static OperateSuccess Instance() {

        //双重校验锁实现单例模式
        if (singleton == null) {
            synchronized (OperateSuccess.class) {
                if (singleton == null) {
                    singleton = new OperateSuccess();
                    singleton.setErrmsg(ErrorCodeEnum.SUCCESS.getValue());
                    singleton.setErrcode(ErrorCodeEnum.SUCCESS.getCode());
                    singleton.setData(new HashMap<String, String>() {});
                }
            }
        }

        return singleton;
    }
}
