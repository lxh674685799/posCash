package com.soft.laboratory.service.user;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soft.core.db.GenericDao;
import com.soft.core.service.GenericService;
import com.soft.laboratory.dao.user.SysOrgDao;
import com.soft.laboratory.model.user.SysOrg;

@Transactional
@Service
public class SysOrgService  extends GenericService<SysOrg> {
	
	@Resource
	private SysOrgDao dao;

	@Override
	protected GenericDao<SysOrg> getEntityDao() {
		return dao;
	}
	
	public List<SysOrg> getByParentId(String id) {
		return dao.getByParentId(id);
	}
	
	public String add(SysOrg type) {
		String parentId= type.getParentId();
		SysOrg pPost = dao.findById(parentId);
		if(!pPost.getisParent()){
			pPost.setisParent(true);
			dao.modifyEntity(pPost);
		}	
		return dao.createEntity(type).toString();
	}

	public void delByPath(String path) {
		dao.delByPath(path);
	}
}