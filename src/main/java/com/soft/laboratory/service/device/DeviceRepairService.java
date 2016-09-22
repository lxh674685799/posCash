package com.soft.laboratory.service.device;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soft.core.db.GenericDao;
import com.soft.core.service.GenericService;
import com.soft.core.syscontext.Const;
import com.soft.core.util.DateFormatUtil;
import com.soft.laboratory.dao.device.DeviceEntityDao;
import com.soft.laboratory.dao.device.DeviceRepairDao;
import com.soft.laboratory.dao.message.MessageDao;
import com.soft.laboratory.model.device.DeviceRepair;
import com.soft.laboratory.model.message.InnerMessage;
import com.soft.laboratory.model.user.SysUser;

@Transactional
@Service
public class DeviceRepairService  extends GenericService<DeviceRepair> {
	
	@Resource
	private DeviceRepairDao dao;

	@Resource
	private MessageDao messageDao;
	
	@Resource
	private DeviceEntityDao entityDao;


	@Override
	protected GenericDao<DeviceRepair> getEntityDao() {
		return dao;
	}
	
	public void add(DeviceRepair device,SysUser loginUser) throws Exception {
			String sendDate = DateFormatUtil.getNowByString("");
		
			device.setSendUserId(loginUser.getId());
			device.setSendDate(sendDate);
			device.setRepairStatus(Const.REPAIRING);
			
			String eventId = dao.createEntity(device).toString();
			
			entityDao.deviceEntity(device.getDeviceIds(), eventId, Const.DEVICE_STATUS_REPAIR);
			//发送维修通知
			InnerMessage innerMessage = new InnerMessage();
			innerMessage.setSubject("设备维修通知");
			innerMessage.setFromUserId(loginUser.getId());
			innerMessage.setToUserId(device.getRepairUserId());
			innerMessage.setSendDate(sendDate);
			innerMessage.setEventId(eventId);
			innerMessage.setType(Const.DEVICE_STATUS_REPAIR);
			innerMessage.setReadStatus(Const.MESSAGE_UNREAD);
			messageDao.createEntity(innerMessage);
	}
}
