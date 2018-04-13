package com.wan.sys.entity.dynamic;

import com.wan.sys.common.BaseEntity;
import com.wan.sys.entity.view.ViewTypeEnum;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Formula;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "city_dynamic")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Dynamic extends BaseEntity{

    private String title;
    private String subtitle;
    private String content;
    private String isOnline;
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

    @Column(name = "IS_ONLINE")
    public String getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(String isOnline) {
        this.isOnline = isOnline;
    }

    @Formula("( select count(*) from city_view t where t.BELONGID=id and t.TYPE=1 )")
    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    @Formula("( select count(*) from city_comment t where t.BELONGID=id and t.TYPE=1 )")
    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }
}
