package com.soft.laboratory.model.user;

import com.soft.core.model.BaseModel;

/**
 * 系统组织机构实体类
 * @author 翟瑞东
 *
 */
public class SysOrg extends BaseModel {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 组织机构ID
	 */
	private String id;
	/**
	 * 组织机构代码
	 */
	private String code;
	/**
	 * 组织机构名称
	 */
	private String name;
	/**
	 * 父ID
	 */
	private String parentId;
	/**
	 * 是否为父节点
	 */
	private boolean isParent;
	/**
	 * 所在地
	 */
	private String address;
	/**
	 * 负责人
	 */
	private String user;
	/**
	 * 联系电话
	 */
	private String phone;	
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 路径
	 */
	private String path;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public boolean getisParent() {
		return isParent;
	}
	public void setisParent(boolean isParent) {
		this.isParent = isParent;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
