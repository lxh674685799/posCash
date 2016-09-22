package com.soft.laboratory.model.message;

import com.soft.core.model.BaseModel;
import com.soft.laboratory.model.user.SysUser;

public class InnerMessage extends BaseModel {

	private static final long serialVersionUID = 1L;
	
	private String id;
	/** 主题 */
	private String subject;
	/** 发送人 */
	private String fromUserId;
	private String fromUserName;
	private SysUser fromUser;
	/** 接收人*/
	private String toUserId;
	private SysUser toUser;
	/** 发送时间*/
	private String sendDate;
	/** 用户读取时间*/
	private String readDate;
	/** 用户办理时间*/
	private String doDate;
	/** 发送内容*/
	private String content;
	/** 信息种类 */
	private Integer type;
	/** 信息状态，已读、未读 */
	private Integer readStatus;
	/** 信息状态，针对发送用户是否删除 */
	private Integer deleteStatus;
	/** 信息重要性 */
	private Integer important;
	/** 事件ID */
	private String eventId;
	
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
	public String getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}
	public String getToUserId() {
		return toUserId;
	}
	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}
	public String getSendDate() {
		return sendDate;
	}
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}
	public String getReadDate() {
		return readDate;
	}
	public void setReadDate(String readDate) {
		this.readDate = readDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getReadStatus() {
		return readStatus;
	}
	public void setReadStatus(Integer readStatus) {
		this.readStatus = readStatus;
	}
	public Integer getDeleteStatus() {
		return deleteStatus;
	}
	public void setDeleteStatus(Integer deleteStatus) {
		this.deleteStatus = deleteStatus;
	}
	public Integer getImportant() {
		return important;
	}
	public void setImportant(Integer important) {
		this.important = important;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public SysUser getFromUser() {
		return fromUser;
	}
	public void setFromUser(SysUser fromUser) {
		this.fromUser = fromUser;
	}
	public SysUser getToUser() {
		return toUser;
	}
	public void setToUser(SysUser toUser) {
		this.toUser = toUser;
	}
	public String getDoDate() {
		return doDate;
	}
	public void setDoDate(String doDate) {
		this.doDate = doDate;
	}
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	
}
