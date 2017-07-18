package com.soft.laboratory.controller.goods;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.soft.core.controller.GenericController;
import com.soft.core.page.Page;
import com.soft.core.syscontext.Const;
import com.soft.core.syscontext.SystemContext;
import com.soft.core.util.DateFormatUtil;
import com.soft.core.util.RequestUtil;
import com.soft.core.web.ResultMessage;
import com.soft.laboratory.model.device.DeviceFactory;
import com.soft.laboratory.model.goods.Goods;
import com.soft.laboratory.model.user.SysUser;
import com.soft.laboratory.service.device.DeviceFactoryService;
import com.soft.laboratory.service.goods.GoodsService;
@Controller
@RequestMapping({ "/goods/goods" })
public class GoodsController extends GenericController {

	@Resource
	private GoodsService goodsService;
	@Resource
	private DeviceFactoryService factoryService;
	
	/**
	 * 得到所有的商品
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "list" })
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response,Goods goods) throws Exception {
		
		String typeName = request.getParameter("typename");
	
		int page =1;
		int total = 0;
		if(request.getParameter("page")!=null){
			page = Integer.parseInt(request.getParameter("page"));
			total = Integer.parseInt(request.getParameter("totalCount"));
		}else{
			total = goodsService.getTotal(goods); 
		}	

		Page pagination = new Page(page, total);
		String order ="order by d.id desc";
		List<Goods> list = goodsService.listByPage(pagination,goods,order);
		goods.setTypeName(typeName);
		ModelAndView mv= getAutoView(request);
		mv.addObject("page",pagination).addObject("Goods", list).addObject("goods", goods);
		return mv;		
	}
	
	@RequestMapping({ "toUpdateSum" })
	public ModelAndView toUpdateSum(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView(request);
		String id = request.getParameter("id");
		String returnUrl = RequestUtil.getPrePage(request);
		Goods goods = null;
		if (id != null) {
			goods= goodsService.getById(id);
		}else{
			goods= new Goods();
		}
		List<DeviceFactory> deviceFactorys = (List<DeviceFactory>) factoryService.listAll(new DeviceFactory());
		return mv.addObject("returnUrl", returnUrl)
				.addObject("goods", goods).addObject("factorys",deviceFactorys);			
	}
	
	@RequestMapping({ "updateSum" })
	public void updateSum(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = request.getParameter("returnUrl");
		String id = request.getParameter("id");
		String updatesum = request.getParameter("updatesum");

		Goods goods = goodsService.getById(id);
		goods.setSum(goods.getSum()+Integer.valueOf(updatesum));
		goodsService.update(goods);
		ResultMessage message  = new ResultMessage(Const.MESSAGE_SUCCESS, "商品入库成功！");

		addMessage(message, request);
		response.sendRedirect(preUrl);	
	}
	
	/**
	 * 保存，更新商品
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "save" })
	public void save(HttpServletRequest request, HttpServletResponse response,
			Goods goods) throws Exception {
		String preUrl = request.getParameter("returnUrl");
		SysUser loginUser =SystemContext.getCurrentUser(request);
		String userId = loginUser.getId();
		ResultMessage message = null;
		if(goods.getId().equals("")){
			List<Goods> exit = goodsService.isNameExistAdd(goods.getCode(), "code");
			if(exit.size()>0){
				message = new ResultMessage(Const.MESSAGE_ERROR, "添加失败，商品编号已存在！");
			}else{
				goods.setCreateTime(DateFormatUtil.getNowByString(""));
				goods.setUserId(userId);
				goodsService.add(goods);
				message = new ResultMessage(Const.MESSAGE_SUCCESS, "添加商品成功！");
			}	
		}else{	
			List<Goods> exit = goodsService.isNameExistUpdate(goods.getCode(), "code", goods.getId());
			if(exit.size()>0){
				message = new ResultMessage(Const.MESSAGE_ERROR, "修改失败，商品编号已存在！");
			}else{
				goodsService.update(goods);
				message = new ResultMessage(Const.MESSAGE_SUCCESS, "修改商品成功！");
			}
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);	
	}
	
	
	/**
	 * 添加，修改商品
	 * @param requests
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "edit" })
	public ModelAndView edit(HttpServletRequest request) throws Exception{
		ModelAndView mv = getAutoView(request);
		String id = request.getParameter("id");
		String returnUrl = RequestUtil.getPrePage(request);
		Goods goods = null;
		if (id != null) {
			goods= goodsService.getById(id);
		}else{
			goods= new Goods();
		}
		List<DeviceFactory> deviceFactorys = (List<DeviceFactory>) factoryService.listAll(new DeviceFactory());
		return mv.addObject("returnUrl", returnUrl)
				.addObject("goods", goods).addObject("factorys",deviceFactorys);	
	}
	
	/**
	 * 得到商品详情
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "get" })
	public ModelAndView get(HttpServletRequest request) throws Exception {
		ModelAndView mv = getAutoView(request);
		String returnUrl = RequestUtil.getPrePage(request);
		String id = request.getParameter("id");
		Goods goods = goodsService.getById(id);	
		return mv.addObject("returnUrl", returnUrl).addObject("goods", goods);		
	}
	
	/**
	 * 删除商品
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
			goodsService.delByIds(ids);
			message = new ResultMessage(Const.MESSAGE_SUCCESS, "删除成功！");
		}catch(Exception e){
			message = new ResultMessage(Const.MESSAGE_ERROR, "删除失败！");
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);	
	}
	
	
	
	 @RequestMapping({"excelExport"})    
    public void exportExcel(HttpServletRequest request, HttpServletResponse response)throws Exception {  
		 List<Goods> list = (List<Goods>) goodsService.listAll(new Goods());
		 final String userAgent = request.getHeader("USER-AGENT");
		 
        HSSFWorkbook wb = goodsService.export(list);    
        //设置excel名称 为中文
        String  fileName = "商品信息.xls"; 
        String finalFileName = null;  
        if(StringUtils.contains(userAgent, "MSIE")){//IE浏览器  
        finalFileName = URLEncoder.encode(fileName,"UTF8");  
	    }else if(StringUtils.contains(userAgent, "Mozilla")){//google,火狐浏览器  
	        finalFileName = new String(fileName.getBytes(), "ISO8859-1");  
	    }else{  
	        finalFileName = URLEncoder.encode(fileName,"UTF8");//其他浏览器  
	    }  
        response.setHeader("Content-Disposition", "attachment; filename=\"" + finalFileName + "\"");//这里设置一下让浏览器弹出下载提示框，而不是直接在浏览器中打开  
        response.setContentType("application/vnd.ms-excel");      
        OutputStream ouputStream = response.getOutputStream();    
        wb.write(ouputStream);    
        ouputStream.flush();    
        ouputStream.close();    
   }     
}
