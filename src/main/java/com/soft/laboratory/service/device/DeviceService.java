package com.soft.laboratory.service.device;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soft.core.db.GenericDao;
import com.soft.core.service.GenericService;
import com.soft.laboratory.dao.device.DeviceDao;
import com.soft.laboratory.model.device.Device;

@Transactional
@Service
public class DeviceService  extends GenericService<Device> {
	
	@Resource
	private DeviceDao dao;

	@Override
	protected GenericDao<Device> getEntityDao() {
		return dao;
	}
}
