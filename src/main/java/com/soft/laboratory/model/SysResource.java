package com.soft.laboratory.model;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.soft.laboratory.model.user.SysRole;


/**
 * 资源列表实体类
 * @author 翟瑞东
 *
 */
public class SysResource implements Serializable {
	
	private static final long serialVersionUID = 7448745974279350649L;

	// 资源ID
	@Expose
	private String id;

	// 资源的标题
	@Expose
	private String title;
	
	// 资源的URI
	private String uri;
	
	// 资源图标的URI
	private String iconUri;
	
	// 资源的描述
	@Expose
	private String desc;

	// 上级资源的ID
	@Expose
	private String parentId;

	// 在同级中的序号，用来排序
	@Expose
	private int orderIndex;

	// 资源是否可访问，true代表可以访问，false代表不能访问
	@Expose
	private boolean checked=false;
	
	// 是否有下级资源,true代表有下级资源，false代表没有
	@Expose
	private boolean isParent;
	
	private List<SysRole> roles;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getIconUri() {
		return iconUri;
	}

	public void setIconUri(String iconUri) {
		this.iconUri = iconUri;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public int getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}

	public boolean getisParent() {
		return isParent;
	}

	public void setisParent(boolean isParent) {
		this.isParent = isParent;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public List<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}
}
