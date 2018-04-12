package com.wan.sys.entity.common;

public enum StateEnum {

    WAITING(0), PROCESSING(1), END(2);

    private int index;

    StateEnum(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return String.valueOf(index);
    }
}
