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

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "city_photo")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Photo extends BaseEntity {

    private String state;
    private Set<Image> images;
    private String content;
    private User createUserName;
    private List<PartToState> allState;
    private Set<Handle> handles;
    private String addrName;
    private Long type;
    private String typeName;
    private String h5url;
    private String userDel="Y";

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
	public List<PartToState> getAllState() {
		return allState;
	}

	public void setAllState(List<PartToState> allState) {
		this.allState = allState;
	}

	
	@OneToMany(cascade = CascadeType.ALL)
	@Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name="BELONG_ID", updatable = false, insertable = false)
	public Set<Handle> getHandles() {
		return handles;
	}

	public void setHandles(Set<Handle> handles) {
		this.handles = handles;
	}

	@Column(name="ADDR_NAME")
	public String getAddrName() {
		return addrName;
	}

	public void setAddrName(String addrName) {
		this.addrName = addrName;
	}

	@Formula(" (select t.NAME from city_photo_type t where t.ID = TYPE )")
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    @Column(name="h5url")
	public String getH5url() {
		return h5url;
	}

	public void setH5url(String h5url) {
		this.h5url = h5url;
	}

	public String getUserDel() {
		return userDel;
	}

	public void setUserDel(String userDel) {
		this.userDel = userDel;
	}
    
	
}
