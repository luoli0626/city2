package com.wan.sys.entity.guide;

import com.wan.sys.common.BaseEntity;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "city_guide")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Guide extends BaseEntity{

    private String title;

    private String subtitle;

    private String content;

    private String isOnline;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "IS_ONLINE")
    @NotNull(message = "{message.notnull}")
    public String getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(String isOnline) {
        this.isOnline = isOnline;
    }
}
