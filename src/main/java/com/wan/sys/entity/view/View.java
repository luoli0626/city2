package com.wan.sys.entity.view;

import com.wan.sys.common.BaseEntity;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "city_view")
@JsonIgnoreProperties(ignoreUnknown = true)
public class View extends BaseEntity {

    private Long belongId;
    private String type;

    public Long getBelongId() {
        return belongId;
    }

    public void setBelongId(Long forumId) {
        this.belongId = forumId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
