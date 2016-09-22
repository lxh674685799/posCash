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
import com.soft.core.util.StringUtil;
import com.soft.core.web.ResultMessage;
import com.soft.laboratory.model.MyFile;
import com.soft.laboratory.model.device.DeviceBuy;
import com.soft.laboratory.model.device.DeviceEntity;
import com.soft.laboratory.model.device.DeviceRepair;
import com.soft.laboratory.model.message.InnerMessage;
import com.soft.laboratory.model.user.SysUser;
import com.soft.laboratory.service.FileService;
import com.soft.laboratory.service.device.DeviceBuyService;
import com.soft.laboratory.service.device.DeviceEntityService;
import com.soft.laboratory.service.device.DeviceRepairService;
import com.soft.laboratory.service.message.MessageService;
import com.soft.laboratory.service.user.SysUserService;

/**
 * 系统消息控制类
 * @author 刘旭辉
 */
@Controller
@RequestMapping({ "/message/message" })
public class MessageController extends GenericController{
	
	@Resource
	private MessageService messageService;
	
	@Resource
	private SysUserService userService;
	
	@Resource
	private DeviceBuyService buyService;
	
	@Resource
	private DeviceRepairService repairService;
	
	@Resource
	private FileService fileService;
	
	@Resource
	private DeviceEntityService entityService;

	/**
	 * 添加，修改消息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "edit" })
	public ModelAndView edit(HttpServletRequest request) throws Exception{		
		ModelAndView mv = getAutoView(request);
		String id = request.getParameter("id");
		String returnUrl = RequestUtil.getPrePage(request);
		InnerMessage innerMessage = null;
		if (id != null) {
			innerMessage= messageService.getById(id);
		}else{
			innerMessage= new InnerMessage();
		}
		return mv.addObject("returnUrl", returnUrl)
				.addObject("innerMessage", innerMessage);
	}
	
	/**
	 * 保存，更新消息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "save" })
	public void save(HttpServletRequest request, HttpServletResponse response,
			InnerMessage innerMessage) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		if(innerMessage.getId().equals("")){
			String[] toUserIds = RequestUtil.getStringAryByStr(request, "toUserIds");
			for(String toUserId :toUserIds){
				innerMessage.setToUserId(toUserId);
				innerMessage.setSendDate(DateFormatUtil.getNowByString(""));
				innerMessage.setReadStatus(Const.MESSAGE_UNREAD);
				innerMessage.setFromUserId(SystemContext.getCurrentUser(request).getId());
				innerMessage.setFromUserName(SystemContext.getCurrentUser(request).getName());
				messageService.add(innerMessage);
			}
			message = new ResultMessage(Const.MESSAGE_SUCCESS, "发送消息成功！");
		}else{	
			messageService.update(innerMessage);
			message = new ResultMessage(Const.MESSAGE_SUCCESS, "修改消息成功！");
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 得到用户消息列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping({ "list" })
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response,InnerMessage innerMessage) throws Exception {
		
		SysUser user = SystemContext.getCurrentUser(request);
		
//		if(!user.getId().equals(Const.SYSTEM_ADMIN_ID)){
			innerMessage.setToUserId(user.getId());
//		}
		String order = ""; 
		int messageType = RequestUtil.getInt(request, "type");
		if(messageType==Const.USER_MESSAGE_INFO){
			innerMessage.setType(messageType);
		}else{
			order =" and d.type !="+Const.USER_MESSAGE_INFO;
		}

		int page =1;
		int total = 0;
		if(request.getParameter("page")!=null){
			page = Integer.parseInt(request.getParameter("page"));
			total = Integer.parseInt(request.getParameter("totalCount"));
		}else{
			total = messageService.getTotal(innerMessage);	   
		}
		
		Page pagination = new Page(page, total);	
		order = order +" order by d.readStatus  asc ,d.sendDate asc";
		List<InnerMessage> innerMessages = messageService.listByPage(pagination,innerMessage,order);	
		List<SysUser> users = (List<SysUser>) userService.listAll(new SysUser());
		
		ModelAndView mv= getAutoView(request);
		mv.addObject("page",pagination)
			.addObject("users",users)
			.addObject("innerMessage",innerMessage)
			.addObject("innerMessages", innerMessages);
		return mv;	
	}

	/**
	 * 得到具体的消息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping({ "get" })
	public ModelAndView get(HttpServletRequest request) throws Exception {
		String returnUrl = RequestUtil.getPrePage(request);	
		ModelAndView mv= getAutoView(request);
		String id = request.getParameter("id");
		InnerMessage innerMessage = messageService.getById(id);
		if(innerMessage.getReadStatus() == Const.MESSAGE_UNREAD){
			innerMessage.setReadStatus(Const.MESSAGE_READ);
			innerMessage.setReadDate(DateFormatUtil.getNowByString(""));
			messageService.update(innerMessage);	
		}
		int type = innerMessage.getType();
		
		if(type == Const.USER_MESSAGE_BUY){
			DeviceBuy buy = buyService.getById(innerMessage.getEventId());
			MyFile file = new MyFile();
			file.setInfoId(buy.getId());
			List<MyFile> downFile = (List<MyFile>) fileService.listAll(file);
			if(downFile.size()>0){
				mv.addObject("file",downFile.get(0));
			}
			buy.setTotalPrice(StringUtil.hangeToBig(buy.getNumber()*buy.getPrice()));
			mv.addObject("buy",buy);
		}
		
		if(type == Const.USER_MESSAGE_REPAIR){
			DeviceRepair repair = repairService.getById(innerMessage.getEventId());
			
			List<DeviceEntity> entitys = (List<DeviceEntity>) entityService.getDevieByIds(repair.getDeviceIds());
			
			mv.addObject("repair",repair).addObject("entitys",entitys);
		}
		mv.addObject("returnUrl", returnUrl)
			.addObject("innerMessage",innerMessage);
		return mv;
	}
		
	/**
	 * 删除消息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "del" })
	public void del(HttpServletRequest request, HttpServletResponse response) throws IOException{		
		String[] ids = request.getParameterValues("id");
		String preUrl = RequestUtil.getPrePage(request);
		messageService.delByIds(ids);
		ResultMessage message = new ResultMessage(Const.MESSAGE_SUCCESS, "删除成功！");
		addMessage(message, request);
		response.sendRedirect(preUrl);	

	}
	
	/**
	 * 同意
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "agree" })
	public void agree(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = request.getParameter("returnUrl");
		ResultMessage message = null;
		String msgId = request.getParameter("innerMessageId");
		InnerMessage msg = messageService.getById(msgId);
		
		messageService.agree(msg);
	
		message = new ResultMessage(Const.MESSAGE_SUCCESS, "办理成功！");
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 不同意
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "unagree" })
	public void unagree(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = request.getParameter("returnUrl");
		ResultMessage message = null;
		String msgId = request.getParameter("innerMessageId");
		InnerMessage msg = messageService.getById(msgId);	
		messageService.unagree(msg);
		message = new ResultMessage(Const.MESSAGE_SUCCESS, "办理成功！");
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 购买任务
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "buy" })
	public void buy(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = request.getParameter("returnUrl");
		ResultMessage message = null;
		String msgId = request.getParameter("innerMessageId");
		InnerMessage msg = messageService.getById(msgId);	
		messageService.buy(msg);
		message = new ResultMessage(Const.MESSAGE_SUCCESS, "办理成功！");
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 消息详情
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("show")
	public ModelAndView show(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mv = getAutoView(request);
		String preUrl = RequestUtil.getPrePage(request);
		String id=RequestUtil.getSecureString(request, "id");
		InnerMessage message = messageService.getById(id);		
		return mv.addObject("message", message).addObject("returnUrl", preUrl);
	}
	
	/**
	 * 取得通知公告
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("rebark")
	public ModelAndView rebark(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mv = getAutoView(request);
		String preUrl = RequestUtil.getPrePage(request);
		String id=RequestUtil.getSecureString(request, "id");
		InnerMessage message = messageService.getById(id);		
		return mv.addObject("message", message).addObject("returnUrl", preUrl);
	}
}
