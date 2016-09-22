package com.soft.core.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.soft.core.db.GenericDao;
import com.soft.core.page.Page;

@Transactional
public abstract class GenericService<T> {
	
	protected abstract GenericDao<T> getEntityDao();

	public String add(T entity) {
		return getEntityDao().createEntity(entity).toString();
	}

	public void delById(String id) {
		getEntityDao().deleteEntity(getEntityDao().findById(id));
	}
	
	public void delByIds(String[] ids) {
		for (String id : ids)
			delById(id);
	}
	
	public void update(T entity) {
		getEntityDao().modifyEntity(entity);
	}

	public T getById(Serializable id) {
		return getEntityDao().findById(id);
	}
	
	public List<?> listAll(T t) {
		return getEntityDao().listAll(t);		
	}
	
	public int getTotal(T t) {
		return getEntityDao().getTotal(t);		
	}

	public List<?> listEntity(Page page,String hql, Map<String, Object> params) {
		return getEntityDao().listEntity(page,hql, params);
	}

	public List<T> listByPage(Page page,T t,String order) {	
		return getEntityDao().listByPage(page,t,order);
	}
	
	public List<T> isNameExistAdd(String name,String property){
		return getEntityDao().isNameExistAdd(name, property);
	}
	
	public List<T> isNameExistUpdate(String name,String property,String id){
		return getEntityDao().isNameExistUpdate(name, property, id);
	}

}
