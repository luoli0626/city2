package com.wan.sys.entity.comment;

import com.wan.sys.common.BaseEntity;
import com.wan.sys.entity.CreateModifiedable;
import com.wan.sys.entity.image.Image;
import com.wan.sys.entity.user.UserInfo;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "city_comment")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment extends BaseEntity {

    private String content;
    private String type;
    private Long belongId;
    private UserInfo userInfo;

    @NotNull(message = "{message.notnull}")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @NotNull(message = "{message.notnull}")
    public Long getBelongId() {
        return belongId;
    }

    public void setBelongId(Long dynamicId) {
        this.belongId = dynamicId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "createUserId", referencedColumnName = "id", updatable = false, insertable = false)
    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
