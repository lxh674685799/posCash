package com.soft.laboratory.controller.user;

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
import com.soft.laboratory.model.user.SysOrg;
import com.soft.laboratory.service.user.SysOrgService;

/**
 * 系统组织机构控制类
 * @author 翟瑞东
 */
@Controller
@RequestMapping({ "/user/org" })
public class SysOrgController extends GenericController{
	
	@Resource
	private SysOrgService postService;
	
	/**
	 * 添加，修改系统组织机构
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
		SysOrg post = null;
		if (id != null) {
			post= postService.getById(id);
		}else{
			post= new SysOrg();
			post.setParentId(superId);
			post.setPath(superPath);
		}
		return mv.addObject("returnUrl", returnUrl)
				.addObject("post", post);	
	}
	
	/**
	 * 保存，更新组织机构
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "save" })
	public void save(HttpServletRequest request, HttpServletResponse response,
			SysOrg post) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		if(post.getId().equals("")){
			List<SysOrg> exit = postService.isNameExistAdd(post.getCode(), "code");
			if(exit.size()>0){
				message = new ResultMessage(Const.MESSAGE_ERROR, "添加失败，组织机构代码已存在！");
			}else{
				String id = postService.add(post);
				
				post.setPath(post.getPath()+id+".");
				
				postService.update(post);
				message = new ResultMessage(Const.MESSAGE_SUCCESS, "添加组织机构成功！");
			}
		}else{	
			List<SysOrg> exit = postService.isNameExistUpdate(post.getCode(), "code", post.getId());
			if(exit.size()>0){
				message = new ResultMessage(Const.MESSAGE_ERROR, "修改失败，组织机构代码已存在！");
			}else{
				postService.update(post);
				message = new ResultMessage(Const.MESSAGE_SUCCESS, "修改组织机构成功！");
			}
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);	
	}
	
	/**
	 * 转到组织机构列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "list" })
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response,SysOrg post) throws Exception {
		return null;
	}
	
	/**
	 * 得到具体的组织机构
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "get" })
	public ModelAndView get(HttpServletRequest request) throws Exception {
		return null;
		
	}
		
	/**
	 * 删除系统组织机构
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "del" })
	public void del(HttpServletRequest request, HttpServletResponse response) throws IOException{		
		String path = RequestUtil.getSecureString(request, "path");
		postService.delByPath(path);		
	}
	
	/**
	 * 跳转组织机构树
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
	 * 得到组织机构树
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({"getTreeDate" })
	@ResponseBody
	public void getTreeDate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {	
		String postId = request.getParameter("id");
		if(postId==null){
			postId="-1";
		}	
		List<SysOrg> postList = postService.getByParentId(postId);
		PrintWriter out = response.getWriter();
		JSONArray jsonarray = JSONArray.fromObject(postList);	
		out.println(jsonarray.toString());	
	}
	
	/**
	 * 人员选择器
	 * @param isSingle
	 * @param request
	 * @return
	 */
	@RequestMapping("orgDialog")
	public ModelAndView orgDialog(boolean isSingle,HttpServletRequest request,String roleId){
		ModelAndView mv = new ModelAndView("tree/orgTree.jsp");
		
		mv.addObject("isSingle", isSingle).addObject("roleId",roleId);
		return mv;
	}
	
	/**
	 * 设备选择器
	 * @param isSingle
	 * @param request
	 * @return
	 */
	@RequestMapping("orgDeviceDialog")
	public ModelAndView orgDeviceDialog(boolean isSingle,HttpServletRequest request,int status){
		ModelAndView mv = new ModelAndView("tree/orgDeviceTree.jsp");
	
		mv.addObject("isSingle", isSingle).addObject("status",status);
		return mv;
	}
	
}
