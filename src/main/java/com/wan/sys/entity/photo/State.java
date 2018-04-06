package com.wan.sys.entity.photo;

public enum State implements Comparable<State>{

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
