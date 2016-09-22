package com.soft.laboratory.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.soft.core.db.HibernateGenericDao;
import com.soft.core.page.Page;
import com.soft.laboratory.model.SysResource;

@Repository
public class SysResourceDao extends HibernateGenericDao<SysResource>{
	
	@SuppressWarnings("unchecked")
	public List<SysResource> getByParentId(String id) {
		String hql="from SysResource q where q.parentId = :parentId";
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("parentId", id);
		List<SysResource> result = (List<SysResource>) this.listEntity(new Page(),hql, params);
		return result;
	}

	public void delChilds(String resId) {
		Session session = getSession(); 
		session.createSQLQuery("delete l_role_resource  from l_role_resource , l_resource  where l_resource.resource_id = l_role_resource.resource_id" +
				" and l_resource.parentId = '"+resId+"'").executeUpdate();
		String hql="delete from SysResource d where d.parentId = '" + resId+"'";
		this.deleteEntity(hql);
	}
	
	public void del(String resId){
		Session session = getSession(); 
		session.createSQLQuery("delete from l_role_resource where resource_id = '"+resId+"'").executeUpdate();
		session.delete(this.findById(resId));
	}
}
