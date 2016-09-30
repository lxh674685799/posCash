package com.soft.laboratory.service;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soft.core.db.GenericDao;
import com.soft.core.service.GenericService;
import com.soft.laboratory.dao.ChartDao;
import com.soft.laboratory.model.ChartModel;

@Transactional
@Service
public class ChartService  extends GenericService<ChartModel> {
	
	@Resource
	private ChartDao dao;

	@Override
	protected GenericDao<ChartModel> getEntityDao() {
		return dao;
	}

	public Map<String, String> queryByMonth(String monthStr) {
		return dao.queryByMonth(monthStr);
	}

	public List queryByEmp(String yearMonth) {
		return dao.queryByEmp(yearMonth);
	}

	
}
