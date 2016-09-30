package com.soft.laboratory.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.soft.core.db.HibernateGenericDao;
import com.soft.laboratory.model.ChartModel;

@Repository
public class ChartDao extends HibernateGenericDao<ChartModel>{

	public Map<String, String> queryByMonth(String monthStr) {
		Session session = getSession(); 
		List query = session.createSQLQuery("SELECT sum(lgl.g_count_money) as totalMoney,SUM(lgl.g_receive_credit) as totalCredit from l_goods_log lgl  "+
		"where lgl.g_create_time LIKE '"+monthStr+"%';").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		
		Map queryMap = (Map) query.get(0);
		
		Map<String,String> returnMap = new HashMap<String, String>();
		
		if(queryMap.get("totalMoney") != null){
			returnMap.put("totalMoney",queryMap.get("totalMoney")+"" );
		}else{
			returnMap.put("totalMoney","0" );
		}
		
		if(queryMap.get("totalCredit") != null){
			returnMap.put("totalCredit", queryMap.get("totalCredit")+"");
		}else{
			returnMap.put("totalCredit", "0");
		}
		 
		return returnMap;
	}

	public List queryByEmp(String yearMonth) {
		Session session = getSession(); 
		List query = session.createSQLQuery("SELECT sum(lgl.g_count_money) as totalMoney,SUM(lgl.g_receive_credit) as totalCredit,lu.u_name as userName from l_goods_log lgl,l_user lu  "+
		"where lgl.g_create_time LIKE '"+yearMonth+"%' and lu.u_id = lgl.g_create_user_id GROUP BY lu.u_name;").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return query;
	}
	
}
