package com.soft.laboratory.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.soft.core.controller.GenericController;
import com.soft.core.page.Page;
import com.soft.core.syscontext.Const;
import com.soft.core.syscontext.SystemContext;
import com.soft.laboratory.model.message.InnerMessage;
import com.soft.laboratory.model.message.Notice;
import com.soft.laboratory.service.message.MessageService;
import com.soft.laboratory.service.message.NoticeService;

/**
 * 首页展示类
 * 
 * @author 刘旭辉
 * 
 */
@Controller
@RequestMapping({ "/home/portal" })
public class HomePortalController extends GenericController {

	@Resource
	private NoticeService noticeService;

	@Resource
	private MessageService messageService;


	// 通知公告　信息
	@RequestMapping({ "noticeList" })
	public ModelAndView noice(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("portal/notice.jsp");
		int total = noticeService.getTotal(new Notice());
		Page pagination = new Page(1, total);
		pagination.setPageSize(10);
		String order = "order by d.createDate desc";
		List<Notice> noticeList = noticeService.listByPage(pagination,
				new Notice(), order);
		view.addObject("noticeList", noticeList);
		return view;
	}

	// 系统内部消息　信息
	@RequestMapping({ "messageList" })
	public ModelAndView messageList(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("portal/message.jsp");
		 InnerMessage message = new InnerMessage();
		 message.setType(Const.USER_MESSAGE_INFO);
		 message.setReadStatus(Const.MESSAGE_UNREAD);
		 message.setToUserId(SystemContext.getCurrentUser(request).getId());
		int total = messageService.getTotal(message);
		Page pagination = new Page(1, total);
		pagination.setPageSize(10);
		String order = "order by d.sendDate";
		List<InnerMessage> messageList = messageService.listByPage(pagination,
				message, order);
		view.addObject("messageList", messageList);
		return view;
	}

	// 发布任务　信息
	@RequestMapping({ "taskList" })
	public ModelAndView taskList(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("portal/task.jsp");
		 InnerMessage message = new InnerMessage();
		 message.setReadStatus(Const.MESSAGE_UNREAD);
		 message.setToUserId(SystemContext.getCurrentUser(request).getId());
		int total = messageService.getTotal(message);
		Page pagination = new Page(1, total);
		pagination.setPageSize(10);
		String order = " and d.type !="+Const.USER_MESSAGE_INFO + "order by d.sendDate";
		List<InnerMessage> messageList = messageService.listByPage(pagination,
				message, order);
		view.addObject("messageList", messageList);
		return view;
	}
}
