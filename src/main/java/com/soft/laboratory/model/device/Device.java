package com.soft.laboratory.model.device;

import com.soft.core.model.BaseModel;

/**
 * 设备实体
 * @author 刘旭辉
 *
 */
public class Device extends BaseModel{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 设备ID
	 */
	private String id;
	/**
	 * 设备名称
	 */
	private String name;
	/**
	 * 设备厂家ID
	 */
	private String factoryId;
	private String factoryName;
	private DeviceFactory factory;
	/**
	 * 设备类型ID
	 */
	private String typeId;
	private DeviceType type;
	/**
	 * 设备数量
	 */
	private Long number;
	/**
	 * 设备型号
	 */
	private String model;
	/**
	 * 设备条码
	 */
	private String code;	
	/**
	 * 设备备注
	 */
	private String remark;
	/**
	 * 国别码
	 */
	private String countryCode;
	
	/**
	 * 设备规格
	 */
	private String spec;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFactoryId() {
		return factoryId;
	}
	public void setFactoryId(String factoryId) {
		this.factoryId = factoryId;
	}
	
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public DeviceFactory getFactory() {
		return factory;
	}
	public void setFactory(DeviceFactory factory) {
		this.factory = factory;
	}
	public DeviceType getType() {
		return type;
	}
	public void setType(DeviceType type) {
		this.type = type;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public String getFactoryName() {
		return factoryName;
	}
	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}


}
