package com.soft.laboratory.controller.message;

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
import com.soft.laboratory.model.message.Notice;
import com.soft.laboratory.model.user.SysUser;
import com.soft.laboratory.service.message.NoticeService;

/**
 * 系统通知公告控制类
 * @author 刘旭辉
 */
@Controller
@RequestMapping({ "/message/notice" })
public class NoticeController extends GenericController{
	
	@Resource
	private NoticeService noticeService;
	
	/**
	 * 添加，修改通知公告
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "edit" })
	public ModelAndView edit(HttpServletRequest request) throws Exception{		
		ModelAndView mv = getAutoView(request);
		String id = request.getParameter("id");
		String returnUrl = RequestUtil.getPrePage(request);
		Notice notice = null;
		if (id != null) {
			notice= noticeService.getById(id);
		}else{
			notice= new Notice();
		}
		return mv.addObject("returnUrl", returnUrl)
				.addObject("notice", notice);
	}
	
	/**
	 * 保存，更新通知公告
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "save" })
	public void save(HttpServletRequest request, HttpServletResponse response,
			Notice notice) throws Exception {
		String preUrl = request.getParameter("returnUrl");
		ResultMessage message = null;
		if(notice.getId().equals("")){
			SysUser loginUser = SystemContext.getCurrentUser(request);
			notice.setCreateUserId(loginUser.getId());
			notice.setCreateDate(DateFormatUtil.getNowByString(""));
			notice.setCreateUserName(loginUser.getName());
			noticeService.add(notice);
			message = new ResultMessage(Const.MESSAGE_SUCCESS, "发送通知公告成功！");
		}else{	
			noticeService.update(notice);
			message = new ResultMessage(Const.MESSAGE_SUCCESS, "修改通知公告成功！");
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 得到用户通知公告列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "list" })
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response,Notice notice) throws Exception {
		int page =1;
		int total = 0;
		if(request.getParameter("page")!=null){
			page = Integer.parseInt(request.getParameter("page"));
			total = Integer.parseInt(request.getParameter("totalCount"));
		}else{
			total = noticeService.getTotal(notice);	   
		}
				
		Page pagination = new Page(page, total);	
		String order = "order by d.createDate desc";
		List<Notice> notices = noticeService.listByPage(pagination,notice,order);	
		
		ModelAndView mv= getAutoView(request);
		mv.addObject("page",pagination)
			.addObject("notices", notices);
		return mv;	
	}
	
	/**
	 * 取得通知公告
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mv = getAutoView(request);
		String preUrl = RequestUtil.getPrePage(request);
		String id=RequestUtil.getSecureString(request, "id");
		Notice notice = noticeService.getById(id);		
		return mv.addObject("notice", notice).addObject("returnUrl", preUrl);
	}
		
	/**
	 * 删除通知公告
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "del" })
	public void del(HttpServletRequest request, HttpServletResponse response) throws IOException{		
		String[] ids = request.getParameterValues("id");
		String preUrl = RequestUtil.getPrePage(request);
		noticeService.delByIds(ids);
		ResultMessage message = new ResultMessage(Const.MESSAGE_SUCCESS, "删除成功！");
		addMessage(message, request);
		response.sendRedirect(preUrl);	
	}
	
}
