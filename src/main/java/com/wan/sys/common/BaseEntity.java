package com.wan.sys.common;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import com.alibaba.fastjson.annotation.JSONField;
import com.wan.sys.entity.CreateModifiedable;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

/**
 * 
* 文件名称： 实体基类
 * 内容摘要： 实体包含的通用基本信息
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
//JPA基类标识
@MappedSuperclass
public class BaseEntity implements Serializable, CreateModifiedable {
	
	/***/
	private static final long serialVersionUID = 5530323836271860201L;

	/** 主键 */
	protected Long id;
	
	/** 删除状态 */
	protected String recordStatus = "Y";
	
	/** 创建人id */
	protected Long createUserId;
	
	/** 创建时间 */
	@Temporal(TemporalType.TIMESTAMP)
	protected Date createTime;
	
	/** 最近修改人id */
	protected Long modifyUserId;
	
	/** 最近修改时间 */
	@Temporal(TemporalType.TIMESTAMP)
	protected Date modifyTime;
	
	

	@Id
	@GeneratedValue(generator = "pk")
	@GenericGenerator(name = "pk", strategy = "com.wan.sys.util.IdGenerator")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(length=1)
	public String getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getModifyUserId() {
		return modifyUserId;
	}

	public void setModifyUserId(Long modifyUserId) {
		this.modifyUserId = modifyUserId;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public BaseEntity() {

	}

}
