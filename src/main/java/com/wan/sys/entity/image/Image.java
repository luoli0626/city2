package com.wan.sys.entity.image;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "city_part_to_images")
public class Image {

    private Long id;
    private String address;
    private String type;
    private int order;

    @Column(name = "BELONG_ID")
    private long belongId;

    @Id
    @GeneratedValue(generator = "pk")
    @GenericGenerator(name = "pk", strategy = "com.wan.sys.util.IdGenerator")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public long getBelongId() {
        return belongId;
    }

    public void setBelongId(long belongId) {
        this.belongId = belongId;
    }
}
