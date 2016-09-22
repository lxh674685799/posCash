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
import com.soft.laboratory.model.user.SysMember;
import com.soft.laboratory.service.user.SysMemberService;

/**
 * 系统用户控制类
 * @author 翟瑞东
 */
@Controller
@RequestMapping({ "/user/member" })
public class SysMemberController extends GenericController{
	
	@Resource
	private SysMemberService memberService;

	
	@RequestMapping({ "edit" })
	public ModelAndView edit(HttpServletRequest request) throws Exception{
		ModelAndView mv = getAutoView(request);
		String returnUrl = RequestUtil.getPrePage(request);
		String userId = request.getParameter("id");
		SysMember member = null;
		if (userId != null) {
			member= memberService.getById(userId);
		}else{
			member= new SysMember();
		}	
		return mv.addObject("returnUrl", returnUrl)
				.addObject("member", member);
	}
	
	/**
	 * 保存，更新用户
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "save" })
	public void save(HttpServletRequest request, HttpServletResponse response,
			SysMember member) throws Exception {
		String preUrl = request.getParameter("returnUrl");
		ResultMessage message = null;
		
		if(member.getId().equals("")){
			List<SysMember> exit = memberService.isNameExistAdd(member.getPhone(), "phone");
			if(exit.size()>0){
				message = new ResultMessage(Const.MESSAGE_ERROR, "添加失败，该手机号已存在！");
			}else{
				memberService.add(member);
				message = new ResultMessage(Const.MESSAGE_SUCCESS, "添加会员成功！");
			}
		}else{	
			List<SysMember> exit = memberService.isNameExistUpdate(member.getPhone(), "phone",member.getId());
			if(exit.size()>0){
				message = new ResultMessage(Const.MESSAGE_ERROR, "修改失败，该手机号已存在！");
			}else{
				memberService.update(member);
				message = new ResultMessage(Const.MESSAGE_SUCCESS, "修改会员成功！");
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
			HttpServletResponse response,SysMember member) throws Exception {
		int page =1;
		int total = 0;
		if(request.getParameter("page")!=null){
			page = Integer.parseInt(request.getParameter("page"));
			total = Integer.parseInt(request.getParameter("totalCount"));
		}else{
		    total = memberService.getTotal(member);
		}
		
		Page pagination = new Page(page, total);
		String order ="order by d.id";
		List<SysMember> members = memberService.listByPage(pagination,member,order);
	
		ModelAndView mv= getAutoView(request);
		mv.addObject("page",pagination).addObject("member", member)
			.addObject("members", members);
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
		SysMember member = memberService.getById(userId);
		mv.addObject("member", member)
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
		memberService.delByIds(ids);
		ResultMessage message = new ResultMessage(Const.MESSAGE_SUCCESS, "删除成功！");
		addMessage(message, request);
		response.sendRedirect(preUrl);	
	}

}
