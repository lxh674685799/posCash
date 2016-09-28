package com.soft.laboratory.service.goods;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soft.core.db.GenericDao;
import com.soft.core.service.GenericService;
import com.soft.laboratory.dao.goods.GoodsDao;
import com.soft.laboratory.dao.goods.GoodsLogDao;
import com.soft.laboratory.model.goods.GoodsLog;
@Transactional
@Service
public class GoodsLogService extends GenericService<GoodsLog> {

	
	@Resource
	private GoodsLogDao goodsLogDao;
	
	@Override
	protected GenericDao<GoodsLog> getEntityDao() {
		return goodsLogDao;
	}
	

}
