package com.soft.laboratory.service.message;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soft.core.db.GenericDao;
import com.soft.core.service.GenericService;
import com.soft.core.syscontext.Const;
import com.soft.core.util.DateFormatUtil;
import com.soft.core.util.FileUtil;
import com.soft.core.util.RegExpUtil;
import com.soft.laboratory.dao.device.DeviceBuyDao;
import com.soft.laboratory.dao.device.DeviceDao;
import com.soft.laboratory.dao.device.DeviceEntityDao;
import com.soft.laboratory.dao.device.DeviceRepairDao;
import com.soft.laboratory.dao.message.MessageDao;
import com.soft.laboratory.model.device.Device;
import com.soft.laboratory.model.device.DeviceBuy;
import com.soft.laboratory.model.device.DeviceEntity;
import com.soft.laboratory.model.device.DeviceRepair;
import com.soft.laboratory.model.message.InnerMessage;

@Transactional
@Service
public class MessageService  extends GenericService<InnerMessage> {
	
	@Resource
	private MessageDao dao;
	@Resource
	private DeviceBuyDao buyDao;
	@Resource
	private DeviceRepairDao repairDao;
	@Resource
	private DeviceEntityDao entityDao;
	@Resource
	private DeviceDao deviceDao;
	


	@Override
	protected GenericDao<InnerMessage> getEntityDao() {
		return dao;
	}
	
	public void delByIds(String[] ids) {
	
		for (String p : ids){
			InnerMessage info = dao.findById(p);
			dao.deleteEntity(info);
			// 删除图片文件
			if(info.getContent()!=null)
			FileUtil.deleteFile(RegExpUtil.getImgSrcFromHtml(info
								.getContent()));
		}
	}

	public void agree(InnerMessage msg) {
		String time = DateFormatUtil.getNowByString("");
		//判断如果为购入申请，按照购入处理
		if (msg.getType() == Const.USER_MESSAGE_BUY) {
			// 更新设备购入
			DeviceBuy buy = buyDao.findById(msg.getEventId());
			buy.setStatus(Const.STATUS_BUYING);
			buy.setDoTime(time);
			buyDao.modifyEntity(buy);
			// 向申购者发出购买任务		
			InnerMessage innerMessage = new InnerMessage();
			innerMessage.setSubject("同意设备购入");
			innerMessage.setType(Const.USER_MESSAGE_BUY);
			this.sendMessage(msg,innerMessage);
		}
		
		if(msg.getType() == Const.USER_MESSAGE_REPAIR){
			DeviceRepair repair = repairDao.findById(msg.getEventId());
			repair.setRepairDate(time);
			repair.setRepairStatus(Const.REPAIRED);
			repairDao.modifyEntity(repair);
			
			int status = Const.DEVICE_STATUS_NORMAL;
			entityDao.changeEntityStatus(repair.getId(),status);
			
			// 向报修者发出已修通知		
			InnerMessage innerMessage = new InnerMessage();
			innerMessage.setSubject("设备已维修通知");
			innerMessage.setType(Const.USER_MESSAGE_REPAIR);
			this.sendMessage(msg,innerMessage);
		}
		//设置信息的状态
		this.updateMessage(msg);
	}
	
	public void unagree(InnerMessage msg) {
		String time = DateFormatUtil.getNowByString("");
		//判断如果为购入申请，按照购入处理
		if (msg.getType() == Const.USER_MESSAGE_BUY) {
			// 更新设备购入
			DeviceBuy buy = buyDao.findById(msg.getEventId());
			buy.setStatus(Const.STATUS_EXAMINE_FALSE );
			buy.setDoTime(time);
			buyDao.modifyEntity(buy);
			// 向购买者发出未同意购买任务		
			InnerMessage innerMessage = new InnerMessage();
			innerMessage.setSubject("未同意设备购入");
			innerMessage.setType(Const.USER_MESSAGE_BUY);
			this.sendMessage(msg,innerMessage);
		}
		if(msg.getType() == Const.USER_MESSAGE_REPAIR){
			DeviceRepair repair = repairDao.findById(msg.getEventId());
			repair.setRepairDate(time);
			repair.setRepairStatus(Const.UNABLE_REPAIR);
			repairDao.modifyEntity(repair);
			
			int status = Const.DEVICE_STATUS_SCRAP;
			entityDao.changeEntityStatus(repair.getId(),status);
			
			// 向报修者发出已修通知		
			InnerMessage innerMessage = new InnerMessage();
			innerMessage.setSubject("设备无法维修通知");
			innerMessage.setType(Const.USER_MESSAGE_REPAIR);
			this.sendMessage(msg,innerMessage);
		}
		
		//设置信息的状态
		this.updateMessage(msg);
	}
	
	public void buy(InnerMessage msg) {
		String time = DateFormatUtil.getNowByString("");
		//判断如果为购入申请，按照购入处理
		if (msg.getType() == Const.USER_MESSAGE_BUY) {
			// 更新设备购入
			DeviceBuy buy = buyDao.findById(msg.getEventId());
			buy.setStatus(Const.STATUS_BUYED);
			buy.setBuyTime(time);
			buyDao.modifyEntity(buy);
			
			//设备购入
			int number = buy.getNumber().intValue();
			String typeName =buy.getDevice().getName();
			DeviceEntity  entity = new DeviceEntity();
			entity.setBuyId(buy.getId());
			entity.setDeviceId(buy.getDeviceId());
			entity.setStatus(Const.DEVICE_STATUS_NORMAL);
			entity.setPostId(buy.getBuyUser().getPostId());
				
			//向数据库中同时添加多个设备
			entityDao.craeteEntitys(entity,typeName,number);
			
			//更新设备数量
			Device device = deviceDao.findById(buy.getDeviceId());
			device.setNumber(device.getNumber()+buy.getNumber());
			deviceDao.modifyEntity(device);		
		}
		//设置信息的状态
		this.updateMessage(msg);
	}

	public List<InnerMessage> getNotReadMsg(String id) {
		 List<InnerMessage> messages = dao.getNotReadMsg(id);
		return messages;
	}
	
	public void updateMessage(InnerMessage msg){
		String time = DateFormatUtil.getNowByString("");
		msg.setReadStatus(Const.MESSAGE_DO);
		msg.setDoDate(time);
		dao.modifyEntity(msg);	
	}
	
	public void sendMessage(InnerMessage msg,InnerMessage innerMessage){
		String time = DateFormatUtil.getNowByString("");
		innerMessage.setFromUserId(msg.getToUserId());
		innerMessage.setToUserId(msg.getFromUserId());
		innerMessage.setSendDate(time);
		innerMessage.setEventId(msg.getEventId());
		innerMessage.setReadStatus(Const.MESSAGE_UNREAD);
		dao.createEntity(innerMessage);		
	}
	
}