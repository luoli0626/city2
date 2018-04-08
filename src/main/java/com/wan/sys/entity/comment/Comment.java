package com.wan.sys.entity.comment;

import com.wan.sys.common.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "city_comment")
public class Comment extends BaseEntity{

    private String content;

    private Long dynamicId;

    @NotNull(message = "{message.notnull}")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "DYNAMIC_ID")
    @NotNull(message = "{message.notnull}")
    public Long getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(Long dynamicId) {
        this.dynamicId = dynamicId;
    }
}
