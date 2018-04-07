package com.wan.sys.entity.forum;

import com.wan.sys.common.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "city_forum")
public class Form extends BaseEntity{

    @NotNull(message = "{message.notnull}")
    private String title;

    @NotNull(message = "{message.notnull}")
    private String subtitle;

    @NotNull(message = "{message.notnull}")
    private String content;

    @Column(name = "IS_CHECK")
    private String isCheck;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(String isCheck) {
        this.isCheck = isCheck;
    }
}
