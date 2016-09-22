package com.soft.laboratory.model.user;

import java.util.List;

import com.soft.core.model.BaseModel;
import com.soft.laboratory.model.SysResource;

/**
 * 系统角色实体
 * @author 翟瑞东
 *
 */
public class SysRole extends BaseModel {
	
	private static final long serialVersionUID = 1L;
	
	/** 
	 * 角色的ID
	 */
	private String id;
	/** 
	 * 角色名称
	 */
	private String name;
	/** 
	 * 角色说明
	 */
	private String remark;
	
	private List<SysResource> resources;
	
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public List<SysResource> getResources() {
		return resources;
	}
	public void setResources(List<SysResource> resources) {
		this.resources = resources;
	}


}
