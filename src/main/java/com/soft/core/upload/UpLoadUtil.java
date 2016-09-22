package com.soft.core.upload;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;

import com.soft.core.upload.bean.UploadBean;

public class UpLoadUtil {
	/**
	 * 根据配置文件获取uploadbean
	 * @return
	 */
	public static com.soft.core.upload.bean.UploadBean getUpLoadBeanFromFile(String propertyFileName) {
		BeanDefinitionRegistry reg = new DefaultListableBeanFactory(); 
    	PropertiesBeanDefinitionReader reader = new PropertiesBeanDefinitionReader(reg); 
    	reader.loadBeanDefinitions(new ClassPathResource(propertyFileName)); 
    	BeanFactory factory = (BeanFactory) reg;
    	com.soft.core.upload.bean.UploadBean uploadBean = (UploadBean) factory.getBean("uploadBean"); 
    	return uploadBean;
	}
}
