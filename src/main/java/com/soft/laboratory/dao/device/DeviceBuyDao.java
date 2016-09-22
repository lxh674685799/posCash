package com.soft.laboratory.dao.device;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.soft.core.db.HibernateGenericDao;
import com.soft.laboratory.model.device.DeviceBuy;

@Repository
public class DeviceBuyDao extends HibernateGenericDao<DeviceBuy>{
	
	@SuppressWarnings("unchecked")
	public List<DeviceBuy> isBuyExist(String deviceId,String buyTime) {
		String hql="from DeviceBuy d where d.deviceId = '"+deviceId+"' and d.buyTime = '" +buyTime+"'";
		List<DeviceBuy> result = (List<DeviceBuy>) getSession().createQuery(hql).list();
		return result;
	}
	
}
