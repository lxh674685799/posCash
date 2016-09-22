package com.soft.laboratory.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.reflect.TypeToken;
import com.soft.core.controller.GenericController;
import com.soft.core.syscontext.Const;
import com.soft.core.syscontext.SystemContext;
import com.soft.core.util.RequestUtil;
import com.soft.core.util.StringUtil;
import com.soft.core.web.ResultMessage;
import com.soft.laboratory.model.SysResource;
import com.soft.laboratory.model.user.SysRole;
import com.soft.laboratory.model.user.SysUser;
import com.soft.laboratory.service.SysResourceService;
import com.soft.laboratory.service.user.SysRoleService;
import com.soft.laboratory.service.user.SysUserService;

@Controller
@RequestMapping({ "/resource/resource" })
public class SysResourceController extends GenericController{
	
	@Resource
	private SysResourceService resourceService;
	
	@Resource
	private SysRoleService roleService;
	
	@Resource
	private SysUserService userService;

	/**
	 * 编辑系统资源信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "edit" })
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		ModelAndView mv = getAutoView(request);
		String id = request.getParameter("id");
		String superId = request.getParameter("superId");
		String returnUrl = RequestUtil.getPrePage(request);
		SysResource resource = null;
		if (id != null) {
			resource= resourceService.getById(id);
		}else{
			resource= new SysResource();
			resource.setParentId(superId);
		}	
		return mv.addObject("returnUrl", returnUrl)
				.addObject("resource", resource);
	}
	
	/**
	 * 保存，更新系统资源
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "save" })
	public void save(HttpServletRequest request, HttpServletResponse response,
			SysResource resource) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		if(resource.getId().equals("")){
			String id = resourceService.add(resource);
			resource.setId(id);
			SysRole role = roleService.getById(Const.SYSTEM_ADMIN_ID);
			role.getResources().add(resource);
			roleService.update(role);
			
			message = new ResultMessage(1, "添加成功");
		}else{
			resourceService.update(resource);
			message = new ResultMessage(1, "修改成功");
		}		
		addMessage(message, request);
		response.sendRedirect(preUrl);	
	}

	/**
	 * 系统资源列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "list" })
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
				return null;
		
	}
	
	/**
	 * 得到具体的系统资源
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "get" })
	public ModelAndView get(HttpServletRequest request) throws Exception {
		return null;
		
	}
		

	/**
	 * 删除系统资源信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "del" })
	public void del(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String resId = request.getParameter("resourceId");
		boolean isParent = RequestUtil.getBoolean(request, "isParent");
		if(isParent){
			resourceService.delChilds(resId);
		}
		resourceService.del(resId);	
	}
	
	/**
	 * 跳转系统资源树
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
	 * 设置角色相应的系统权限
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "getByRole" })
	public ModelAndView getByRole(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView(request);
		String roleId = request.getParameter("roleId");
		
		SysRole role= roleService.getById(roleId);

		return mv.addObject("role",role);
	}
	
	
	/**
	 * 得到系统角色对应系统权限
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping({ "getRoleTree" })
	@ResponseBody
	public void getRoleTree(HttpServletRequest request,
			HttpServletResponse response) throws Exception {		
		String roleId = request.getParameter("roleId");	
		SysRole role= roleService.getById(roleId);	
		List<SysResource> resources = (List<SysResource>) resourceService.listAll(new SysResource());	
		for(SysResource s:resources){
			for(SysResource s1:role.getResources()){
				if(s.getId().equals(s1.getId())){
					s.setChecked(true);
				}
			}		
		}	
		PrintWriter out = response.getWriter();
		JSONArray jsonarray = JSONArray.fromObject(resources);
		out.println(jsonarray.toString());
	}

	/**
	 *  更新系统角色权限
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "update" })
	public void update(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String res = request.getParameter("res");
		String roleId = request.getParameter("roleId");
		
		List<SysResource> rs = StringUtil.fromJson(res, new TypeToken<List<SysResource>>(){}.getType());
		SysRole role= roleService.getById(roleId);
		role.setResources(rs);
		
		roleService.update(role);
	}

	/**
	 * 系统资源树
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "getTreeDate" })
	@ResponseBody
	public void getTreeDate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {	
		String resourceId = request.getParameter("id");
		String rootId = request.getParameter("rootId");	
		if(resourceId==null){
			if(rootId!=null)
			resourceId=rootId;
			else
			resourceId="";
		}	
		List<SysResource> resourceList = resourceService.getByParentId(resourceId);
		PrintWriter out = response.getWriter();
		
		JSONArray jsonarray = JSONArray.fromObject(resourceList);
		out.println(jsonarray.toString());
	}
	

	/**
	 * 得到系统的菜单
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "menu" })
	public ModelAndView menu(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SysUser user =SystemContext.getCurrentUser(request);
		SysUser loginUser = userService.getById(user.getId());
		SysRole role = roleService.getById(user.getRole().getId());
		List<SysResource> resources = role.getResources();
		List<SysResource> resourceList = new ArrayList<SysResource>();
		for(SysResource r :resources){
			if(r.getParentId().equals("100")){
				resourceList.add(r);
			}
		}
		ModelAndView view = new ModelAndView();
		user.setPost(user.getPost());
		view.addObject("user",loginUser);
		view.setViewName("main.jsp");
		view.addObject("resource", resourceList);
		return view;
	}
	
	/**
	 * 构建系统菜单
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "getMenu" })
	public void getMenu(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String rootId = request.getParameter("rootId");
		
		SysUser user =SystemContext.getCurrentUser(request);
		SysRole role = roleService.getById(user.getRole().getId());
		List<SysResource> resources = role.getResources();
		List<SysResource> resourceList = new ArrayList<SysResource>();
		for(SysResource r :resources){
			if(r.getParentId().equals(rootId)){
				resourceList.add(r);
			}
		}
		PrintWriter out = response.getWriter();

		JSONArray jsonarray = JSONArray.fromObject(resourceList);
		out.println(jsonarray.toString());
	}
}
