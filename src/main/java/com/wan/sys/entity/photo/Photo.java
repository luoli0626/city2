package com.wan.sys.entity.photo;

import com.wan.sys.common.BaseEntity;
import com.wan.sys.entity.image.Image;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "city_photo")
public class Photo extends BaseEntity implements Serializable {

    private String state;

    @NotNull(message = "{message.notnull}")
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


    private List<Image> images;

    @Transient
    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    @PrePersist
    public void onCreate() {
        createTime = new Date();
    }
}
