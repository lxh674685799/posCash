package com.soft.laboratory.model;

import java.io.Serializable;

import com.soft.core.poi.ExcelSign;


/**
 * 数据初始化导入
 * @author 刘旭辉
 *
 */
public class InitInfo implements Serializable {
	
	private static final long serialVersionUID = 7448745974279350649L;
	
	private String id;
	
	/** 凭证号**/
	private String voucherNumber;
	/** 仪器编号**/	
	private String codes;
	/** 分类号**/
	private String classNumber;
	/** 仪器名称**/
	private String deviceName;
	/**型号**/
	private String type;
	/**规格**/
	private String standard;
	/**仪器来源**/
	private String deviceResource;
	/**经费来源**/
	private String financialResources;
	/**购置日期**/
	private String buyDate;
	/**单价 **/
	private Double price;
	/**附件金额 **/
	private Double amount;
	/**总金额 **/
	private Double totalMoney;
	/**国别码 **/
	private String nationality;
	/**设备分类 **/
	private String classification;
	/**出厂日期 **/
	private String productionDate;
	/**生产厂家**/
	private String  manufactureFactory;
	/**现领用人**/
	private String user;
	/**发票号**/
	private String billNumber;
	/**变动日期**/
	private String changeDate;
	/**现状码**/
	private String statusCode;
	/**使用方向**/
	private String useDirection;
	/**单位编号**/
	private String unitNumber;
	/**单位名称**/
	private String  unitName;
	/**附件情况及存放地点**/
	private String  locationName;
	/**条码**/
	private String barCode;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@ExcelSign(title="凭证号",order=1)
	public String getVoucherNumber() {
		return voucherNumber;
	}
	public void setVoucherNumber(String voucherNumber) {
		this.voucherNumber = voucherNumber;
	}
	@ExcelSign(title="仪器编号",order=2)
	public String getCodes() {
		return codes;
	}
	public void setCodes(String codes) {
		this.codes = codes;
	}
	@ExcelSign(title="分类号",order=3)
	public String getClassNumber() {
		return classNumber;
	}
	public void setClassNumber(String classNumber) {
		this.classNumber = classNumber;
	}
	@ExcelSign(title="仪器名称",order=4)
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	@ExcelSign(title="型号",order=5)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@ExcelSign(title="规格",order=6)
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	@ExcelSign(title="仪器来源",order=7)
	public String getDeviceResource() {
		return deviceResource;
	}
	public void setDeviceResource(String deviceResource) {
		this.deviceResource = deviceResource;
	}
	@ExcelSign(title="经费来源",order=8)
	public String getFinancialResources() {
		return financialResources;
	}
	public void setFinancialResources(String financialResources) {
		this.financialResources = financialResources;
	}
	@ExcelSign(title="购置日期",order=9)
	public String getBuyDate() {
		return buyDate;
	}
	public void setBuyDate(String buyDate) {
		this.buyDate = buyDate;
	}
	@ExcelSign(title="单价",order=10)
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	@ExcelSign(title="附件金额",order=11)
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	@ExcelSign(title="总金额",order=12)
	public Double getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}
	@ExcelSign(title="国别码",order=13)
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	@ExcelSign(title="设备分类",order=14)
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	@ExcelSign(title="出厂时间",order=15)
	public String getProductionDate() {
		return productionDate;
	}
	public void setProductionDate(String productionDate) {
		this.productionDate = productionDate;
	}
	@ExcelSign(title="生产厂家",order=16)
	public String getManufactureFactory() {
		return manufactureFactory;
	}
	public void setManufactureFactory(String manufactureFactory) {
		this.manufactureFactory = manufactureFactory;
	}
	@ExcelSign(title="现领用人",order=17)
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	@ExcelSign(title="发票号",order=18)
	public String getBillNumber() {
		return billNumber;
	}
	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}
	@ExcelSign(title="变动日期",order=19)
	public String getChangeDate() {
		return changeDate;
	}
	public void setChangeDate(String changeDate) {
		this.changeDate = changeDate;
	}
	@ExcelSign(title="现状码",order=20)
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	@ExcelSign(title="使用方向",order=21)
	public String getUseDirection() {
		return useDirection;
	}
	public void setUseDirection(String useDirection) {
		this.useDirection = useDirection;
	}
	@ExcelSign(title="单位编号",order=22)
	public String getUnitNumber() {
		return unitNumber;
	}
	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
	}
	@ExcelSign(title="单位名称",order=23)
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	@ExcelSign(title="条码",order=24)
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	@ExcelSign(title="附件情况及存放地点",order=25)
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	@Override
	public String toString() {
		return "InitInfo [id=" + id + ", voucherNumber=" + voucherNumber
				+ ", codes=" + codes + ", classNumber=" + classNumber
				+ ", deviceName=" + deviceName + ", type=" + type
				+ ", standard=" + standard + ", deviceResource="
				+ deviceResource + ", financialResources=" + financialResources
				+ ", buyDate=" + buyDate + ", price=" + price + ", amount="
				+ amount + ", totalMoney=" + totalMoney + ", nationality="
				+ nationality + ", classification=" + classification
				+ ", productionDate=" + productionDate
				+ ", manufactureFactory=" + manufactureFactory + ", user="
				+ user + ", billNumber=" + billNumber + ", changeDate="
				+ changeDate + ", statusCode=" + statusCode + ", useDirection="
				+ useDirection + ", unitNumber=" + unitNumber + ", unitName="
				+ unitName + ", locationName=" + locationName + ", barCode="
				+ barCode + "]";
	}	
	
}
