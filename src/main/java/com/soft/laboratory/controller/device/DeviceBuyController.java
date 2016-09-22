package com.soft.laboratory.controller.device;

import java.io.IOException;
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
import com.soft.core.syscontext.Const;
import com.soft.core.syscontext.SystemContext;
import com.soft.core.util.FileUtil;
import com.soft.core.util.RequestUtil;
import com.soft.core.util.StringUtil;
import com.soft.core.web.ResultMessage;
import com.soft.laboratory.model.MyFile;
import com.soft.laboratory.model.device.Device;
import com.soft.laboratory.model.device.DeviceBuy;
import com.soft.laboratory.model.user.SysUser;
import com.soft.laboratory.service.FileService;
import com.soft.laboratory.service.device.DeviceBuyService;
import com.soft.laboratory.service.device.DeviceService;
import com.soft.laboratory.service.user.SysUserService;

/**
 * 设备购入控制类
 * @author 刘旭辉
 */
@Controller
@RequestMapping({ "/device/buy" })
public class DeviceBuyController extends GenericController{
	
	@Resource
	private DeviceBuyService deviceBuyService;
	
	@Resource
	private DeviceService deviceService;
	
	@Resource
	private SysUserService userService;
	
	@Resource
	private FileService fileService;
	
	/**
	 * 添加，修改设备购入
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
		List<Device> devices = (List<Device>) deviceService.listAll(new Device());
		DeviceBuy deviceBuy = null;
		if (id != null) {
			deviceBuy= deviceBuyService.getById(id);
			deviceBuy.setRatifyUser(userService.getById(deviceBuy.getRatifyUserId()));
		}else{
			deviceBuy= new DeviceBuy();
		}
		return mv.addObject("returnUrl", returnUrl)
				.addObject("device", deviceBuy)
				.addObject("devices",devices);	
	}
	
	/**
	 * 保存，更新设备购入
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "save" })
	public void save(HttpServletRequest request, HttpServletResponse response,
			DeviceBuy device,MultipartHttpServletRequest attachmentFile) throws Exception {
		String preUrl = request.getParameter("returnUrl");
		ResultMessage message = null;
		String name = device.getRatifyUserName();
		
		SysUser rUser = userService.getByName(name);
		
		if(rUser==null){
			message = new ResultMessage(Const.MESSAGE_WARN, "添加失败，审批人不存在！");
		}else
		{
			device.setRatifyUserId(rUser.getId());	
		  if(device.getId().equals("")){
			deviceBuyService.add(request,device,attachmentFile,rUser);
			message = new ResultMessage(Const.MESSAGE_SUCCESS, "添加成功");
		}
		else{		
			deviceBuyService.update(device);
			message = new ResultMessage(Const.MESSAGE_SUCCESS, "修改成功");
		}
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);	
	}
	
	/**
	 * 转到设备购入列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping({ "list" })
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response,DeviceBuy device) throws Exception {
		SysUser loginUser =SystemContext.getCurrentUser(request);
		String id = loginUser.getId();
		if(!id.equals(Const.SYSTEM_ADMIN_ID)){
			device.setBuyUserId(loginUser.getId());
		}
		int page =1;
		int total = 0;
		if(request.getParameter("page")!=null){
			page = Integer.parseInt(request.getParameter("page"));
			total = Integer.parseInt(request.getParameter("totalCount"));
		}else{
			total = deviceBuyService.getTotal(device); 
		}	

		Page pagination = new Page(page, total);
		String order ="order by d.ratifyTime desc";
		List<DeviceBuy> devices = deviceBuyService.listByPage(pagination,device,order);

		List<Device> ds = (List<Device>) deviceService.listAll(new Device());
		
		
		ModelAndView mv= getAutoView(request);
		mv.addObject("page",pagination).addObject("devices", devices)
		.addObject("device",device).addObject("ds",ds);
		return mv;		
	}
	
	/**
	 * 得到具体的申购设备信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping({ "get" })
	public ModelAndView get(HttpServletRequest request) throws Exception {
		ModelAndView mv= getAutoView(request);
		String returnUrl = RequestUtil.getPrePage(request);
		String buyId = request.getParameter("id");
		DeviceBuy buy = deviceBuyService.getById(buyId);
		MyFile file = new MyFile();
		file.setInfoId(buy.getId());
		List<MyFile> downFile = (List<MyFile>) fileService.listAll(file);
		if(downFile.size()>0){
			mv.addObject("file",downFile.get(0));
		}
		buy.setTotalPrice(StringUtil.hangeToBig(buy.getNumber()*buy.getPrice()));
		
		mv.addObject("buy",buy);
		mv.addObject("returnUrl", returnUrl);
		return mv;		
	}
		
	/**
	 * 删除申购设备信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping({ "del" })
	public void del(HttpServletRequest request, HttpServletResponse response) throws IOException{		
		String[] ids = request.getParameterValues("id");
		String preUrl = RequestUtil.getPrePage(request);
		List<MyFile> files = null;
		for (String id : ids){
			MyFile file = new MyFile();
			file.setInfoId(id);
			files = (List<MyFile>) fileService.listAll(file);
			FileUtil.deleteFile(files.get(0).getFilePath());
			fileService.delById(files.get(0).getId());
		}
			deviceBuyService.delByIds(ids);
		ResultMessage message = new ResultMessage(1, "删除成功！");
		addMessage(message, request);
		response.sendRedirect(preUrl);	
	}

}
