package com.soft.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.soft.core.syscontext.SystemContext;

/**
 * 此类为 删除项目中的文件(一般为上传过的，废弃的文件)
 * 只删除文件不删除目录。
 * 
 * @author zxl :)
 * @version 1.0   
 * date   2011-11-1
 * time   上午10:45:47
 */
public class FileUtil {
	public static void deleteFile(List<String> urls){
		for(String url:urls){
			deleteFile(url);
		}
	}
	public static void deleteFile(String url){
		if(StringUtils.isBlank(url))return;
		String contextPath = SystemContext.getServletContext().getContextPath();
		String realUrl =url;
		if(realUrl.startsWith(contextPath)){
			realUrl = realUrl.substring(contextPath.length());
		}
		File file = new File(SystemContext.getRealPath()+realUrl);
		deleteFile(file);
	}
	public static void deleteFile(File file){ 
		   if(file.exists()){ 
		    if(file.isFile()){ 
		     file.delete(); 
		    }else if(file.isDirectory()){ 
		     File files[] = file.listFiles(); 
		     for(int i=0;i<files.length;i++){ 
		      deleteFile(files[i]); 
		     } 
		     return;
		    } 
		    file.delete(); 
		   }
		}

	
	public static String getFilePrefix(String fileName){
		int splitIndex = fileName.lastIndexOf(".");
        return fileName.substring(0, splitIndex);
	}
	
	public static String getFileSufix(String fileName){
		int splitIndex = fileName.lastIndexOf(".");
        return fileName.substring(splitIndex + 1);
	}
	
	public static void copyFile(String inputFile,String outputFile) throws FileNotFoundException{
		File sFile = new File(inputFile);
		File tFile = new File(outputFile);
		FileInputStream fis = new FileInputStream(sFile);
		FileOutputStream fos = new FileOutputStream(tFile);
		int temp = 0;  
        try {  
			while ((temp = fis.read()) != -1) {  
			    fos.write(temp);  
			}
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally{
            try {
				fis.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        } 
	}
	
//	public static void deleteFiles(String url) throws Exception{
//		
//		if(StringUtils.isBlank(url))return;
//		String contextPath = SystemContext.getServletContext().getContextPath();
//		String realUrl =url;
//		
//		if(realUrl.indexOf(contextPath)!= -1){
//			realUrl = realUrl.substring(realUrl.indexOf(contextPath)+contextPath.length());
//			realUrl = realUrl.replaceAll("/" ,Matcher.quoteReplacement(File.separator));	
//		}
//		File file = new File(SystemContext.getRealPath()+realUrl);
//		deleteFile(file);
//	}

}
