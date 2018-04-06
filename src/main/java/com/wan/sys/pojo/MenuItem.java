package com.wan.sys.pojo;

import java.util.List;



/**
 * 页面上显示的菜单项，每一个MenuItem对应一个节点
 */
public class MenuItem implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;// 0：根节点，否则是子节点
	private String pid;// 菜单项的父亲节点
	private String url;// 菜单的URL地址
	private String target = "rightFrame";// 打开的目标
	private String name;// 菜单名称
	private boolean open = false;// 是否展开
	private boolean checked;// true:勾选
	private List<MenuItem> children;// 子节点
	private String icon;// 节点图标
	

	

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<MenuItem> getChildren() {
		return children;
	}

	public void setChildren(List<MenuItem> children) {
		this.children = children;
	}

	public MenuItem(String name, List<MenuItem> children) {
		super();
		this.name = name;
		this.children = children;
	}

	public MenuItem() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}


}
