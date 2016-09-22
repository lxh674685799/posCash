package com.soft.core.down;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.soft.core.syscontext.SystemContext;
import com.soft.core.upload.UpLoadUtil;
import com.soft.core.upload.bean.UploadBean;
import com.soft.laboratory.dao.FileDao;
import com.soft.laboratory.model.MyFile;

/**
 * 文件下载
 * @author 刘旭辉
 *
 */
@Controller
@RequestMapping("/download")
public class DownLoadController {

	@Autowired
	FileDao fileDao;

    @RequestMapping("/downFile/{id}")
    	public void mailFile(@PathVariable String id,HttpServletResponse response,HttpServletRequest request) throws IOException{
    		MyFile myFile = fileDao.findById(id);
    		outFile(myFile, response,request,true);
    	}
	
	private void outFile(MyFile myFile,HttpServletResponse response,HttpServletRequest request,boolean inDisk) throws IOException{
		InputStream in = null;
		if(inDisk){
			in = getInputStreamFromDisk(myFile);
		}else{
			in = getInputStream(myFile);
		}
		//如果 myFile为空,没有数据源或数据源为空则提示 文件不存在
		if(myFile==null||in==null){
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.write("<script>") ;
			out.write("alert('文件不存在');") ;
			out.write("history.go(-1);") ;
			out.write("</script>") ;
		}else{
			String fileName = myFile.getFileName() ;
			fileName = URLEncoder.encode(fileName, "UTF-8");//在火狐下文件名有问题
	       
	        if (fileName.length() > 150) {
	            //String guessCharset = request.getLocale().get /*根据request的locale 得出可能的编码，中文操作系统通常是gb2312*/
	            fileName = new String(myFile.getFileName().getBytes("gb2312"), "ISO8859-1"); 
	        }
				response.reset();
				response.setContentType("application/x-msdownload");
				response.setHeader("Content-disposition","attachment; filename="+fileName);
				//以上输出文件元信息
				
				byte[] b = new byte[2048]; 
				int fileLength=0;
				int len = 0; 
				while((len=in.read(b)) >0){
					response.getOutputStream().write(b,0,len);      //向浏览器输出
					fileLength+=len;
				}
				response.setContentLength(fileLength);      //设置输入文件长度
				in.close();         //关闭文件输入流
				response.flushBuffer();
		}
	}
	
	private InputStream getInputStreamFromDisk(MyFile myFile){
		UploadBean uploadBean = UpLoadUtil.getUpLoadBeanFromFile("upload_resource.properties");
    	InputStream input =null;
		if(myFile.getFilePath()==null){
			input= null;
		}else{
			String realUrl = myFile.getFilePath();
	    	File file = new File(uploadBean.getUploadDir()+File.separator+realUrl);
			try {
				input= new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			}
		}
		return input;
	}
	
	private InputStream getInputStream(MyFile myFile) {
		InputStream input =null;
		if(myFile.getFilePath()==null){
			input= null;
		}else{
			//去掉 绝对路径根目录  如: 路径为/resources/new/jquery.js
			//修改后为:/resources/new/jquery.js
			String contextPath = SystemContext.getServletContext().getContextPath();
			String realUrl = myFile.getFilePath();
			if(realUrl.startsWith(contextPath)){
				realUrl = realUrl.substring(contextPath.length());
			}
			myFile.setFilePath(realUrl);
			File file = new File(SystemContext.getRealPath()+myFile.getFilePath());
			try {
				input= new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			}		
		}
		return input;		
	}
}
