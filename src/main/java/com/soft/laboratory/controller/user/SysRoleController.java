package com.soft.laboratory.controller.user;

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
import com.soft.laboratory.model.user.SysRole;
import com.soft.laboratory.service.user.SysRoleService;

/**
 * 系统角色控制类
 * @author 翟瑞东
 */
@Controller
@RequestMapping({ "/user/role/" })
public class SysRoleController extends GenericController{
	
	@Resource
	private SysRoleService roleService;
	
	/**
	 * 添加，系统角色
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "edit" })
	public ModelAndView edit(HttpServletRequest request) throws Exception{
		ModelAndView mv = getAutoView(request);
		String id = request.getParameter("id");
		String returnUrl = RequestUtil.getPrePage(request);
		SysRole role = null;
		if (id != null) {
			role= roleService.getById(id);
		}else{
			role= new SysRole();
		}
		return mv.addObject("returnUrl", returnUrl)
				.addObject("role", role);	
	}
	
	/**
	 * 保存，更新系统角色
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "save" })
	public void save(HttpServletRequest request, HttpServletResponse response,
			SysRole role) throws Exception {
		String preUrl = request.getParameter("returnUrl");
		ResultMessage message = null;
		if(role.getId().equals("")){
			List<SysRole> exit = roleService.isNameExistAdd(role.getName(), "name");
			if(exit.size()>0){
				message = new ResultMessage(Const.MESSAGE_ERROR, "添加失败，角色已存在！");
			}else{
				roleService.add(role);
				message = new ResultMessage(Const.MESSAGE_SUCCESS, "添加角色成功！");
			}
		}else{
			List<SysRole> exit = roleService.isNameExistUpdate(role.getName(), "name", role.getId());
			if(exit.size()>0){
				message = new ResultMessage(Const.MESSAGE_ERROR, "修改失败，角色已存在！");
			}else{
				roleService.update(role);
				message = new ResultMessage(Const.MESSAGE_SUCCESS, "修改角色成功！");
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
			HttpServletResponse response,SysRole role) throws Exception {
		int page =1;
		int total = 0;
		if(request.getParameter("page")!=null){
			page = Integer.parseInt(request.getParameter("page"));
			total = Integer.parseInt(request.getParameter("totalCount"));
		}else{
			total = roleService.getTotal(role);  
		}    
		
		Page pagination = new Page(page, total);
		String order ="order by d.id";
		List<SysRole> roles = roleService.listByPage(pagination,role,order);
	
		ModelAndView mv= getAutoView(request);
		mv.addObject("page",pagination)
			.addObject("roles", roles)
			.addObject("role",role);
		return mv;		
	}
	
	/**
	 * 得到具体角色
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "get" })
	public ModelAndView get(HttpServletRequest request) throws Exception {
		return null;		
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
		roleService.delByIds(ids);
		ResultMessage message = new ResultMessage(Const.MESSAGE_SUCCESS, "删除设备成功！");
		addMessage(message, request);
		response.sendRedirect(preUrl);	
	}
}
