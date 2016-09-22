package com.soft.laboratory.dao.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.soft.core.db.HibernateGenericDao;
import com.soft.core.page.Page;
import com.soft.laboratory.model.user.SysMember;

@Repository
public class SysMemberDao extends HibernateGenericDao<SysMember>{


	@SuppressWarnings("unchecked")
	public SysMember getByNos(String nos) {
		String hql="from SysMember s where s.memberNo = '" + nos +"'";
		Map<String, Object> params = new HashMap<String,Object>();
		List<SysMember> members = (List<SysMember>) this.listEntity(new Page(),hql, params);
		if(members.size()==0){
			return null;
		}else{
			return members.get(0);
		}
	}
	
}
