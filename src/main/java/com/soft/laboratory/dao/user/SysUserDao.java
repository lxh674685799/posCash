package com.soft.laboratory.dao.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.soft.core.db.HibernateGenericDao;
import com.soft.core.page.Page;
import com.soft.core.util.Base64Demo;
import com.soft.laboratory.model.user.SysUser;

@Repository
public class SysUserDao extends HibernateGenericDao<SysUser>{

	@SuppressWarnings("unchecked")
	public SysUser login(SysUser user) {
		String hql="from SysUser s where s.account = '" + user.getAccount()+"' and s.passWord= '" + Base64Demo.getBASE64(user.getPassWord())+"'";
		Map<String, Object> params = new HashMap<String,Object>();
		List<SysUser> users = (List<SysUser>) this.listEntity(new Page(),hql, params);
		if(users.size()==0){
			return null;
		}else{
			return users.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	public SysUser getByName(String name) {
		String hql="from SysUser s where s.name = '" + name +"'";
		Map<String, Object> params = new HashMap<String,Object>();
		List<SysUser> users = (List<SysUser>) this.listEntity(new Page(),hql, params);
		if(users.size()==0){
			return null;
		}else{
			return users.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	public List<SysUser> getUserByOrgId(String orgId,Page page, String roleId) {
		String param = "and u.roleId = '"+roleId+"'";
		
		String hql = "select u from SysOrg o ,SysUser u where o.id = u.postId  and o.path like '"+orgId+"%'";
		if(!roleId.equals("undefined")){
			hql = hql+param;
		}
		Session session = getSession(); 
		Query query = session.createQuery(hql).setFirstResult(page.getResultIndex())
				.setMaxResults(page.getPageSize());
		return query.list();
	}

	public int getTotalForUserByOrgId(String orgId, String roleId) {
		String hql = "select count(u) from SysOrg o ,SysUser u where o.id = u.postId and o.path like '"+orgId+"%'";
		String param = "and u.roleId = '"+roleId+"'";
		if(!roleId.equals("undefined")){
			hql = hql+param;
		}
		Session session = getSession(); 
		Query query = session.createQuery(hql);
		Number total = (Number)query.uniqueResult();
		return total.intValue();
	}
	
}
