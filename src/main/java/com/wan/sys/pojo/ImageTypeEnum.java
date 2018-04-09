package com.wan.sys.pojo;

/**
 * 图片类型枚举
 */
public enum ImageTypeEnum {

    MESSAGE("1"),
    PHOTO("2"),
    DYNAMIC("3"),
    AVATAR("4"),
    LOST("5"),
    FORUM("6");

    private String index;

    ImageTypeEnum(String index) {
        this.index = index;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
