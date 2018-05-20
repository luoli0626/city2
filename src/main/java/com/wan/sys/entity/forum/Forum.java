package com.wan.sys.entity.forum;

import com.wan.sys.common.BaseEntity;
import com.wan.sys.entity.User;
import com.wan.sys.entity.cityManager.PartToState;
import com.wan.sys.entity.image.Image;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "city_forum")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Forum extends BaseEntity{

    private String title;
    private String subtitle;
    private String content;
    private String isCheck = "D";
    private Set<Image> images;
    private Long viewCount = 0L;
    private Long commentCount = 0L;
    private Set<PartToState> allState;
    private User createUserName;
    
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "CREATEUSERID", updatable = false, insertable = false)
    public User getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(User createUserName) {
		this.createUserName = createUserName;
	}

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

    @OneToMany(cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "BELONG_ID")
    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    @Formula("( select count(*) from city_view t where t.BELONGID=id and t.TYPE=2 and t.RECORDSTATUS='Y' )")
    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    @Formula("( select count(*) from city_comment t where t.BELONGID=id and t.TYPE=2 and t.RECORDSTATUS='Y' )")
    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }
    
	@OneToMany(targetEntity=PartToState.class,cascade=CascadeType.ALL,fetch = FetchType.LAZY)  
	@Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name="BELONG_ID",updatable=false)  
	public Set<PartToState> getAllState() {
		return allState;
	}

	public void setAllState(Set<PartToState> allState) {
		this.allState = allState;
	}
}
