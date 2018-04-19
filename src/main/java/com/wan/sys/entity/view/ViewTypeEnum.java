package com.wan.sys.entity.view;

public enum ViewTypeEnum {

    DYNAMIC(1), FORUM(2), MESSAGE(3);

    private int index;

    ViewTypeEnum(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return String.valueOf(index);
    }
}
