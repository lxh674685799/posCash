package com.soft.laboratory.model.device;

import com.soft.core.model.BaseModel;
import com.soft.laboratory.model.user.SysOrg;
import com.soft.laboratory.model.user.SysUser;

/**
 * 设备发放
 * @author 刘旭辉
 *
 */
public class DeviceGet extends BaseModel{

	private static final long serialVersionUID = 1L;
	
	/** 设备发放ID*/
	private String id;
	/** 发放设备ID*/
	private String deviceIds;
	/** 设备接收人ID*/
	private String getUserId;
	private SysUser getUser;
	/** 设备接收单位ID*/
	private String getPostId;
	private SysOrg getPost;
	/** 设备发放人ID*/
	private String sendUserId;
	private SysUser sendUser;
	/**设备发放时间*/
	private String getDate;
	/** 设备发放状态*/
	private Integer status;
	/** 设备发放用途*/
	private String useFor;
	/** 设备发放备注*/
	private String remark;
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
	public String getGetUserId() {
		return getUserId;
	}
	public void setGetUserId(String getUserId) {
		this.getUserId = getUserId;
	}
	public SysUser getGetUser() {
		return getUser;
	}
	public void setGetUser(SysUser getUser) {
		this.getUser = getUser;
	}
	public String getGetPostId() {
		return getPostId;
	}
	public void setGetPostId(String getPostId) {
		this.getPostId = getPostId;
	}
	public SysOrg getGetPost() {
		return getPost;
	}
	public void setGetPost(SysOrg getPost) {
		this.getPost = getPost;
	}
	public String getSendUserId() {
		return sendUserId;
	}
	public void setSendUserId(String sendUserId) {
		this.sendUserId = sendUserId;
	}
	public SysUser getSendUser() {
		return sendUser;
	}
	public void setSendUser(SysUser sendUser) {
		this.sendUser = sendUser;
	}
	public String getGetDate() {
		return getDate;
	}
	public void setGetDate(String getDate) {
		this.getDate = getDate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getUseFor() {
		return useFor;
	}
	public void setUseFor(String useFor) {
		this.useFor = useFor;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
