package com.soft.laboratory.controller.device;

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
import com.soft.core.util.RequestUtil;
import com.soft.core.web.ResultMessage;
import com.soft.laboratory.model.device.DeviceEntity;
import com.soft.laboratory.service.device.DeviceEntityService;

/**
 * 设备控制类
 * @author 刘旭辉
 */
@Controller
@RequestMapping({ "/device/entity" })
public class DeviceEntityController extends GenericController{
	
	@Resource
	private DeviceEntityService deviceEntityService;
	
	/**
	 * 转到设备列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "list" })
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response,DeviceEntity device) throws Exception {
		String deviceId = RequestUtil.getSecureString(request, "deviceId");
		String returnUrl = request.getParameter("returnUrl");
		if(returnUrl == null){
			returnUrl = RequestUtil.getPrePage(request);
		}
		device.setDeviceId(deviceId);
		int page =1;
		int total = 0;
		if(request.getParameter("page")!=null){
			page = Integer.parseInt(request.getParameter("page"));
			total = Integer.parseInt(request.getParameter("totalCount"));
		}else{
			total = deviceEntityService.getTotal(device);     
		}
		
		Page pagination = new Page(page, total);
		String order ="order by d.number desc";
		List<DeviceEntity> devices = deviceEntityService.listByPage(pagination,device,order);
	
		ModelAndView mv= getAutoView(request);
		mv.addObject("page",pagination).addObject("returnUrl", returnUrl)
			.addObject("deviceId", deviceId)
			.addObject("devices", devices);
			
		return mv;		
	}
	
	/**
	 * 得到具体的设备
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "get" })
	public ModelAndView get(HttpServletRequest request) throws Exception {
		return null;		
	}
	
	/**
	 * 得到具体的设备
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "toRepair" })
	public void toRepair(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		String[] id = request.getParameterValues("id");
		
		StringBuffer ids = new StringBuffer();	
		for(String deviceId : id){
			ids.append(deviceId+",");
		}
		String deviceIds = ids.deleteCharAt(ids.length()-1).toString();
		
		int status = Const.DEVICE_STATUS_REPAIR;
		
		deviceEntityService.deviceEntity(deviceIds,null,status);
		
		ResultMessage message = new ResultMessage(Const.MESSAGE_SUCCESS, "设备报修成功！");
		addMessage(message, request);	
		response.sendRedirect(preUrl);
	}
		
	/**
	 * 根据组织机构查找设备
	 * @param isSingle
	 * @param request
	 * @return
	 */
	@RequestMapping("getDeviceByOrgId")
	public ModelAndView getUserByOrgId(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("tree/deviceList.jsp");
		String orgId = RequestUtil.getString(request, "orgId");
		boolean isSingle = RequestUtil.getBoolean(request, "isSingle");
		int status = RequestUtil.getInt(request, "status");
		
		int page =1;
		int total = 0;
		if(request.getParameter("page")!=null){
			page = Integer.parseInt(request.getParameter("page"));
			total = Integer.parseInt(request.getParameter("totalCount"));
		}else{
		    total = deviceEntityService.getTotalForDeviceByOrgId(orgId,status);
		}
		
		Page pagination = new Page(page, total);
		
		List<DeviceEntity> deviceList = deviceEntityService.getDeviceByOrgId(orgId,status,pagination);
	
		mv.addObject("isSingle", isSingle);
		mv.addObject("orgId",orgId);
		mv.addObject("deviceList",deviceList);
		mv.addObject("page",pagination);
		return mv;
	}
}
