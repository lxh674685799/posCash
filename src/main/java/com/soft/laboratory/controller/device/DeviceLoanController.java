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
import com.soft.laboratory.model.device.DeviceLoan;
import com.soft.laboratory.model.user.SysUser;
import com.soft.laboratory.service.device.DeviceEntityService;
import com.soft.laboratory.service.device.DeviceLoanService;

/**
 * 设备控制类
 * @author 刘旭辉
 */
@Controller
@RequestMapping({ "/device/loan" })
public class DeviceLoanController extends GenericController{
	
	@Resource
	private DeviceLoanService loanService;
	
	@Resource
	private DeviceEntityService entityService;
	
	
	/**
	 * 添加，修改设备借出信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "edit" })
	public ModelAndView edit(HttpServletRequest request) throws Exception{
		ModelAndView mv = getAutoView(request);
		String id = request.getParameter("id");
		String returnUrl = RequestUtil.getPrePage(request);
		DeviceLoan device = null;
		if (id != null) {
			device= loanService.getById(id);
		}else{
			device= new DeviceLoan();
		}
		return mv.addObject("returnUrl", returnUrl)
				.addObject("loan", device);	
	}
	
	/**
	 * 保存，更新设备借出信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "save" })
	public void save(HttpServletRequest request, HttpServletResponse response,
			DeviceLoan device) throws Exception {
		String preUrl = request.getParameter("returnUrl");
		ResultMessage message = null;
		if(device.getId().equals("")){
		
				SysUser loginUser = SystemContext.getCurrentUser(request);	
				device.setLoanUserId(loginUser.getId());
				device.setLoanTime(DateFormatUtil.getNowByString(""));
				device.setStatus(Const.DEVICE_LOAN);
				String entityId = loanService.add(device);
				
				int status = Const.DEVICE_STATUS_LOAN;
				
				String deviceIds = device.getDeviceIds();
				entityService.deviceEntity(deviceIds,entityId,status);
						
				message = new ResultMessage(Const.MESSAGE_SUCCESS, "添加设备借出信息成功！");
					
		}else{	
			loanService.update(device);
			message = new ResultMessage(Const.MESSAGE_SUCCESS, "修改设备借出信息成功！");
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);	
	}
	
	/**
	 * 转到设备借出列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "list" })
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response,DeviceLoan loan) throws Exception {
		SysUser loginUser =SystemContext.getCurrentUser(request);
		String id = loginUser.getId();
		if(!id.equals(Const.SYSTEM_ADMIN_ID)){
			loan.setLoanUserId(loginUser.getId());
		}
		int page =1;
		int total = 0;
		if(request.getParameter("page")!=null){
			page = Integer.parseInt(request.getParameter("page"));
			total = Integer.parseInt(request.getParameter("totalCount"));
		}else{
			total = loanService.getTotal(loan);     
		}
		
		Page pagination = new Page(page, total);
		String order = "order by d.status asc, d.returnTime asc";
		List<DeviceLoan> devices = loanService.listByPage(pagination,loan,order);
	
		ModelAndView mv= getAutoView(request);
		
		String  nowTime = DateFormatUtil.getNowByString("");
		
		mv.addObject("page",pagination)
			.addObject("devices", devices)
			.addObject("device",loan)
			.addObject("nowTime",nowTime);		
		return mv;		
	}
	
	/**
	 * 得到设备借出详情
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "get" })
	public ModelAndView get(HttpServletRequest request) throws Exception {
		ModelAndView mv= getAutoView(request);
		String returnUrl = RequestUtil.getPrePage(request);
		String loanId = request.getParameter("id");
		DeviceLoan loan = loanService.getById(loanId);
		
		String deviceIds = loan.getDeviceIds();
		
		List<DeviceEntity> entitys = (List<DeviceEntity>) entityService.getDevieByIds(deviceIds);
		
		String  nowTime = DateFormatUtil.getNowByString("");
		
		mv.addObject("loan",loan)
			.addObject("entitys",entitys)
			.addObject("returnUrl", returnUrl)
			.addObject("nowTime",nowTime);
		
		return mv;
	}
	
	/**
	 * 设备归还操作
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "returnBack" })
	public void returnBack(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		String[] id = request.getParameterValues("id");
		
		StringBuffer ids = new StringBuffer();	
		for(String loanId : id){
			ids.append(loanId+",");
		}	
		
		String loanIds = ids.deleteCharAt(ids.length()-1).toString();
		loanService.returnBack(loanIds);
		
		int status = Const.DEVICE_STATUS_NORMAL;
		entityService.changeEntityStatus(loanIds,status);
				
		ResultMessage message = new ResultMessage(Const.MESSAGE_SUCCESS, "设备归还成功！");
		addMessage(message, request);	
		response.sendRedirect(preUrl);
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
			loanService.delByIds(ids);
			message = new ResultMessage(Const.MESSAGE_SUCCESS, "删除成功！");
		}catch(Exception e){
			message = new ResultMessage(Const.MESSAGE_WARN, "删除失败！设备正在申购！");
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);	
	}

}
