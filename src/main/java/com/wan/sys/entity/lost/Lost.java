package com.wan.sys.entity.lost;

import com.wan.sys.common.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "city_lost")
public class Lost extends BaseEntity{

    @NotNull(message = "{message.notnull}")
    private String content;

    private String image;

    @NotNull(message = "{message.notnull}")
    private String contact;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
