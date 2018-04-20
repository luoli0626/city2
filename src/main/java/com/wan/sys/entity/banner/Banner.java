package com.wan.sys.entity.banner;

import com.wan.sys.common.BaseEntity;
import com.wan.sys.entity.image.Image;

import javax.persistence.*;

@Entity
@Table(name = "city_banner")
public class Banner extends BaseEntity {

    private String isUrl;
    private String urlAddress;
    private Long articleId;
    private String articleType;
    private Integer orderNumber;
    private Image image;

    public String getIsUrl() {
        return isUrl;
    }

    public void setIsUrl(String isUrl) {
        this.isUrl = isUrl;
    }

    public String getUrlAddress() {
        return urlAddress;
    }

    public void setUrlAddress(String urlAddress) {
        this.urlAddress = urlAddress;
    }

    @Column(name = "article_id")
    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    @Column(name = "article_type")
    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Transient
    public Image getImage() {
        return image;
    }

}
