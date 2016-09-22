package com.soft.laboratory.dao.device;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.soft.core.db.HibernateGenericDao;
import com.soft.core.page.Page;
import com.soft.core.util.DateFormatUtil;
import com.soft.laboratory.model.device.DeviceEntity;

@Repository
public class DeviceEntityDao extends HibernateGenericDao<DeviceEntity> {

	public void craeteEntitys(DeviceEntity entity, String typeName, int number) {
		// 打开Session
		Session session = this.getSession();
		// 循环number次，插入number条记录
		for (int i = 1; i <=number; i++) {
			entity.setNumber(DateFormatUtil.formatDate(new Date())+typeName+i);
			session.save(entity);
			session.flush();
			session.clear();
		}
	}

	public int getTotalForDeviceByOrgId(String orgId, int status) {
		String hql = "select count(e) from SysOrg o ,DeviceEntity e where o.id = e.postId and e.status = '"+status+"' and o.path like '"+orgId+"%'";
		Session session = getSession(); 
		Query query = session.createQuery(hql);
		Number total = (Number)query.uniqueResult();
		return total.intValue();
	}

	@SuppressWarnings("unchecked")
	public List<DeviceEntity> getDeviceByOrgId(String orgId, int status, Page page) {
		String hql = "select e from SysOrg o ,DeviceEntity e where o.id = e.postId and e.status = '"+status+"' and o.path like '"+orgId+"%'";
		Session session = getSession(); 
		Query query = session.createQuery(hql).setFirstResult(page.getResultIndex())
				.setMaxResults(page.getPageSize());
		return query.list();
	}

	public void deviceEntity(String deviceIds, String entityId,int status) {
		String hql = "update DeviceEntity e set e.eventId = '"+entityId+"' , e.status = '"+status+"' where e.id in ('"+deviceIds.replaceAll(",", "','")+"')";
		Session session = getSession(); 
		session.createQuery(hql).executeUpdate();
	}

	public void changeEntityStatus(String loanIds, int status) {
		String hql = "update DeviceEntity e set e.eventId = null , e.status = '"+status+"' where e.eventId in ('"+loanIds.replaceAll(",", "','")+"')";
		Session session = getSession(); 
		session.createQuery(hql).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<DeviceEntity> getDevieByIds(String deviceIds) {
		String hql = "select e from DeviceEntity e where e.id in ('"+deviceIds.replaceAll(",", "','")+"')";
		Session session = getSession(); 
		Query query = session.createQuery(hql);
		return query.list();
	}
}
