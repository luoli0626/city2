package com.wan.sys.entity.forum;

import com.wan.sys.common.BaseEntity;
import com.wan.sys.entity.image.Image;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "city_forum")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Forum extends BaseEntity{

    private String title;
    private String subtitle;
    private String content;
    private String isCheck;
    private List<Image> images;
    private Long viewCount;
    private Long commentCount;

    @NotNull(message = "{message.notnull}")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    @NotNull(message = "{message.notnull}")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "IS_CHECK")
    public String getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(String isCheck) {
        this.isCheck = isCheck;
    }

    @Transient
    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    @Transient
    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    @Transient
    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }
}
