package com.soft.laboratory.dao.goods;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.soft.core.db.HibernateGenericDao;
import com.soft.core.page.Page;
import com.soft.laboratory.model.goods.GoodsInfo;
@Repository
public class GoodsInfoDao extends HibernateGenericDao<GoodsInfo> {

	public int getTotalDiv(String goodName, String startTime, String endTime) {
		Session session = getSession();
		String sql =" select count(*) from ( "+"select lg.g_name,lg.g_inPrice,lg.g_money,lg.g_credit,lg.g_moneyCre,lg.g_creditMon,lg.g_sum,lgl.payType,lgl.number"+
 " from l_goods lg ,(  select max(lgi.g_code) as goodCode, max(lgi.g_name) as goodName,max(lgi.g_payType) as payType,sum(lgi.g_number) as number"+
 " from l_goods_info lgi where 1=1 ";
	
		if(goodName != null){
			sql = sql +"and lgi.g_name like '%"+goodName+"%'";
		}
		if(startTime != null){
			sql = sql +"and lgi.g_crateDate >='"+startTime+"'";
		}

		if(endTime != null){
			sql = sql +"and lgi.g_crateDate <='"+endTime+"'";
		}
		sql = sql +" GROUP BY lgi.g_code,lgi.g_payType) lgl where lg.g_code = lgl.goodCode) ls";
		
		Query query = session.createSQLQuery(sql);
		Number total = (Number)query.uniqueResult();

		return total.intValue();
	}

	public List listByPageByDiv(Page pagination, String goodName, String startTime, String endTime) {
		Session session = getSession();
		String sql ="select lg.g_name,lg.g_inPrice,lg.g_money,lg.g_credit,lg.g_moneyCre,lg.g_creditMon,lg.g_sum,lgl.payType,lgl.number"+
 " from l_goods lg ,(  select max(lgi.g_code) as goodCode, max(lgi.g_name) as goodName,max(lgi.g_payType) as payType,sum(lgi.g_number) as number"+
 " from l_goods_info lgi where 1=1  ";
		
		if(goodName != null){
			sql = sql +"and lgi.g_name like '%"+goodName+"%'";
		}
		if(startTime != null){
			sql = sql +"and lgi.g_crateDate >='"+startTime+"'";
		}

		if(endTime != null){
			sql = sql +"and lgi.g_crateDate <='"+endTime+"'";
		}
		sql = sql +" GROUP BY lgi.g_code,lgi.g_payType) lgl  where lg.g_code = lgl.goodCode ";
		List query = session.createSQLQuery(sql).setFirstResult(pagination.getResultIndex())
				.setMaxResults(pagination.getPageSize()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return query;
	}

}
