package com.wan.sys.entity;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.wan.sys.common.BaseEntity;
import com.wan.sys.entity.organization.Organization;

/**
 * 
 * 文件名称： 用户与部门关联实体
 * 内容摘要： 
 * 创建人： 唐君左
 * 创建日期： 2017-6-26
 * 版本号： v1.0.0
 * 公  司：金科物业服务有限公司
 * 版权所有： (C)2016-2017     
 * 修改记录1 
 * 修改日期：
 * 版本号：
 * 修改人：
 * 修改内容：  
 *
 */
@Entity
@Table(name = "sys_user_project")
public class TUserProject  extends BaseEntity  implements java.io.Serializable {

	private User userId;
	private Organization projectId;

	/** default constructor */
	public TUserProject() {
	}



	/** full constructor */
	public TUserProject( User userId, Organization projectId) {
		
		this.userId = userId;
		this.projectId = projectId;
	}



	@OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name="USER_ID")
	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	@OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name="PROJECT_ID")
	public Organization getProjectId() {
		return projectId;
	}

	public void setProjectId(Organization projectId) {
		this.projectId = projectId;
	}
	
	
	
	
	
	

	
}