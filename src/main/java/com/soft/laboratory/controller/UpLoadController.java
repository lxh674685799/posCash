package com.soft.laboratory.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.soft.core.controller.GenericController;
import com.soft.core.upload.UpLoadContext;
import com.soft.core.upload.UploadImage;
import com.soft.core.upload.UploadResource;


/**
 * 此类为   上传文件类
 *
 * @author 
 * @version 1.0
 */
@Controller
@RequestMapping("/upload")
public class UpLoadController extends GenericController{
    @RequestMapping(value = "/image")
    public void image(
        @RequestParam(value = "imgFile", required = false)
    MultipartFile imgFile,
        HttpServletResponse response) throws Exception {
    	UpLoadContext upLoad = new UpLoadContext(new UploadImage());
    	upLoad.uploadFile(imgFile,response);
    }
    //上传共享资源文件
    @RequestMapping(value="/resource")
    public void resource(
    		@RequestParam(value="resourceFile",required=false)MultipartFile file,
    		HttpServletResponse response)throws Exception{
    	UpLoadContext upLoad=new UpLoadContext(new UploadResource());
    	upLoad.uploadFile(file, response);
    }
}
