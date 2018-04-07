package com.wan.sys.entity.comment;

import com.wan.sys.entity.common.Query;

public class CommentQuery extends Query {

    private Long dynamicId;

    public Long getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(Long dynamicId) {
        this.dynamicId = dynamicId;
    }
}
