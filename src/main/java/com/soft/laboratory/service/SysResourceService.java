package com.soft.laboratory.service;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soft.core.db.GenericDao;
import com.soft.core.service.GenericService;
import com.soft.laboratory.dao.SysResourceDao;
import com.soft.laboratory.model.SysResource;

@Transactional
@Service
public class SysResourceService  extends GenericService<SysResource> {
	
	@Resource
	private SysResourceDao dao;

	@Override
	protected GenericDao<SysResource> getEntityDao() {
		return dao;
	}

	public List<SysResource> getByParentId(String id) {
		return dao.getByParentId(id);
	}
	
	public String add(SysResource resource) {
		String parentId= resource.getParentId();
		SysResource pResource = dao.findById(parentId);
	
		if(!pResource.getisParent()){
			pResource.setisParent(true);
			dao.modifyEntity(pResource);
		}	
		return dao.createEntity(resource).toString();	
	}

	public void delChilds(String resId) {
		dao.delChilds(resId);	
	}

	public void del(String resId) {
		dao.del(resId);
	}
}
