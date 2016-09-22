package com.soft.core.poi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExcelSign {
	String title() ;  //记录每个字段在Excel中的标题
	int order() default 0 ;  //记录每个字段在Excel的排序
}