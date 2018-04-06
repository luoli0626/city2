package com.wan.sys.pojo;

import java.math.BigDecimal;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.wan.sys.common.BaseEntity;

/**
 * 菜单模型
 * 
 * @author  
 * 
 */
@SuppressWarnings("serial")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer" })
public class MenuBean extends BaseEntity implements java.io.Serializable {

	
	private String text;
	private String iconCls;
	private String state;
	private String checked;
	private String src;
	private BigDecimal seq;
	private String func;//0为菜单1为功能点
	private String _parentId;
	private Long parentId;
	private String parentText;
	private List<MenuBean> children;
	public String getParentText() {
		return parentText;
	}

	public void setParentText(String parentText) {
		this.parentText = parentText;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public BigDecimal getSeq() {
		return seq;
	}

	public void setSeq(BigDecimal seq) {
		this.seq = seq;
	}

	public String getFunc() {
		return func;
	}

	public void setFunc(String func) {
		this.func = func;
	}

	public String get_parentId() {
		return _parentId;
	}

	public void set_parentId(String id) {
		_parentId = id;
	}
	@JsonIgnore
	public List<MenuBean> getChildren() {
		return children;
	}

	public void setChildren(List<MenuBean> children) {
		this.children = children;
	}

}
