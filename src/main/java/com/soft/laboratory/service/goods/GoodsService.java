package com.soft.laboratory.service.goods;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soft.core.db.GenericDao;
import com.soft.core.service.GenericService;
import com.soft.laboratory.dao.goods.GoodsDao;
import com.soft.laboratory.model.goods.Goods;
@Transactional
@Service
public class GoodsService extends GenericService<Goods> {

	@Resource
	private GoodsDao goodsDao;
	
	@Override
	protected GenericDao<Goods> getEntityDao() {
		return goodsDao;
	}

}
