package com.soft.laboratory.model.user;

import com.soft.core.model.BaseModel;

/**
 * 系统用户实体
 * @author 翟瑞东
 *
 */
public class SysUser extends BaseModel {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户Id
	 */
	private String id;
	/**
	 * 用户姓名
	 */
	private String name;
	/**
	 * 用户登录帐号
	 */
	private String account;
	/**
	 * 用户登录密码
	 */
	private String passWord;
	/**
	 * 用户性别 ture：男
	 */
	private boolean sex;
	/**
	 * 用户email
	 */
	private String email;
	/**
	 * 用户联系电话
	 */
	private String phone;
	/**
	 * 用户备注
	 */
	private String remark;	
	/**
	 * 所在机构
	 */
	private String postId;
	private String postName;
	private SysOrg post;
	/**
	 * 所属角色
	 */
	private String roleId;
	private SysRole role;

	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public boolean isSex() {
		return sex;
	}
	public void setSex(boolean sex) {
		this.sex = sex;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public SysRole getRole() {
		return role;
	}
	public void setRole(SysRole role) {
		this.role = role;
	}
	public SysOrg getPost() {
		return post;
	}
	public void setPost(SysOrg post) {
		this.post = post;
	}
	public String getPostName() {
		return postName;
	}
	public void setPostName(String postName) {
		this.postName = postName;
	}
}
