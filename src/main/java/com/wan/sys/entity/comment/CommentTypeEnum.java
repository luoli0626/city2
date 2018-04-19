package com.wan.sys.entity.comment;

public enum CommentTypeEnum {

    DYNAMIC(1), FORUM(2);

    private int index;

    CommentTypeEnum(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return String.valueOf(index);
    }
}
