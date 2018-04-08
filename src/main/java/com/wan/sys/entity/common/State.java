package com.wan.sys.entity.common;

public enum State {

    WAITING(0), PROCESSING(1), END(2);

    public int index;

    State(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return String.valueOf(index);
    }
}
