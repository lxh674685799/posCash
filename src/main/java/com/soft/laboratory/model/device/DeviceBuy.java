package com.soft.laboratory.model.device;

import com.soft.core.model.BaseModel;
import com.soft.laboratory.model.user.SysUser;

/**
 * 设备购入
 * @author 刘旭辉
 *
 */
public class DeviceBuy extends BaseModel{

	private static final long serialVersionUID = 1L;

	/**
	 * 设备购入ID
	 */
	private String id;
	/**
	 * 设备ID
	 */
	private String deviceId;
	private Device device;
	/**
	 * 购入数量
	 */
	private Long number;
	/**
	 * 购入价格
	 */
	private Double price;
	/**
	 * 购入总价格
	 */
	private String totalPrice;
	/**
	 * 购买人
	 */
	private String buyUserId;
	private SysUser buyUser;
	/**
	 * 审批人
	 */
	private String ratifyUserName;
	private String ratifyUserId;
	private SysUser ratifyUser;
	/**
	 * 购入时间
	 */
	private String buyTime;
	/**
	 * 提价审批时间
	 */
	private String ratifyTime;
	/**
	 * 审批时间
	 */
	private String doTime;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 附件
	 */
	private String fileId;	
	/**
	 * 备注
	 */
	private String remark;
	/*** 审批意见*/
	private String ratifyRemark;
	/*** 仪器来源*/
	private String deviceResource;
	/*** 经费来源*/
	private String financialResources;
	/**发票号**/
	private String billNumber;
	
	private Double amount;
	
	public String getRatifyRemark() {
		return ratifyRemark;
	}
	public void setRatifyRemark(String ratifyRemark) {
		this.ratifyRemark = ratifyRemark;
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
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getBuyUserId() {
		return buyUserId;
	}
	public void setBuyUserId(String buyUserId) {
		this.buyUserId = buyUserId;
	}
	public SysUser getBuyUser() {
		return buyUser;
	}
	public void setBuyUser(SysUser buyUser) {
		this.buyUser = buyUser;
	}
	public SysUser getRatifyUser() {
		return ratifyUser;
	}
	public void setRatifyUser(SysUser ratifyUser) {
		this.ratifyUser = ratifyUser;
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

	public String getRatifyUserName() {
		return ratifyUserName;
	}
	public void setRatifyUserName(String ratifyUserName) {
		this.ratifyUserName = ratifyUserName;
	}
	public String getRatifyUserId() {
		return ratifyUserId;
	}
	public void setRatifyUserId(String ratifyUserId) {
		this.ratifyUserId = ratifyUserId;
	}
	public String getBuyTime() {
		return buyTime;
	}
	public void setBuyTime(String buyTime) {
		this.buyTime = buyTime;
	}
	public String getRatifyTime() {
		return ratifyTime;
	}
	public void setRatifyTime(String ratifyTime) {
		this.ratifyTime = ratifyTime;
	}
	public String getDoTime() {
		return doTime;
	}
	public void setDoTime(String doTime) {
		this.doTime = doTime;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getDeviceResource() {
		return deviceResource;
	}
	public void setDeviceResource(String deviceResource) {
		this.deviceResource = deviceResource;
	}
	public String getFinancialResources() {
		return financialResources;
	}
	public void setFinancialResources(String financialResources) {
		this.financialResources = financialResources;
	}
	public String getBillNumber() {
		return billNumber;
	}
	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
}
