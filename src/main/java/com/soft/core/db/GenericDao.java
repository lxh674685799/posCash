package com.soft.core.db;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.soft.core.page.Page;


public abstract interface GenericDao<T> {
	
	T findById(Serializable id);
	List<?> listAll(T t);
	List<?> listEntity(Page page,String hql, Map<String, Object> params);
	Serializable createEntity(T t);
	void deleteEntity(T t);
	void modifyEntity(T t);
	List<T> listByPage(Page page,T t,String order);
	int getTotal(T t);
	List<T> isNameExistAdd(String name,String property);
	List<T> isNameExistUpdate(String name,String property,String id);
}