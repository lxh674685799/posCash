package com.soft.laboratory.dao.device;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.soft.core.db.HibernateGenericDao;
import com.soft.core.syscontext.Const;
import com.soft.core.util.DateFormatUtil;
import com.soft.laboratory.model.device.DeviceLoan;

@Repository
public class DeviceLoanDao extends HibernateGenericDao<DeviceLoan>{

	public void returnBack(String loanIds) {
		String hql = "update DeviceLoan l set l.trueReturnTime = '"+DateFormatUtil.getNowByString("")+"' , l.status = '"+Const.DEVICE_RETURN+"' where l.id in ('"+loanIds.replaceAll(",", "','")+"')";
		Session session = getSession(); 
		session.createQuery(hql).executeUpdate();
	}
	
}
