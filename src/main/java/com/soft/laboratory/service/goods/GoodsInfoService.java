package com.soft.laboratory.service.goods;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soft.core.db.GenericDao;
import com.soft.core.page.Page;
import com.soft.core.service.GenericService;
import com.soft.laboratory.dao.goods.GoodsInfoDao;
import com.soft.laboratory.model.goods.GoodsInfo;
@Transactional
@Service
public class GoodsInfoService extends GenericService<GoodsInfo> {

	@Resource
	private GoodsInfoDao goodsInfoDao;
	
	@Override
	protected GenericDao<GoodsInfo> getEntityDao() {
		return goodsInfoDao;
	}

	public int getTotalDiv(String goodName, String startTime, String endTime, String payType) {
		return goodsInfoDao.getTotalDiv(goodName,startTime,endTime,payType);
	}

	public List listByPageByDiv(Page pagination, String goodName, String startTime, String endTime, String payType) {
		return goodsInfoDao.listByPageByDiv(pagination,goodName,startTime,endTime,payType);
	}
	
}
