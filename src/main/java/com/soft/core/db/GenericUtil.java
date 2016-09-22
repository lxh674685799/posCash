package com.soft.core.db;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GenericUtil {
	private GenericUtil(){
	}
	
	@SuppressWarnings("rawtypes")
	public static Class getSuperClassGenericType(Class clazz){
		return getSuperClassGenericType(clazz, 0);
	}
	
	@SuppressWarnings("rawtypes")
	public static Class getSuperClassGenericType(Class clazz, int index){
		//取得该类的实体类型
		Type genType = clazz.getGenericSuperclass();
		
		//判断该类是否是泛型类
		if(!(genType instanceof ParameterizedType)) {
			return Object.class;
		}
		
		//返回该类的实际参数类型
		Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
		if( index >= params.length || index < 0){
			return Object.class;
		}
		
		if(!(params[index] instanceof Class)){
			return Object.class;
		}
		
		//返回该类的泛型类
		return (Class)params[index];
	}
}
