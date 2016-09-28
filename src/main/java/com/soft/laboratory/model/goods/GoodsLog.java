package com.soft.laboratory.model.goods;

import com.soft.core.model.BaseModel;

public class GoodsLog extends BaseModel{

	private static final long serialVersionUID = 1L;

	private String id;
	/**
	 * 商品信息
	 */
	private String goodsInfo;
	
	/**
	 * 结账方式 1 现金结账  2 积分结账  3  积分和现金结账
	 */
	private String  checkType;
	/**
	 * 总价
	 */
	private String countMoney;
	/**
	 * 总积分
	 */
	private String countCredit;
	/**
	 * 会员id
	 */
	private String memberUserId;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 *创建人
	 */
	private String createUserId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGoodsInfo() {
		return goodsInfo;
	}
	public void setGoodsInfo(String goodsInfo) {
		this.goodsInfo = goodsInfo;
	}
	public String getCheckType() {
		return checkType;
	}
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	public String getCountMoney() {
		return countMoney;
	}
	public void setCountMoney(String countMoney) {
		this.countMoney = countMoney;
	}
	public String getMemberUserId() {
		return memberUserId;
	}
	public void setMemberUserId(String memberUserId) {
		this.memberUserId = memberUserId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public String getCountCredit() {
		return countCredit;
	}
	public void setCountCredit(String countCredit) {
		this.countCredit = countCredit;
	}
	
	
	
}
