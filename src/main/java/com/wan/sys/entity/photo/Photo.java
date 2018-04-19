package com.wan.sys.entity.photo;

import com.wan.sys.common.BaseEntity;
import com.wan.sys.entity.User;
import com.wan.sys.entity.cityManager.PartToState;
import com.wan.sys.entity.image.Image;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.IndexColumn;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "city_photo")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Photo extends BaseEntity {

    private String state;
    private Set<Image> images;
    private String content;
    private User createUserName;
    private Set<PartToState> allState;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "CREATEUSERID", updatable = false, insertable = false)
    public User getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(User createUserName) {
		this.createUserName = createUserName;
	}

	public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
    
	@OneToMany(cascade = CascadeType.ALL)
	@Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name="BELONG_ID")
	public Set<PartToState> getAllState() {
		return allState;
	}

	public void setAllState(Set<PartToState> allState) {
		this.allState = allState;
	}
    
}
