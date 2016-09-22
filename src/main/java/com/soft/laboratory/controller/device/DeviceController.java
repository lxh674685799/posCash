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
import com.soft.core.util.RequestUtil;
import com.soft.core.web.ResultMessage;
import com.soft.laboratory.model.device.Device;
import com.soft.laboratory.model.device.DeviceFactory;
import com.soft.laboratory.service.device.DeviceFactoryService;
import com.soft.laboratory.service.device.DeviceService;

/**
 * 设备控制类
 * @author 刘旭辉
 */
@Controller
@RequestMapping({ "/device/device" })
public class DeviceController extends GenericController{
	
	@Resource
	private DeviceService deviceService;
	
	@Resource
	private DeviceFactoryService factoryService;
	
	
	/**
	 * 添加，修改设备
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping({ "edit" })
	public ModelAndView edit(HttpServletRequest request) throws Exception{
		ModelAndView mv = getAutoView(request);
		String id = request.getParameter("id");
		String returnUrl = RequestUtil.getPrePage(request);
		List<DeviceFactory> deviceFactorys = (List<DeviceFactory>) factoryService.listAll(new DeviceFactory());
		Device device = null;
		if (id != null) {
			device= deviceService.getById(id);
		}else{
			device= new Device();
		}
		return mv.addObject("returnUrl", returnUrl)
				.addObject("device", device)
				.addObject("factorys",deviceFactorys);	
	}
	
	/**
	 * 保存，更新设备
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "save" })
	public void save(HttpServletRequest request, HttpServletResponse response,
			Device device) throws Exception {
		String preUrl = request.getParameter("returnUrl");
		ResultMessage message = null;
		if(device.getId().equals("")){
			List<Device> exit = deviceService.isNameExistAdd(device.getName(), "name");
			if(exit.size()>0){
				message = new ResultMessage(Const.MESSAGE_ERROR, "添加失败，设备已存在！");
			}else{
				device.setNumber(0L);
				device.setFactoryName(factoryService.getById(device.getFactoryId()).getName());
				deviceService.add(device);
				message = new ResultMessage(Const.MESSAGE_SUCCESS, "添加设备成功！");
			}		
		}else{	
			List<Device> exit = deviceService.isNameExistUpdate(device.getName(), "name", device.getId());
			if(exit.size()>0){
				message = new ResultMessage(Const.MESSAGE_ERROR, "修改失败，设备已存在！");
			}else{
				deviceService.update(device);
				message = new ResultMessage(Const.MESSAGE_SUCCESS, "修改设备成功！");
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
			HttpServletResponse response,Device device) throws Exception {
		int page =1;
		int total = 0;
		if(request.getParameter("page")!=null){
			page = Integer.parseInt(request.getParameter("page"));
			total = Integer.parseInt(request.getParameter("totalCount"));
		}else{
			total = deviceService.getTotal(device);     
		}
		
		Page pagination = new Page(page, total);
		String order ="order by d.id";
		List<Device> devices = deviceService.listByPage(pagination,device,order);
		
		ModelAndView mv= getAutoView(request);
		mv.addObject("page",pagination)
			.addObject("devices", devices)
			.addObject("device",device);
			
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
		ModelAndView mv = getAutoView(request);
		String id = request.getParameter("id");
		String returnUrl = RequestUtil.getPrePage(request);
		Device device = deviceService.getById(id);
		mv.addObject("device", device)
			.addObject("returnUrl", returnUrl);
		return mv;		
	}
		
	/**
	 * 删除设备
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
			deviceService.delByIds(ids);
			message = new ResultMessage(1, "删除成功！");
		}catch(Exception e){
			message = new ResultMessage(Const.MESSAGE_WARN, "删除失败！设备正在申购！");
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);	
	}

}
