package com.soft.laboratory.service.device;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.soft.core.db.GenericDao;
import com.soft.core.service.GenericService;
import com.soft.core.syscontext.Const;
import com.soft.core.syscontext.SystemContext;
import com.soft.core.upload.UpLoadContext;
import com.soft.core.upload.UploadResource;
import com.soft.core.util.DateFormatUtil;
import com.soft.laboratory.dao.FileDao;
import com.soft.laboratory.dao.device.DeviceGetDao;
import com.soft.laboratory.dao.message.MessageDao;
import com.soft.laboratory.model.MyFile;
import com.soft.laboratory.model.device.DeviceGet;
import com.soft.laboratory.model.message.InnerMessage;
import com.soft.laboratory.model.user.SysUser;

@Transactional
@Service
public class DeviceGetService  extends GenericService<DeviceGet> {
	
	@Resource
	private DeviceGetDao dao;
	@Resource
	private FileDao fileDao;
	@Resource
	private MessageDao messageDao;


	@Override
	protected GenericDao<DeviceGet> getEntityDao() {
		return dao;
	}
	
	public void add(HttpServletRequest request,DeviceGet device
			,MultipartHttpServletRequest attachmentFile,SysUser rUser) throws Exception {
			String userId =SystemContext.getCurrentUser(request).getId();
			String ratifyDate = DateFormatUtil.getNowByString("");
//		
//			device.setStatus(Const.STATUS_EXAMINE);
//			device.setRatifyUserId(userId);
//			device.setRatifyDate(ratifyDate);
//			
//			String eventId = dao.createEntity(device).toString();
//			
//			UpLoadContext upLoad = new UpLoadContext(
//                    new UploadResource());
//			MultipartFile file = attachmentFile.getFile("file");
//			String url = upLoad.uploadFile(file, null);
//			String fileName = file.getOriginalFilename();
//			
//			MyFile upFile = new MyFile();
//			upFile.setInfoId(eventId);
//			upFile.setFileName(fileName);
//			upFile.setFilePath(url);
//			upFile.setImpTime(ratifyDate);
//			upFile.setImpUserId(userId);
//			
//			fileDao.createEntity(upFile);
//			
//			InnerMessage innerMessage = new InnerMessage();
//			innerMessage.setSubject("设备申领");
//			innerMessage.setFromUserId(userId);
//			innerMessage.setToUserId(rUser.getId());
//			innerMessage.setSendDate(ratifyDate);
//			innerMessage.setEventId(eventId);
//			innerMessage.setType(Const.USER_MESSAGE_BUY);
//			innerMessage.setReadStatus(Const.MESSAGE_UNREAD);
//			messageDao.createEntity(innerMessage);
	}
}
