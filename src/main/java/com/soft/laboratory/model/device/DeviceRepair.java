package com.soft.laboratory.model.device;

import com.soft.core.model.BaseModel;
import com.soft.laboratory.model.user.SysUser;

/**
 * 设备维修实体
 * @author 刘旭辉
 *
 */
public class DeviceRepair extends BaseModel{

	private static final long serialVersionUID = 1L;
	
	/** 设备维修ID*/
	private String id;
	/** 设备Id*/
	private String deviceIds;
	/** 设备维修人员*/
	private String repairUserId;
	private String repairUserName;
	private SysUser repairUser;
	/** 设备维修时间*/
	private String repairDate;
	/** 设备报修时间*/
	private String sendDate;
	/** 设备报修人员*/
	private String sendUserId;
	private SysUser sendUser;
	/** 设备维修状态*/
	private Integer repairStatus;	
	/** 设备维修备注 */
	private String remark;
	

	public SysUser getRepairUser() {
		return repairUser;
	}
	public void setRepairUser(SysUser repairUser) {
		this.repairUser = repairUser;
	}
	public SysUser getSendUser() {
		return sendUser;
	}
	public void setSendUser(SysUser sendUser) {
		this.sendUser = sendUser;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRepairUserId() {
		return repairUserId;
	}
	public void setRepairUserId(String repairUserId) {
		this.repairUserId = repairUserId;
	}
	public String getRepairDate() {
		return repairDate;
	}
	public void setRepairDate(String repairDate) {
		this.repairDate = repairDate;
	}
	public String getSendDate() {
		return sendDate;
	}
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}
	public Integer getRepairStatus() {
		return repairStatus;
	}
	public void setRepairStatus(Integer repairStatus) {
		this.repairStatus = repairStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSendUserId() {
		return sendUserId;
	}
	public void setSendUserId(String sendUserId) {
		this.sendUserId = sendUserId;
	}
	public String getDeviceIds() {
		return deviceIds;
	}
	public void setDeviceIds(String deviceIds) {
		this.deviceIds = deviceIds;
	}
	public String getRepairUserName() {
		return repairUserName;
	}
	public void setRepairUserName(String repairUserName) {
		this.repairUserName = repairUserName;
	}

}
