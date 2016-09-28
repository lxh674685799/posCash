package com.soft.laboratory.model.goods;

import com.soft.core.model.BaseModel;

public class GoodsInfo  extends BaseModel{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 条码
	 */
	private String code;
	/**
	 * 商品名称
	 */
	private String name;
	
	/**
	 * 销售价 现金
	 */
	private String money;
	/**
	 * 销售价 积分
	 */
	private String  credit;
	/**
	 * 商品的付款方式
	 */
	private String payType;
	
	/**
	 * 数量
	 */
	private  String number;
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	
}
