package com.wan.sys.entity.common;

import org.hibernate.validator.constraints.Range;

public class Query {

    @Range(min = 1, message = "{message.notlt}{min}")
    private int page = 0;

    @Range(min = 1, message = "{message.notlt}{min}")
    private int rows = 0;

    private boolean online;
    private Long createUserId;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }
}
