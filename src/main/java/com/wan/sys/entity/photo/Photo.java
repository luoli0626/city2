package com.wan.sys.entity.photo;

import com.wan.sys.common.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "city_photo")
public class Photo extends BaseEntity {

    private String state;
    private String content;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
