package com.wan.sys.entity.comment;

import com.wan.sys.entity.common.Query;

public class CommentQuery extends Query {

    private Long belongId;
    private String type;

    public Long getBelongId() {
        return belongId;
    }

    public void setBelongId(Long id) {
        this.belongId = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
