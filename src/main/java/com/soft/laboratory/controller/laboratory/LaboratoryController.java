package com.soft.laboratory.controller.laboratory;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.soft.core.controller.GenericController;
import com.soft.core.page.Page;
import com.soft.core.syscontext.Const;
import com.soft.core.syscontext.SystemContext;
import com.soft.core.util.RequestUtil;
import com.soft.core.web.ResultMessage;
import com.soft.laboratory.model.laboratory.Laboratory;
import com.soft.laboratory.service.laboratory.LaboratoryService;
import com.soft.laboratory.service.user.SysUserService;

/**
 * 实验室控制类
 * @author 刘旭辉
 */
@Controller
@RequestMapping({ "/laboratory/laboratory" })
public class LaboratoryController extends GenericController{
	
	@Resource
	private LaboratoryService laboratoryService;
	
	@Resource
	private SysUserService userService;

	
	private static final Logger log = LogManager.getLogger(LaboratoryController.class);
	
	/**
	 * 添加，修改实验室
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "edit" })
	public ModelAndView edit(HttpServletRequest request) throws Exception{
		ModelAndView mv = getAutoView(request);
		String id = request.getParameter("id");
		String returnUrl = RequestUtil.getPrePage(request);
		Laboratory laboratory = null;
		if (id != null) {
			laboratory= laboratoryService.getById(id);
		}else{
			laboratory= new Laboratory();
		}
		return mv.addObject("returnUrl", returnUrl)
				.addObject("laboratory", laboratory);	
	}
	
	/**
	 * 保存，更新实验室
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "save" })
	public void save(HttpServletRequest request, HttpServletResponse response,
			Laboratory laboratory) throws Exception {
		String preUrl = request.getParameter("returnUrl");
		
		laboratory.setPrincipalId(userService.getByName(laboratory.getPrincipalName()).getId());
		
		ResultMessage message = null;
		
		if(laboratory.getId().equals("")){
			List<Laboratory> exit = laboratoryService.isNameExistAdd(laboratory.getName(), "name");
			if(exit.size()>0){
				message = new ResultMessage(Const.MESSAGE_ERROR, "添加失败，添加实验室已存在！");
			}else{
				laboratory.setStatus(Const.LABORATORY_NORMAL);
				laboratoryService.add(laboratory);
				message = new ResultMessage(Const.MESSAGE_SUCCESS, "添加实验室信息成功！");
			}		
		}else{	
			List<Laboratory> exit = laboratoryService.isNameExistUpdate(laboratory.getName(), "name", laboratory.getId());
			if(exit.size()>0){
				message = new ResultMessage(Const.MESSAGE_ERROR, "修改失败，实验室已存在！");
			}else{
				laboratoryService.update(laboratory);
				message = new ResultMessage(Const.MESSAGE_SUCCESS, "修改实验室信息成功！");
			}
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);	
	}
	
	/**
	 * 转到实验室列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "list" })
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response,Laboratory laboratory) throws Exception {
		int page =1;
		int total = 0;
		if(request.getParameter("page")!=null){
			page = Integer.parseInt(request.getParameter("page"));
			total = Integer.parseInt(request.getParameter("totalCount"));
		}else{
			total = laboratoryService.getTotal(laboratory);  
		}

		String postId = SystemContext.getCurrentUser(request).getPostId();
		if(postId.equals(Const.SYSTEM_ADMIN_ID)){
		laboratory.setPostId(SystemContext.getCurrentUser(request).getPostId());
		}
		String postName = laboratory.getPostName();
		laboratory.setPostName(null);

		Page pagination = new Page(page, total);
		String order = "order by d.bulidTime";
		List<Laboratory> laboratorys = laboratoryService.listByPage(pagination,laboratory,order);
	
		laboratory.setPostName(postName);
		ModelAndView mv= getAutoView(request);
		mv.addObject("page",pagination)
			.addObject("laboratorys", laboratorys)
			.addObject("laboratory",laboratory);
		return mv;		
	}
	
	/**
	 * 得到具体的实验室信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "rebark" })
	public ModelAndView rebark(HttpServletRequest request) throws Exception {
		return null;		
	}
	
	/**
	 * 得到具体的实验室信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "loan" })
	public void loan(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String preUrl = RequestUtil.getPrePage(request);
		Laboratory laboratory = laboratoryService.getById(id);
		laboratory.setStatus(Const.LABORATORY_LOAN);
		laboratoryService.update(laboratory);
		ResultMessage message = new ResultMessage(1, "借出成功！");
		addMessage(message, request);
		response.sendRedirect(preUrl);			
	}
	
	/**
	 * 得到具体的实验室信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "get" })
	public void get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String preUrl = RequestUtil.getPrePage(request);
		Laboratory laboratory = laboratoryService.getById(id);
		laboratory.setStatus(Const.LABORATORY_NORMAL);
		laboratoryService.update(laboratory);
		ResultMessage message = new ResultMessage(1, "归还成功！");
		addMessage(message, request);
		response.sendRedirect(preUrl);			
	}
		
	/**
	 * 删除实验室
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "del" })
	public void del(HttpServletRequest request, HttpServletResponse response) throws IOException{		
		String[] ids = request.getParameterValues("id");
		String preUrl = RequestUtil.getPrePage(request);
		laboratoryService.delByIds(ids);
		ResultMessage message = new ResultMessage(1, "删除成功！");
		addMessage(message, request);
		response.sendRedirect(preUrl);	
	}

}
