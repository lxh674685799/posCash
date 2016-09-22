package com.soft.laboratory.model.message;

import com.soft.core.model.BaseModel;
import com.soft.laboratory.model.user.SysUser;

/**
 * 通知公告
 * @author lxh
 *
 */
public class Notice extends BaseModel {

	private static final long serialVersionUID = 1L;
	
	private String id;
	/** 主题 */
	private String subject;
	/** 发布人 */
	private String createUserId;
	private String createUserName;
	private SysUser createUser;
	/** 发布时间*/
	private String createDate;
	/** 发送内容*/
	private String content;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public SysUser getCreateUser() {
		return createUser;
	}
	public void setCreateUser(SysUser createUser) {
		this.createUser = createUser;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
}
