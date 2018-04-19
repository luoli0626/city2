package com.wan.sys.entity.cityManager;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.wan.sys.common.BaseEntity;

@Entity
@Table(name="city_part_to_state")
public class PartToState extends BaseEntity{
	private String name;
	private Long belongId;
	private String content;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name="BELONG_ID")
	public Long getBelongId() {
		return belongId;
	}
	public void setBelongId(Long belongId) {
		this.belongId = belongId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
