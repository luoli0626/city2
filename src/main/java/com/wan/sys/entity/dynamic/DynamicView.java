package com.wan.sys.entity.dynamic;

import com.wan.sys.common.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "city_dynamic_view")
public class DynamicView extends BaseEntity{

    private String dynamicId;

    public String getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(String dynamicId) {
        this.dynamicId = dynamicId;
    }
}
