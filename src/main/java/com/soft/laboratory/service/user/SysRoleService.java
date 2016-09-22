package com.soft.laboratory.service.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soft.core.db.GenericDao;
import com.soft.core.service.GenericService;
import com.soft.laboratory.dao.user.SysRoleDao;
import com.soft.laboratory.model.user.SysRole;

@Transactional
@Service
public class SysRoleService  extends GenericService<SysRole> {
	
	@Resource
	private SysRoleDao dao;

	@Override
	protected GenericDao<SysRole> getEntityDao() {
		return dao;
	}
	
}