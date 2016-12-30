package com.soft.laboratory.dao.goods;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.soft.core.db.HibernateGenericDao;
import com.soft.laboratory.model.goods.Goods;
@Repository
public class GoodsDao extends HibernateGenericDao<Goods> {

	/**
	 * 通过商品编码得到商品
	 * @param code
	 * @return
	 */
	public Goods getByCode(String code) {
		String hql="from Goods d where d.code = '"+code+"'";
		Query query = getSession().createQuery(hql);
		Goods goods =(Goods) query.uniqueResult();
		return goods;
	}

}
