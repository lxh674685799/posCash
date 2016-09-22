package com.soft.core.db;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.soft.core.page.Page;
import com.soft.core.util.GetSearchSql;


public abstract class HibernateGenericDao<T>implements GenericDao<T> {
	private Class<T> persistClass;	
	
	 private SessionFactory sessionFactory;  
	 
      
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	 
	@SuppressWarnings("unchecked")
	public HibernateGenericDao( ) {
		persistClass = GenericUtil.getSuperClassGenericType(getClass());
	}
	
	public Serializable createEntity(T t) {
		Session session = getSession(); 
		Serializable oid= null;
		oid = session.save(t);
		return oid;
	}

	public void deleteEntity(T t) {	
		Session session = getSession(); 
		session.delete(t);	
	}
	
	public void modifyEntity(T t) {	
		Session session = getSession(); 
		session.update(t);
	}

	@SuppressWarnings("unchecked")
	public T findById(Serializable id) {
		Session session = getSession(); 
		Object query=null;
		query = session.get(persistClass, id);
		return (T) query;
	}

	public int getTotal(T t) {
		String sql =GetSearchSql.getConditionMap(t);
		String hql ="select count(*) from "+persistClass.getSimpleName()+sql;
		Session session = getSession();
		Query query = session.createQuery(hql);
		Number total = (Number)query.uniqueResult();
		return total.intValue();
	}

	public List<?> listEntity(Page page,String hql, Map<String, Object> params) {
		Session session = getSession();
		Query query = session.createQuery(hql);
		Set<String> names = params.keySet();
		for (String name : names) {
			query.setParameter(name, params.get(name));
		}	
		if(page != null){
		query.setFirstResult(page.getResultIndex())
		.setMaxResults(page.getPageSize());
		}
		return query.list();
	}
	
	public int deleteEntity(String hql) {
		Session session = getSession();	
		int number = session.createQuery(hql).executeUpdate();
		return number;
	}

	public List<?> listAll(T t) {
		Session session = getSession(); 
		String sql =GetSearchSql.getConditionMap(t);
		return session.createQuery(" from " + persistClass.getSimpleName()+sql).list();	
	}
	
	@SuppressWarnings("unchecked")
	public List<T> listByPage(Page page,T t,String order) {
		Session session = getSession();
		String sql =GetSearchSql.getConditionMap(t);
			List<T> list = session.createQuery(" from " + persistClass.getSimpleName()+sql+order)
			.setFirstResult(page.getResultIndex())
			.setMaxResults(page.getPageSize()).list();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> isNameExistAdd(String name,String property){
		Session session = getSession();
		String hql = " from "+persistClass.getSimpleName()+" d where d."+property+" = '"+name+"'";
		return session.createQuery(hql).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> isNameExistUpdate(String name,String property,String id){
		Session session = getSession();
		String hql = " from "+persistClass.getSimpleName()+" d where d."+property+" = '"+name+"'" +
				" and d.id <> '"+id+"'";
		return session.createQuery(hql).list();
	}
}
