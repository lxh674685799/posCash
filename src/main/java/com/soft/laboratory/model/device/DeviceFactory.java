package com.soft.laboratory.model.device;

import com.soft.core.model.BaseModel;
import com.soft.core.poi.ExcelSign;
/**
 * 设备厂家实体类
 * @author 刘旭辉
 *
 */
public class DeviceFactory extends BaseModel{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 厂家ID
	 */
	private String id;
	/**
	 * 厂家名称
	 */
	private String name;
	/**
	 * 厂家地址
	 */
	private String address;
	/**
	 * 厂家固定电话
	 */
	private String phone;
	/**
	 * 厂家联系人姓名
	 */
	private String linkName;
	/**
	 * 厂家联系人电话
	 */
	private String mobile;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@ExcelSign(title="厂家名称",order=1)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@ExcelSign(title="厂家地址",order=2)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@ExcelSign(title="联系人",order=3)
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	@ExcelSign(title="联系人电话",order=4)
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@ExcelSign(title="厂家电话",order=4)
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Override
	public String toString() {
		return "DeviceFactory [id=" + id + ", name=" + name + ", address="
				+ address + ", phone=" + phone + ", linkName=" + linkName
				+ ", mobile=" + mobile + "]";
	}

}
