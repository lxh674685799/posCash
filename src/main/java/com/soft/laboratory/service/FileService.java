package com.soft.laboratory.service;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soft.core.db.GenericDao;
import com.soft.core.service.GenericService;
import com.soft.laboratory.dao.FileDao;
import com.soft.laboratory.model.MyFile;

@Transactional
@Service
public class FileService  extends GenericService<MyFile> {
	
	@Resource
	private FileDao dao;

	@Override
	protected GenericDao<MyFile> getEntityDao() {
		return dao;
	}

}
