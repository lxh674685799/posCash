package com.soft.laboratory.model.laboratory;

import com.soft.core.model.BaseModel;
import com.soft.laboratory.model.user.SysOrg;
import com.soft.laboratory.model.user.SysUser;

public class Laboratory extends BaseModel {

	private static final long serialVersionUID = 1L;
	/** 实验室ID*/
	private String id;
	/** 实验室名称*/
	private String name;
	/** 实验室创建时间*/
	private String bulidTime;
	/** 实验室负责人*/
	private String principalName;
	private String principalId;
	private SysUser principal;
	/** 实验室所属组织*/
	private String postName;
	private String postId;
	private SysOrg post;
	/** 实验室备注*/
	private String remark;
	/** 实验室状态*/
	private Integer status;
	
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
	public String getBulidTime() {
		return bulidTime;
	}
	public void setBulidTime(String bulidTime) {
		this.bulidTime = bulidTime;
	}
	public SysUser getPrincipal() {
		return principal;
	}
	public void setPrincipal(SysUser principal) {
		this.principal = principal;
	}

	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPrincipalName() {
		return principalName;
	}
	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public SysOrg getPost() {
		return post;
	}
	public void setPost(SysOrg post) {
		this.post = post;
	}
	public String getPrincipalId() {
		return principalId;
	}
	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}
	public String getPostName() {
		return postName;
	}
	public void setPostName(String postName) {
		this.postName = postName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
