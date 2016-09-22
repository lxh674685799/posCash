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
import com.soft.core.util.DateFormatUtil;
import com.soft.core.util.RequestUtil;
import com.soft.core.web.ResultMessage;
import com.soft.laboratory.model.device.DeviceEntity;
import com.soft.laboratory.model.device.DeviceGet;
import com.soft.laboratory.model.user.SysUser;
import com.soft.laboratory.service.device.DeviceEntityService;
import com.soft.laboratory.service.device.DeviceGetService;

/**
 * 设备发放信息控制类
 * @author 刘旭辉
 */
@Controller
@RequestMapping({ "/device/get" })
public class DeviceGetController extends GenericController{
	
	@Resource
	private DeviceGetService getService;
	
	@Resource
	private DeviceEntityService entityService;
	
	/**
	 * 添加，修改设备发放信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "edit" })
	public ModelAndView edit(HttpServletRequest request) throws Exception{
		ModelAndView mv = getAutoView(request);
		String id = request.getParameter("id");
		String returnUrl = RequestUtil.getPrePage(request);
		DeviceGet device = null;
		if (id != null) {
			device= getService.getById(id);
		}else{
			device= new DeviceGet();
		}
		return mv.addObject("returnUrl", returnUrl)
				.addObject("getDevice", device);	
	}
	
	/**
	 * 保存，更新发放信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "save" })
	public void save(HttpServletRequest request, HttpServletResponse response,
			DeviceGet device) throws Exception {
		String preUrl = request.getParameter("returnUrl");
		ResultMessage message = null;
		if(device.getId().equals("")){
			SysUser loginUser = SystemContext.getCurrentUser(request);	
			device.setSendUserId(loginUser.getId());
			device.setGetDate(DateFormatUtil.getNowByString(""));
			device.setStatus(Const.DEVICE_LOAN);
			String entityId = getService.add(device);
			
			int status = Const.DEVICE_STATUS_GET;
			
			String deviceIds = device.getDeviceIds();
			entityService.deviceEntity(deviceIds,entityId,status);
			
			message = new ResultMessage(Const.MESSAGE_SUCCESS, "添加设备发放信息成功！");	
		}else{	
			getService.update(device);
			message = new ResultMessage(Const.MESSAGE_SUCCESS, "修改设备发放信息成功！");
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);	
	}
	
	/**
	 * 转到设备发放信息列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "list" })
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response,DeviceGet device) throws Exception {
		int page =1;
		int total = 0;
		if(request.getParameter("page")!=null){
			page = Integer.parseInt(request.getParameter("page"));
			total = Integer.parseInt(request.getParameter("totalCount"));
		}else{
			total = getService.getTotal(device);     
		}
		
		Page pagination = new Page(page, total);
		String order= "order by d.getDate desc";
		List<DeviceGet> devices = getService.listByPage(pagination,device,order);

		ModelAndView mv= getAutoView(request);
		mv.addObject("page",pagination)
			.addObject("devices", devices);
			
		return mv;		
	}
	
	/**
	 * 得到具体的设备发放信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "get" })
	public ModelAndView get(HttpServletRequest request) throws Exception {
		ModelAndView mv = getAutoView(request);
		String id = request.getParameter("id");
		String returnUrl = RequestUtil.getPrePage(request);
		
		DeviceGet getDevice = getService.getById(id);
		
		String deviceIds = getDevice.getDeviceIds();
		
		List<DeviceEntity> entitys = (List<DeviceEntity>) entityService.getDevieByIds(deviceIds);
		mv.addObject("getDevice",getDevice)
			.addObject("entitys",entitys)
			.addObject("returnUrl", returnUrl);	
		return mv;		
	}
		
	/**
	 * 删除设备发放信息
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
			getService.delByIds(ids);
			message = new ResultMessage(1, "删除成功！");
		}catch(Exception e){
			message = new ResultMessage(Const.MESSAGE_WARN, "删除失败！");
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);	
	}

}
