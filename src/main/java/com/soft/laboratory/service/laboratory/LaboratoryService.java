package com.soft.laboratory.service.laboratory;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soft.core.db.GenericDao;
import com.soft.core.service.GenericService;
import com.soft.laboratory.dao.laboratory.LaboratoryDao;
import com.soft.laboratory.model.laboratory.Laboratory;

@Transactional
@Service
public class LaboratoryService  extends GenericService<Laboratory> {
	
	@Resource
	private LaboratoryDao dao;

	@Override
	protected GenericDao<Laboratory> getEntityDao() {
		return dao;
	}
}
