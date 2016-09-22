package com.soft.laboratory.service.device;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soft.core.db.GenericDao;
import com.soft.core.page.Page;
import com.soft.core.service.GenericService;
import com.soft.laboratory.dao.device.DeviceEntityDao;
import com.soft.laboratory.model.device.DeviceEntity;

@Transactional
@Service
public class DeviceEntityService  extends GenericService<DeviceEntity> {
	
	@Resource
	private DeviceEntityDao dao;

	@Override
	protected GenericDao<DeviceEntity> getEntityDao() {
		return dao;
	}

	public int getTotalForDeviceByOrgId(String orgId, int status) {
		return dao.getTotalForDeviceByOrgId(orgId,status);
	}

	public List<DeviceEntity> getDeviceByOrgId(String orgId,int status ,Page pagination) {
		return dao.getDeviceByOrgId(orgId,status,pagination);
	}

	public void deviceEntity(String deviceIds, String entityId,int status) {
		dao.deviceEntity(deviceIds,entityId,status);
	}

	public void changeEntityStatus(String loanIds, int status) {
		dao.changeEntityStatus(loanIds,status);
	}

	public List<DeviceEntity> getDevieByIds(String deviceIds) {
		return dao.getDevieByIds(deviceIds);
	}
}
