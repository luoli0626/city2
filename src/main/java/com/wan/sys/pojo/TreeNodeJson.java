package com.wan.sys.pojo;

import java.util.List;
import java.util.Map;

/**
 * 
 * <p>Title: </p>
 * <p>Description:easyui使用的tree模型 id_是Long类型的 </p>
 * <p>Company:重庆亚德科技 </p>
 * @author xiangguiyin create 2012-12-26
 * @version 0.1
 *
 */
public class TreeNodeJson {
	
	private Long id;
	private String text;// 树节点名称
	private String iconCls;// 前面的小图标样式
	private Boolean checked = false;// 是否勾选状态
	private Map<String, Object> attributes;// 其他参数
	private List<TreeNodeJson> children;// 子节点
	private String state = "open";// 是否展开(open,closed)

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public List<TreeNodeJson> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNodeJson> children) {
		this.children = children;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

}
