package com.soft.laboratory.service.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soft.core.db.GenericDao;
import com.soft.core.service.GenericService;
import com.soft.laboratory.dao.user.SysMemberDao;
import com.soft.laboratory.model.user.SysMember;

@Transactional
@Service
public class SysMemberService  extends GenericService<SysMember> {
	
	@Resource
	private SysMemberDao dao;

	@Override
	protected GenericDao<SysMember> getEntityDao() {
		return dao;
	}

	public SysMember getByNos(String nos) {
		return dao.getByNos(nos);
	}
	
}
