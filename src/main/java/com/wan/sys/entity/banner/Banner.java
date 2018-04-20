package com.wan.sys.entity.banner;

import com.wan.sys.common.BaseEntity;
import com.wan.sys.entity.image.Image;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "city_banner")
public class Banner extends BaseEntity {

    private String isUrl;
    private String urlAddress;
    private Long articleId;
    private String articleType;
    private Integer orderNumber;
    private Set<Image> bannerImages;
    private Set<Image> articleImages;
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

    @OneToMany(cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "BELONG_ID", updatable = false, insertable = false)
    public Set<Image> getBannerImages() {
        return bannerImages;
    }

    @OneToMany(cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "BELONG_ID", referencedColumnName = "article_id", updatable = false, insertable = false)
    public Set<Image> getArticleImages() {
        return articleImages;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Transient
    public Image getImage() {
        return image;
    }

    public void setBannerImages(Set<Image> bannerImages) {
        this.bannerImages = bannerImages;
    }

    public void setArticleImages(Set<Image> articleImages) {
        this.articleImages = articleImages;
    }
}
