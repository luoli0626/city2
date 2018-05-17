package com.wan.sys.entity.photo;

import com.wan.sys.common.BaseEntity;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "city_photo_type")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PhotoType extends BaseEntity {
    private String name;

    private String createUserName;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Transient
    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }
}
