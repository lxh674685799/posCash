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
import com.soft.core.util.Base64Demo;
import com.soft.core.util.RequestUtil;
import com.soft.core.web.ResultMessage;
import com.soft.laboratory.model.user.SysRole;
import com.soft.laboratory.model.user.SysUser;
import com.soft.laboratory.service.user.SysRoleService;
import com.soft.laboratory.service.user.SysUserService;

/**
 * 系统用户控制类
 * @author 翟瑞东
 */
@Controller
@RequestMapping({ "/user/user" })
public class SysUserController extends GenericController{
	
	@Resource
	private SysUserService userService;
	@Resource
	private SysRoleService roleService;

	/**
	 * 添加，修改用户
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping({ "edit" })
	public ModelAndView edit(HttpServletRequest request) throws Exception{
		ModelAndView mv = getAutoView(request);
		String returnUrl = RequestUtil.getPrePage(request);
		String userId = request.getParameter("id");
		SysUser user = null;
		if (userId != null) {
			user= userService.getById(userId);
		}else{
			user= new SysUser();
		}	
		List<SysRole> roles = (List<SysRole>) roleService.listAll(new SysRole());
		return mv.addObject("returnUrl", returnUrl).addObject("roles",roles)
				.addObject("user", user);
	}
	
	/**
	 * 保存，更新用户
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "save" })
	public void save(HttpServletRequest request, HttpServletResponse response,
			SysUser user) throws Exception {
		String preUrl = request.getParameter("returnUrl");
		ResultMessage message = null;
	
		String passWord = Base64Demo.getBASE64(user.getPassWord());
		user.setPassWord(passWord);
		
		if(user.getId().equals("")){
			List<SysUser> exit = userService.isNameExistAdd(user.getAccount(), "account");
			if(exit.size()>0){
				message = new ResultMessage(Const.MESSAGE_ERROR, "添加失败，帐号已存在！");
			}else{
				userService.add(user);
				message = new ResultMessage(Const.MESSAGE_SUCCESS, "添加用户成功！");
			}
		}else{	
			List<SysUser> exit = userService.isNameExistUpdate(user.getAccount(), "account", user.getId());
			if(exit.size()>0){
				message = new ResultMessage(Const.MESSAGE_ERROR, "修改失败，帐号已存在！");
			}else{
				userService.update(user);
				message = new ResultMessage(Const.MESSAGE_SUCCESS, "修改用户成功！");
			}
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 得到用户列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "list" })
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response,SysUser user) throws Exception {
		int page =1;
		int total = 0;
		if(request.getParameter("page")!=null){
			page = Integer.parseInt(request.getParameter("page"));
			total = Integer.parseInt(request.getParameter("totalCount"));
		}else{
		    total = userService.getTotal(user);
		}
		
		Page pagination = new Page(page, total);
		String order ="order by d.id";
		List<SysUser> users = userService.listByPage(pagination,user,order);
	
		ModelAndView mv= getAutoView(request);
		mv.addObject("page",pagination).addObject("user", user)
			.addObject("users", users);
		return mv;	
	}

	/**
	 * 得到具体的用户
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "get" })
	public ModelAndView get(HttpServletRequest request) throws Exception {
		ModelAndView mv = getAutoView(request);
		String returnUrl = RequestUtil.getPrePage(request);
		String userId = request.getParameter("id");
		SysUser user = userService.getById(userId);
		
		mv.addObject("user", user)
			.addObject("returnUrl", returnUrl);
		return mv;
		
	}
		
	/**
	 * 删除用户
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "del" })
	public void del(HttpServletRequest request, HttpServletResponse response) throws IOException{		
		String[] ids = request.getParameterValues("id");
		String preUrl = RequestUtil.getPrePage(request);
		userService.delByIds(ids);
		ResultMessage message = new ResultMessage(Const.MESSAGE_SUCCESS, "删除成功！");
		addMessage(message, request);
		response.sendRedirect(preUrl);	
	}
	
	
	/**
	 * 根据组织机构查找用户
	 * @param isSingle
	 * @param request
	 * @return
	 */
	@RequestMapping("getUserByOrgId")
	public ModelAndView getUserByOrgId(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("tree/userList.jsp");
		String orgId = RequestUtil.getString(request, "orgId");
		String roleId = RequestUtil.getString(request, "roleId");
		boolean isSingle = RequestUtil.getBoolean(request, "isSingle");
		
		int page =1;
		int total = 0;
		if(request.getParameter("page")!=null){
			page = Integer.parseInt(request.getParameter("page"));
			total = Integer.parseInt(request.getParameter("totalCount"));
		}else{
		    total = userService.getTotalForUserByOrgId(orgId,roleId);
		}
		
		Page pagination = new Page(page, total);
			
		List<SysUser> userList = userService.getUserByOrgId(orgId,pagination,roleId);
	
		mv.addObject("isSingle", isSingle);
		mv.addObject("roleId", roleId);
		mv.addObject("orgId", orgId);
		mv.addObject("userList",userList);
		mv.addObject("page",pagination);
		return mv;
	}
	
	/**
	 * 添加，修改用户
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "changeInfo" })
	public ModelAndView changeInfo(HttpServletRequest request) throws Exception{		
		ModelAndView mv = getAutoView(request);
		String userId = request.getParameter("id");
		SysUser user = userService.getById(userId);
			String passWord = user.getPassWord();
			user.setPassWord(Base64Demo.getFromBASE64(passWord));
		return mv.addObject("user", user);
	}
	

	/**
	 * 保存用户修改数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "saveChange" })
	public void saveChange(HttpServletRequest request, HttpServletResponse response,
			SysUser user) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
	
		String passWord = Base64Demo.getBASE64(user.getPassWord());
		user.setPassWord(passWord);

		userService.update(user);
		message = new ResultMessage(Const.MESSAGE_SUCCESS, "修改用户信息成功！");
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
}
