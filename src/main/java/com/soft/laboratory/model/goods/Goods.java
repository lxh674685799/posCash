package com.soft.laboratory.model.goods;

import com.soft.core.model.BaseModel;
import com.soft.laboratory.model.device.DeviceFactory;
import com.soft.laboratory.model.device.DeviceType;
import com.soft.laboratory.model.user.SysUser;

public class Goods extends BaseModel{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 商品id
	 */
	private String id;
	/**
	 * 条码
	 */
	private String code;
	/**
	 * 商品名称
	 */
	private String name;
	/**
	 * 商品类别
	 */
	private String typeId;
	private String typeName;
	private DeviceType type; 
	/**
	 * 库存
	 */
	private  String inventory;
	/**
	 * 进货价
	 */
	private Double inPrice;
	/**
	 * 销售价 现金
	 */
	private Double money;
	/**
	 * 销售价 积分
	 */
	private Double  credit;
	/**
	 * 销售价 现金和积分
	 */
	private Double  moneyCre;//现金
	private Double  creditMon;//积分
	
	/**
	 * 供货商
	 */
	private String factoryId;
	private String factoryName;
	private DeviceFactory factory;
	/**
	 * 创建时间
	 */
	private String  createTime;
	/**
	 * 创建人
	 */
	private String userId;
	private String userName;
	private SysUser user;
	/**
	 * 备注
	 */
	private String remark;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public DeviceType getType() {
		return type;
	}
	public void setType(DeviceType type) {
		this.type = type;
	}
	public Double getInPrice() {
		return inPrice;
	}
	public void setInPrice(Double inPrice) {
		this.inPrice = inPrice;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public Double getCredit() {
		return credit;
	}
	public void setCredit(Double credit) {
		this.credit = credit;
	}
	public String getFactoryId() {
		return factoryId;
	}
	public void setFactoryId(String factoryId) {
		this.factoryId = factoryId;
	}
	public String getFactoryName() {
		return factoryName;
	}
	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}
	public DeviceFactory getFactory() {
		return factory;
	}
	public void setFactory(DeviceFactory factory) {
		this.factory = factory;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public SysUser getUser() {
		return user;
	}
	public void setUser(SysUser user) {
		this.user = user;
	}
	public Double getMoneyCre() {
		return moneyCre;
	}
	public void setMoneyCre(Double moneyCre) {
		this.moneyCre = moneyCre;
	}
	public Double getCreditMon() {
		return creditMon;
	}
	public void setCreditMon(Double creditMon) {
		this.creditMon = creditMon;
	}
	public String getInventory() {
		return inventory;
	}
	public void setInventory(String inventory) {
		this.inventory = inventory;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	
	
	
}
