package com.soft.laboratory.controller.device;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.soft.core.controller.GenericController;
import com.soft.core.page.Page;
import com.soft.core.syscontext.Const;
import com.soft.core.syscontext.SystemContext;
import com.soft.core.util.RequestUtil;
import com.soft.core.web.ResultMessage;
import com.soft.laboratory.model.device.DeviceEntity;
import com.soft.laboratory.model.device.DeviceRepair;
import com.soft.laboratory.model.user.SysUser;
import com.soft.laboratory.service.device.DeviceEntityService;
import com.soft.laboratory.service.device.DeviceRepairService;

/**
 * 设备维修控制类
 * @author 刘旭辉
 */
@Controller
@RequestMapping({ "/device/repair" })
public class DeviceRepairController extends GenericController{
	
	@Resource
	private DeviceRepairService repairService;
	
	@Resource
	private DeviceEntityService entityService;
	
	
	/**
	 * 添加，修改设备维修信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "edit" })
	public ModelAndView edit(HttpServletRequest request) throws Exception{
		ModelAndView mv = getAutoView(request);
		String id = request.getParameter("id");
		String returnUrl = RequestUtil.getPrePage(request);
		DeviceRepair device = null;
		if (id != null) {
			device= repairService.getById(id);
		}else{
			device= new DeviceRepair();
		}
		return mv.addObject("returnUrl", returnUrl)
				.addObject("loan", device);	
	}
	
	/**
	 * 保存，更新设备维修信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "save" })
	public void save(HttpServletRequest request, HttpServletResponse response,
			DeviceRepair device) throws Exception {
		String preUrl = request.getParameter("returnUrl");
		ResultMessage message = null;
		if(device.getId().equals("")){
				SysUser loginUser = SystemContext.getCurrentUser(request);
				repairService.add(device,loginUser);
				message = new ResultMessage(Const.MESSAGE_SUCCESS, "添加设备维修信息成功！");		
		}else{	
				repairService.update(device);
				message = new ResultMessage(Const.MESSAGE_SUCCESS, "修改设备维修信息成功！");
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);	
	}
	
	/**
	 * 转到设备维修列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "list" })
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response,DeviceRepair device) throws Exception {
		
		SysUser loginUser = SystemContext.getCurrentUser(request);
		if(!loginUser.getId().equals(Const.SYSTEM_ADMIN_ID)){
			device.setSendUserId(loginUser.getId());
		}
		
		int page =1;
		int total = 0;
		if(request.getParameter("page")!=null){
			page = Integer.parseInt(request.getParameter("page"));
			total = Integer.parseInt(request.getParameter("totalCount"));
		}else{
			total = repairService.getTotal(device);     
		}
		
		Page pagination = new Page(page, total);
		String order = "order by d.sendDate desc";
		List<DeviceRepair> devices = repairService.listByPage(pagination,device,order);
		
		ModelAndView mv= getAutoView(request);
		mv.addObject("page",pagination)
			.addObject("repairs", devices)
			.addObject("device",device);
			
		return mv;		
	}
	
	/**
	 * 得到具体的设备维修
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "get" })
	public ModelAndView get(HttpServletRequest request) throws Exception {
		ModelAndView mv = getAutoView(request);
		String returnUrl = RequestUtil.getPrePage(request);
		String repairId = request.getParameter("id");
		DeviceRepair repair = repairService.getById(repairId);
		
		List<DeviceEntity> entitys = (List<DeviceEntity>) entityService.getDevieByIds(repair.getDeviceIds());
		
		mv.addObject("repair",repair)
		.addObject("entitys",entitys)
		.addObject("returnUrl", returnUrl);
		return mv;		
	}
	
	
		
	/**
	 * 删除设备维修
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "del" })
	public void del(HttpServletRequest request, HttpServletResponse response) throws IOException{		
		String[] ids = request.getParameterValues("id");
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try{
			repairService.delByIds(ids);
			message = new ResultMessage(1, "删除成功！");
		}catch(Exception e){
			message = new ResultMessage(Const.MESSAGE_WARN, "删除失败");
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);	
	}

}
