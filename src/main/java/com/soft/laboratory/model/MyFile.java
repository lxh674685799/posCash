package com.soft.laboratory.model;

import com.soft.core.model.BaseModel;

/**
 * 系统附件实体类
 * @author 刘旭辉
 *
 */
public class MyFile extends BaseModel {

	private static final long serialVersionUID = 1L;
	/** 附件Id*/
	private String id;
	/** 附件名称*/
	private String fileName;
	/** 关联Id*/
	private String infoId;
	/** 附件路径*/
	private String filePath;
	/** 附件对应的类型*/
	private Integer fileType;
	/** 附件上传时间*/
	private String impTime;
	/** 上传人*/
	private String impUserId;
	

	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getInfoId() {
		return infoId;
	}
	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getImpTime() {
		return impTime;
	}
	public void setImpTime(String impTime) {
		this.impTime = impTime;
	}
	public String getImpUserId() {
		return impUserId;
	}
	public void setImpUserId(String impUserId) {
		this.impUserId = impUserId;
	}
	public Integer getFileType() {
		return fileType;
	}
	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
