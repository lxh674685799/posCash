package com.soft.core.upload.bean;
/**
 * 此类为  上传文件验证类
 * 
 * @author 刘旭辉
 */
public class UploadBean {
	/** 文件保存目录名 */
	private String folder;
	/** 文件最大尺寸 */
	private Long maxSize;
	/** 文件类型 */
	private String[] fileTypes;
	/** 文件上传盘符路径 **/
	private String uploadDir;

	public String getFolder() {
		return folder;
	}
	public void setFolder(String folder) {
		this.folder = folder;
	}
	public Long getMaxSize() {
		return maxSize;
	}
	public void setMaxSize(Long maxSize) {
		this.maxSize = maxSize;
	}
	public String[] getFileTypes() {
		return fileTypes;
	}
	public void setFileTypes(String[] fileTypes) {
		this.fileTypes = fileTypes;
	}
	public String getUploadDir() {
		return uploadDir;
	}
	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}
}
