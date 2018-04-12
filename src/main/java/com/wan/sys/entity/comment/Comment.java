package com.wan.sys.entity.comment;

import com.wan.sys.common.BaseEntity;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "city_comment")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment extends BaseEntity{

    private String content;
    private String type;
    private Long belongId;

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
}
