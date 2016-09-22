package com.soft.laboratory.model.device;

import com.soft.core.model.BaseModel;
import com.soft.laboratory.model.user.SysOrg;
import com.soft.laboratory.model.user.SysUser;

/**
 * 具体的设备
 * @author 刘旭辉
 *
 */
public class DeviceEntity extends BaseModel{

	private static final long serialVersionUID = 1L;	
	/** 具体设备ID*/
	private String id;
	/** 设备ID*/
	private String deviceId;
	private Device device;
	/** 设备编号*/
	private String number;
	/** 设备购入信息ID*/
	private String buyId;
	private DeviceBuy buy;
	/** 设备状态*/
	private Integer status;
	/** 设备事件*/
	private String eventId;
	/** 设备存放位置 */
	private String postId;
	private SysOrg post;
	/** 设备拥有者 */
	private String owerId;
	private SysUser ower;
	
	private String locationName;
	
	public String getOwerId() {
		return owerId;
	}
	public void setOwerId(String owerId) {
		this.owerId = owerId;
	}
	public SysUser getOwer() {
		return ower;
	}
	public void setOwer(SysUser ower) {
		this.ower = ower;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getBuyId() {
		return buyId;
	}
	public void setBuyId(String buyId) {
		this.buyId = buyId;
	}
	public DeviceBuy getBuy() {
		return buy;
	}
	public void setBuy(DeviceBuy buy) {
		this.buy = buy;
	}
	
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}	
}
