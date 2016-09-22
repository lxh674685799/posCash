package com.soft.laboratory.controller.device;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.soft.core.controller.GenericController;
import com.soft.core.page.Page;
import com.soft.core.poi.ExcelUtil;
import com.soft.core.syscontext.Const;
import com.soft.core.util.RequestUtil;
import com.soft.core.web.ResultMessage;
import com.soft.laboratory.model.device.DeviceFactory;
import com.soft.laboratory.service.device.DeviceFactoryService;

/**
 * 设备厂家控制类
 * @author 刘旭辉
 */
@Controller
@RequestMapping({ "/device/factory" })
public class DeviceFactoryController extends GenericController{
	
	@Resource
	private DeviceFactoryService factoryService;
	
	/**
	 * 添加，修改设备厂家
	 * @param requests
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "edit" })
	public ModelAndView edit(HttpServletRequest request) throws Exception{
		ModelAndView mv = getAutoView(request);
		String id = request.getParameter("id");
		String returnUrl = RequestUtil.getPrePage(request);
		DeviceFactory factory = null;
		if (id != null) {
			factory= factoryService.getById(id);
		}else{
			factory= new DeviceFactory();
		}
		return mv.addObject("returnUrl", returnUrl)
				.addObject("factory", factory);	
	}
	
	/**
	 * 保存，更新设备厂家
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "save" })
	public void save(HttpServletRequest request, HttpServletResponse response,
			DeviceFactory factory) throws Exception {
		String preUrl = request.getParameter("returnUrl");
		ResultMessage message = null;
		if(factory.getId().equals("")){
			List<DeviceFactory> exit = factoryService.isNameExistAdd(factory.getName(), "name");
			if(exit.size()>0){
				message = new ResultMessage(Const.MESSAGE_ERROR, "添加失败，设备厂家已存在！");
			}else{
				factoryService.add(factory);
				message = new ResultMessage(Const.MESSAGE_SUCCESS, "添加设备厂家成功！");
			}	
		}else{	
			List<DeviceFactory> exit = factoryService.isNameExistUpdate(factory.getName(), "name", factory.getId());
			if(exit.size()>0){
				message = new ResultMessage(Const.MESSAGE_ERROR, "修改失败，设备厂家已存在！");
			}else{
				factoryService.update(factory);
				message = new ResultMessage(Const.MESSAGE_SUCCESS, "修改设备厂家成功！");
			}
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);	
	}
	
	/**
	 * 转到设备厂家列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "list" })
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response,DeviceFactory factory) throws Exception {
		int page =1;
		int total = 0;
		if(request.getParameter("page")!=null){
			page = Integer.parseInt(request.getParameter("page"));
			total = Integer.parseInt(request.getParameter("totalCount"));
		}else{
			total = factoryService.getTotal(factory);   
		}
		
		Page pagination = new Page(page, total);
		String order = "order by d.id";
		List<DeviceFactory> factorys = factoryService.listByPage(pagination,factory,order) ;
		
		ModelAndView mv= getAutoView(request);
		mv.addObject("page",pagination).addObject("factorys", factorys);
		return mv;		
	}
	
	/**
	 * 得到具体的厂家
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "get" })
	public ModelAndView get(HttpServletRequest request) throws Exception {
		ModelAndView mv = getAutoView(request);
		String returnUrl = RequestUtil.getPrePage(request);
		String id = request.getParameter("factoryId");
		DeviceFactory factory = factoryService.getById(id);	
		return mv.addObject("returnUrl", returnUrl).addObject("factory", factory);		
	}
		
	/**
	 * 删除厂家
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "del" })
	public void del(HttpServletRequest request, HttpServletResponse response) throws IOException{		
		String[] ids = request.getParameterValues("id");
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			factoryService.delByIds(ids);
			message = new ResultMessage(Const.MESSAGE_SUCCESS, "删除成功！");
		}catch(Exception e){
			message = new ResultMessage(Const.MESSAGE_ERROR, "删除失败，所选厂家存在设备！");
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);	
	}
		
	/**
	 * 下载厂家信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked" })
	@RequestMapping({ "exp" })
	public void exp(HttpServletRequest request, HttpServletResponse response) throws IOException{
		ExcelUtil util = ExcelUtil.getInstance() ;
		List<DeviceFactory> list = (List<DeviceFactory>) this.factoryService.listAll(new DeviceFactory());
		String fileName = "厂家信息.xls";
	    response.reset();
		response.setHeader("Content-Disposition","attachment; filename="+new String(fileName.getBytes("gb2312"), "iso8859-1"));
		response.setContentType("application/ms-excel; charset=utf-8");
		util.exprot2ObjByTemp("/模版/厂家信息模版.xls", response.getOutputStream(), DeviceFactory.class, list, 2, null, false);
	}
	
	/**
	 * 下载模版
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "down" })
	public void down(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.reset();
		response.setHeader("Content-Disposition","attachment; filename="+new String("厂家信息模版.xls".getBytes("gb2312"), "iso8859-1"));
		response.setContentType("application/ms-excel; charset=utf-8");

		InputStream in = DeviceFactoryController.class.getResourceAsStream("/模版/厂家信息模版.xls");
		byte[] b = new byte[2048]; 
		int fileLength=0;
		int len = 0; 
		while((len=in.read(b)) >0){
			response.getOutputStream().write(b,0,len); //向浏览器输出
			fileLength+=len;
		}
		response.setContentLength(fileLength); //设置输入文件长度
		in.close();//关闭文件输入流
		response.flushBuffer();
	}
	
	/**
	 * 导入厂家信息
	 * @param request
	 * @return
	 * @throws IOException 
	 * @throws Exception
	 */
	@RequestMapping({ "imp" })
	public void imp(HttpServletRequest request, HttpServletResponse response,MultipartHttpServletRequest attachmentFile) throws IOException{
		ResultMessage message = null;
			
		InputStream	infFile = attachmentFile.getFile("impFile").getInputStream();
		
		try {
			List<DeviceFactory> deviceFactorys = ExcelUtil.getInstance().readExcel2Obj(infFile, DeviceFactory.class, 2, 0) ;
		int success =0;
		int falseNumber =0;
		for(DeviceFactory d : deviceFactorys) {
			if(!(d.getName().equals(null)||d.getName().equals(""))){
			factoryService.add(d);
			success++;
		}else{
			falseNumber++;
			}	
		}
			message = new ResultMessage(Const.MESSAGE_WARN, "成功添加："+success+"条记录，添加失败："+falseNumber+"条！");
		} catch (Exception e) {
			message = new ResultMessage(Const.MESSAGE_ERROR,"导入失败,请严格按照模版格式填写！");
			e.printStackTrace();
		}
		addMessage(message, request);
		response.sendRedirect(RequestUtil.getPrePage(request));	
	}
	
	/**
	 * 导入厂家信息页面跳转
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "impUI" })
	public ModelAndView impUI(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mv = getAutoView(request);
		String returnUrl = RequestUtil.getPrePage(request);
		return mv.addObject("returnUrl", returnUrl);
	}
}
