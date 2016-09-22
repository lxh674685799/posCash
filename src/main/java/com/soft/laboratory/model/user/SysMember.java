package com.soft.laboratory.model.user;

import com.soft.core.model.BaseModel;

/**
 * 会员
 *  * @author 翟瑞东
 *
 */
public class SysMember extends BaseModel {
	
	private static final long serialVersionUID = 1L;
	/**
	 * 会员Id
	 */
	private String id;
	/**
	 * 会员姓名
	 */
	private String name;
	//会员编号
	private String memberNo;
	//会员积分
	private Double  valueMnu;
	//生日
	private String birthday;
	/**
	 * 用户性别 ture：男
	 */
	private boolean sex;
	/**
	 * 会员联系电话
	 */
	private String phone;
	/**
	 * 用户备注
	 */
	private String remark;
	
	
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
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	
	public Double getValueMnu() {
		return valueMnu;
	}
	public void setValueMnu(Double valueMnu) {
		this.valueMnu = valueMnu;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public boolean isSex() {
		return sex;
	}
	public void setSex(boolean sex) {
		this.sex = sex;
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

}
