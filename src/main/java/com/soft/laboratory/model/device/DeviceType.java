package com.soft.laboratory.model.device;

import com.soft.core.model.BaseModel;

/**
 * 设备类型实体
 * @author 刘旭辉
 *
 */
public class DeviceType extends BaseModel{
	
	private static final long serialVersionUID = -2403495739267154694L;
	/**
	 * ID
	 */
	private String id;
	/**
	 * 类别名称
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
	 * 备注
	 */
	private String remark;
	/**
	 * 路径
	 */
	private String path;
	/**
	 * 分类号
	 */
	private String classNumber;
	

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
	public String getClassNumber() {
		return classNumber;
	}
	public void setClassNumber(String classNumber) {
		this.classNumber = classNumber;
	}

}
