package com.soft.laboratory.dao.message;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.soft.core.db.HibernateGenericDao;
import com.soft.core.syscontext.Const;
import com.soft.laboratory.model.message.InnerMessage;

@Repository
public class MessageDao extends HibernateGenericDao<InnerMessage>{

	@SuppressWarnings("unchecked")
	public List<InnerMessage> getNotReadMsg(String id) {
		
		String hql="from InnerMessage d where d.toUserId = '"+id+"' and d.readStatus = '" 
		+Const.MESSAGE_UNREAD+"' and d.type != '"+ Const.USER_MESSAGE_INFO+"'";
		List<InnerMessage> result = (List<InnerMessage>) getSession().createQuery(hql).list();
		return result;
	}
	
}
