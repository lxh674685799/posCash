package com.soft.laboratory.model.device;

import com.soft.core.model.BaseModel;
import com.soft.laboratory.model.user.SysOrg;
import com.soft.laboratory.model.user.SysUser;

/**
 * 设备借出
 * @author 刘旭辉
 *
 */
public class DeviceLoan extends BaseModel{

	private static final long serialVersionUID = 1L;

	/**
	 * 设备借出ID
	 */
	private String id;
	/**
	 * 设备ID
	 */
	private String deviceIds;
	/** 借出人信息*/
	private String loanUserId;
	private SysUser loanUser;
	/** 借给人信息*/
	private String loanedUserId;
	private String loanedUserName;
	private SysUser loanedUser;
	/** 借出时间*/
	private String loanTime;
	/** 归还时间*/
	private String returnTime;
	/** 状态*/
	private Integer status;
	/** 备注*/
	private String remark;
	/** 借出理由*/
	private String forWhat;
	/** 借到什么地方*/
	private String postId;
	private SysOrg post;
	/** 实际归还时间*/
	private String trueReturnTime;
	
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDeviceIds() {
		return deviceIds;
	}
	public void setDeviceIds(String deviceIds) {
		this.deviceIds = deviceIds;
	}

	public String getLoanUserId() {
		return loanUserId;
	}
	public void setLoanUserId(String loanUserId) {
		this.loanUserId = loanUserId;
	}
	public SysUser getLoanUser() {
		return loanUser;
	}
	public void setLoanUser(SysUser loanUser) {
		this.loanUser = loanUser;
	}
	public String getLoanedUserId() {
		return loanedUserId;
	}
	public void setLoanedUserId(String loanedUserId) {
		this.loanedUserId = loanedUserId;
	}
	public SysUser getLoanedUser() {
		return loanedUser;
	}
	public void setLoanedUser(SysUser loanedUser) {
		this.loanedUser = loanedUser;
	}
	public String getLoanTime() {
		return loanTime;
	}
	public void setLoanTime(String loanTime) {
		this.loanTime = loanTime;
	}
	public String getReturnTime() {
		return returnTime;
	}
	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getForWhat() {
		return forWhat;
	}
	public void setForWhat(String forWhat) {
		this.forWhat = forWhat;
	}
	public String getTrueReturnTime() {
		return trueReturnTime;
	}
	public void setTrueReturnTime(String trueReturnTime) {
		this.trueReturnTime = trueReturnTime;
	}
	public String getLoanedUserName() {
		return loanedUserName;
	}
	public void setLoanedUserName(String loanedUserName) {
		this.loanedUserName = loanedUserName;
	}

}
