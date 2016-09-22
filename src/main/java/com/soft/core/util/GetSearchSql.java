package com.soft.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class GetSearchSql {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String getConditionMap(Object model) {
		StringBuffer sql = new StringBuffer();
		sql.append(" d where ");
		if (model == null)
			return null;
		List<Field> fieldList = new ArrayList<Field>();
		Class class1 = (Class) model.getClass();
		Class upClasses = class1.getSuperclass();
		// 添加父类属性
		fieldList.addAll(Arrays.asList(upClasses.getDeclaredFields()));
		// 添加类属性
		fieldList.addAll(Arrays.asList(class1.getDeclaredFields()));

		for (Field field : fieldList) {
			String fieldName = field.getName();
			Object fieldValue = null;

			try {
				Method method = class1.getMethod(
						"get" + Character.toUpperCase(fieldName.charAt(0))
								+ fieldName.substring(1), model.getClass());
				fieldValue = method.invoke(class1, null);
			} catch (Exception e) {
				try {
					if (!Modifier.isPublic(field.getModifiers())) {
						field.setAccessible(true);
					}
					fieldValue = field.get(model);
				} catch (Exception exception) {

				}
			}
			if (fieldValue != null && !fieldName.equals("serialVersionUID")
					&& !fieldName.equals("sex")
				//	&& !fieldName.equals("orderIndex")
					&& !fieldName.equals("checked")
					&& !fieldName.equals("isParent")) {
				if (fieldValue instanceof String) {
					String fieldValueString = String.valueOf(fieldValue);
					if (StringUtils.isNotBlank(fieldValueString)) {
						sql.append("d." + fieldName + " like " + "'%"
								+ fieldValueString.trim() + "%' and ");
					}
				} else {
					sql.append("d." + fieldName + " like " + "'%" + fieldValue
							+ "%' and ");
				}
			}
		}
		if (sql.length() == 9) {
			sql.delete(sql.length() - 6, sql.length());
		} else {
			sql.delete(sql.length() - 5, sql.length());
		}
		return sql.toString();
	}
}
