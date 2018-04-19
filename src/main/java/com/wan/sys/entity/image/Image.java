package com.wan.sys.entity.image;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "city_part_to_images")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Image implements Serializable {

    private Long id;
    private String address;
    private String type;
    private Integer order;
    private Long belongId;

    @Id
    @GeneratedValue(generator = "pk")
    @GenericGenerator(name = "pk", strategy = "com.wan.sys.util.IdGenerator")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull(message = "{message.notnull}")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "\"ORDER\"")
    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Column(name = "BELONG_ID")
    public Long getBelongId() {
        return belongId;
    }

    public void setBelongId(Long belongId) {
        this.belongId = belongId;
    }
}
