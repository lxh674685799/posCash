package com.soft.core.syscontext;

/**
 * 常量表
 * @author 刘旭辉
 * 
 */
public interface Const {

	/** 用于保存当前登录用户 */
	String USER_SESSION_ID = "LOGIN_USER";

	/** systemAdmin不受任何权限限制 */
	String SYSTEM_ADMIN_ID = "1000";
	
	/** 系统返回信息类型 */
	int MESSAGE_SUCCESS =1;
	int MESSAGE_WARN =0;
	int MESSAGE_QUESTION =2;
	int MESSAGE_ERROR =3;
	
	/** 设备购买状态 */
	/** 设备购买状态 ---审批中*/
	int STATUS_EXAMINE = 0;
	/** 设备购买状态 ---审批未同意*/
	int STATUS_EXAMINE_FALSE = 3;
	/** 设备购买状态 ---审批同意购买中*/
	int STATUS_BUYING = 1;
	/** 设备购买状态 ---已购*/
	int STATUS_BUYED = 2;
	
	/** 用户男女 */
	boolean USER_MAN =true;
	boolean USER_WOMAN =false;
	
	/** 用户信息类别 维护、申请、申购*/
	/** 消息为分配的任务*/
	int USER_MESSAGE_TASK=0;
	/** 用户之间的普通消息*/
	int USER_MESSAGE_INFO=1;	
	/** 申购信息*/
	int USER_MESSAGE_BUY=2;
	/** 维护信息*/
	int USER_MESSAGE_REPAIR=3;
	/** 申领信息*/
	int USER_MESSAGE_GET=4;
	
	/** 用户信息状态*/
	/** 已读*/
	int MESSAGE_READ=1;
	/** 未读*/
	int MESSAGE_UNREAD=0;
	/** 已办理*/
	int MESSAGE_DO=2;
	
	/** 用户信息删除状态*/
	int MESSAGE_TO_DELETE=0;
	int MESSAGE_FROM_DELETE=1;
	
	/** 附件类型*/
	int FILE_MESSAGE=0;
	int FILE_EMAIL=1;
	int FILE_DEVICEBUY=2;
	
	/** 设备状态*/
	/** 设备正常状态，在库*/
	int DEVICE_STATUS_NORMAL=0;
	/** 设备正常状态，借出*/
	int DEVICE_STATUS_LOAN=1;
	/** 设备正常状态，发放*/
	int DEVICE_STATUS_GET=2;
	/** 设备正常状态，维修*/
	int DEVICE_STATUS_REPAIR=3;
	/** 设备状态，报废*/
	int DEVICE_STATUS_SCRAP=-1;
	
	/** 设备借出状态*/
	/** 设备借出*/
	int DEVICE_LOAN=0;
	/** 设备归还*/
	int DEVICE_RETURN=1;
	/** 设备归还超时*/
	//int DEVICE_RETURN_late=-1;
	
	/** 设备维修状态*/
	/** 正在维修*/
	int REPAIRING=0;
	/** 已维修*/
	int REPAIRED=1;
	/** 无法维修*/
	int UNABLE_REPAIR=-1;
	
	/** 实验室状态*/
	/** 借出*/
	int LABORATORY_LOAN = 1;
	/** 空闲*/
	int LABORATORY_NORMAL = 0;
}
