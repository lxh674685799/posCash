package com.soft.laboratory.service.device;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soft.core.db.GenericDao;
import com.soft.core.service.GenericService;
import com.soft.laboratory.dao.device.DeviceTypeDao;
import com.soft.laboratory.model.device.DeviceType;

@Transactional
@Service
public class DeviceTypeService  extends GenericService<DeviceType> {
	
	@Resource
	private DeviceTypeDao dao;

	@Override
	protected GenericDao<DeviceType> getEntityDao() {
		return dao;
	}
	
	public List<DeviceType> getByParentId(String id) {
		return dao.getByParentId(id);
	}
	
	public String add(DeviceType type) {
		String parentId= type.getParentId();
		if(!parentId.equals("-1")){
		DeviceType pType = dao.findById(parentId);
		if(!pType.getisParent()){
			pType.setisParent(true);
			dao.modifyEntity(pType);
		}	
		}
		return dao.createEntity(type).toString();	
	}


	public void delByPath(String path) {
		dao.delByPath(path);
	}
}
