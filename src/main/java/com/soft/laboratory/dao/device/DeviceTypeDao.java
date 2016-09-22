package com.soft.laboratory.dao.device;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.soft.core.db.HibernateGenericDao;
import com.soft.core.page.Page;
import com.soft.laboratory.model.device.DeviceType;

@Repository
public class DeviceTypeDao extends HibernateGenericDao<DeviceType>{

	@SuppressWarnings("unchecked")
	public List<DeviceType> getByParentId(String id) {
		String hql="from DeviceType d where d.parentId = :parentId";
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("parentId", id);
		List<DeviceType> result = (List<DeviceType>) this.listEntity(null,hql, params);
		return result;
	}

	public void delByPath(String path) {
		String hql="delete from DeviceType d where d.path like '" + path+"%'";
		this.deleteEntity(hql);
	}
}
