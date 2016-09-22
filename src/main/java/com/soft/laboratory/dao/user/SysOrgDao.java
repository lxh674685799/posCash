package com.soft.laboratory.dao.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.soft.core.db.HibernateGenericDao;
import com.soft.core.page.Page;
import com.soft.laboratory.model.user.SysOrg;

@Repository
public class SysOrgDao extends HibernateGenericDao<SysOrg>{

	@SuppressWarnings("unchecked")
	public List<SysOrg> getByParentId(String id) {
		String hql="from SysOrg d where d.parentId = :parentId";
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("parentId", id);
		List<SysOrg> result = (List<SysOrg>) this.listEntity(new Page(),hql, params);
		return result;
	}

	public void delByPath(String path) {
		String hql="delete from SysOrg d where d.path like '" + path+"%'";
		this.deleteEntity(hql);
	}
}
