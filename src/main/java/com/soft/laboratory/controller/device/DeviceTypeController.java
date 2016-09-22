package com.soft.laboratory.controller.device;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.soft.core.controller.GenericController;
import com.soft.core.syscontext.Const;
import com.soft.core.util.RequestUtil;
import com.soft.core.web.ResultMessage;
import com.soft.laboratory.model.device.DeviceType;
import com.soft.laboratory.service.device.DeviceTypeService;

/**
 * 设备类型控制类
 * @author 刘旭辉
 */
@Controller
@RequestMapping({ "/device/type" })
public class DeviceTypeController extends GenericController{
	
	@Resource
	private DeviceTypeService typeService;
	
	/**
	 * 添加，修改设备类型
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "edit" })
	public ModelAndView edit(HttpServletRequest request) throws Exception{
		ModelAndView mv = getAutoView(request);
		String id = request.getParameter("id");
		String superId = request.getParameter("superId");
		String superPath = request.getParameter("superPath");
		String returnUrl = RequestUtil.getPrePage(request);
		DeviceType type = null;
		if (id != null) {
			type= typeService.getById(id);
		}else{
			type= new DeviceType();
			type.setParentId(superId);
			type.setPath(superPath);
		}
		return mv.addObject("returnUrl", returnUrl)
				.addObject("type", type);	
	}
	
	/**
	 * 保存，更新设备类型
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "save" })
	public void save(HttpServletRequest request, HttpServletResponse response,
			DeviceType type) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		
		if(type.getId().equals("")){
			List<DeviceType> exit = typeService.isNameExistAdd(type.getName(), "name");
			if(exit.size()>0){
				message = new ResultMessage(Const.MESSAGE_ERROR, "添加失败，设备类型已存在！");
			}else{
				String id = typeService.add(type);
				String path=type.getPath()+id+".";
				type.setPath(path);
				type.setId(id);
				typeService.update(type);
				message = new ResultMessage(Const.MESSAGE_SUCCESS, "添加设备类型成功！");
			}
		}else{	
			List<DeviceType> exit = typeService.isNameExistUpdate(type.getName(), "name", type.getId());
			if(exit.size()>0){
				message = new ResultMessage(Const.MESSAGE_ERROR, "修改失败，角色已存在！");
			}else{
				typeService.update(type);
				message = new ResultMessage(Const.MESSAGE_SUCCESS, "修改角色成功！");
			}
		}	
		addMessage(message, request);
		response.sendRedirect(preUrl);	
	}
	
	/**
	 * 转到设备类型列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "list" })
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response,DeviceType type) throws Exception {
		return null;
	}
	
	/**
	 * 得到具体的类型
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "get" })
	public ModelAndView get(HttpServletRequest request) throws Exception {
		return null;
		
	}
		
	/**
	 * 删除类型
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "del" })
	public void del(HttpServletRequest request, HttpServletResponse response) throws IOException{		
		String path = RequestUtil.getSecureString(request, "path");
		ResultMessage message=null;
		try{
			typeService.delByPath(path);
			message = new ResultMessage(Const.MESSAGE_SUCCESS, "删除成功！");
		}catch(Exception e){
			message = new ResultMessage(Const.MESSAGE_ERROR, "删除失败，所选设备类型已使用！");
		}
		addMessage(message, request);
		response.sendRedirect("edit.do?superId=-1&supserPath=1.");
	}
	
	/**
	 * 跳转设备类型树
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "manage" })
	public ModelAndView manage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return getAutoView(request);
	}

	/**
	 * 设备类型树
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({"getTreeDate" })
	@ResponseBody
	public void getTreeDate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {	
		String typeId = request.getParameter("id");
		if(typeId==null){
			typeId = "-1";
		}
		List<DeviceType> typeList = typeService.getByParentId(typeId);
		PrintWriter out = response.getWriter();
		JSONArray jsonarray = JSONArray.fromObject(typeList);	
		out.println(jsonarray.toString());	
	}
}
