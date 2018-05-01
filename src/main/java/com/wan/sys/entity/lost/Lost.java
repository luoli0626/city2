package com.wan.sys.entity.lost;

import com.wan.sys.common.BaseEntity;
import com.wan.sys.entity.User;
import com.wan.sys.entity.cityManager.PartToState;
import com.wan.sys.entity.image.Image;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "city_lost")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Lost extends BaseEntity{

    private String content;
    private Set<Image> images;
    private String contact;
    private User createUserName;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "CREATEUSERID", updatable = false, insertable = false)
    public User getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(User createUserName) {
		this.createUserName = createUserName;
	}

    @NotNull(message = "{message.notnull}")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @OneToMany(cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "BELONG_ID")
    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    @NotNull(message = "{message.notnull}")
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
