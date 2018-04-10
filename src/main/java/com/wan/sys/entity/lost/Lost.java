package com.wan.sys.entity.lost;

import com.wan.sys.common.BaseEntity;
import com.wan.sys.entity.image.Image;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "city_lost")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Lost extends BaseEntity{

    private String content;

    private List<Image> images;

    private String contact;

    @NotNull(message = "{message.notnull}")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Transient
    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    @NotNull(message = "{message.notnull}")
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
