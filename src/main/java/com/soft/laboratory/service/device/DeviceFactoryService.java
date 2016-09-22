package com.soft.laboratory.service.device;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soft.core.db.GenericDao;
import com.soft.core.service.GenericService;
import com.soft.laboratory.dao.device.DeviceFactoryDao;
import com.soft.laboratory.model.device.DeviceFactory;

@Transactional
@Service
public class DeviceFactoryService  extends GenericService<DeviceFactory> {
	
	@Resource
	private DeviceFactoryDao dao;

	@Override
	protected GenericDao<DeviceFactory> getEntityDao() {
		return dao;
	}
}
