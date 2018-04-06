package com.wan.sys.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Where;

import com.wan.sys.common.BaseEntity;
import com.wan.sys.common.GlobalConstant;

/**
 * 
 * 文件名称： 菜单实体
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
@Table(name = "sys_menu")
@Where(clause="recordStatus='"+GlobalConstant.FLAG_Y+"'")
public class Menu extends BaseEntity {
	/***/
	private static final long serialVersionUID = 2402552806318950382L;
	private String text;
	private String iconcls;
	private String function;// 0为菜单1为功能点
	private String src;
	private BigDecimal seq;
	private Long pid;
	private Boolean check;
	private String applicationId;//所属应用
	private String funb;// 0为菜单1为功能点
	@Column(length=100)
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}
	@Column(length=50)
	public String getIconcls() {
		return this.iconcls;
	}

	public void setIconcls(String iconcls) {
		this.iconcls = iconcls;
	}
	@Column(length=200)
	public String getSrc() {
		return this.src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public BigDecimal getSeq() {
		return this.seq;
	}

	public void setSeq(BigDecimal seq) {
		this.seq = seq;
	}
	@Column(name="func",length=50)
	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}
	/**
	 * 菜单是否选中状态
	 */
	@Transient
	public Boolean isCheck() {
		return check;
	}

	public void setCheck(Boolean check) {
		this.check = check;
	}
	

	@Column(name="APPLICATION_ID",length=50)
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	@Column(name="funb",length=50)
	public String getFunb() {
		return funb;
	}

	public void setFunb(String funb) {
		this.funb = funb;
	}
}