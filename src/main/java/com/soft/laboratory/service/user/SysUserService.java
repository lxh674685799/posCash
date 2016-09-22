package com.soft.laboratory.service.user;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soft.core.db.GenericDao;
import com.soft.core.page.Page;
import com.soft.core.service.GenericService;
import com.soft.laboratory.dao.user.SysUserDao;
import com.soft.laboratory.model.user.SysUser;

@Transactional
@Service
public class SysUserService  extends GenericService<SysUser> {
	
	@Resource
	private SysUserDao dao;

	@Override
	protected GenericDao<SysUser> getEntityDao() {
		return dao;
	}

	public SysUser login(SysUser user) {
		return dao.login(user);
	}

	public SysUser getByName(String name) {
		return dao.getByName(name);
	}

	public List<SysUser> getUserByOrgId(String orgId,Page page, String roleId) {
		return dao.getUserByOrgId(orgId,page,roleId);
	}

	public int getTotalForUserByOrgId(String orgId, String roleId) {
		return dao.getTotalForUserByOrgId(orgId,roleId);
	}
	
}
