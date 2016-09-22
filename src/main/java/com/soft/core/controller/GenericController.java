package com.soft.core.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.servlet.ModelAndView;

import com.soft.core.util.RequestUtil;
import com.soft.core.util.StringUtil;
import com.soft.core.web.ResultMessage;

public class GenericController {
	public final String SUCCESS = "{success:true}";
	public final String FAILURE = "{success:false}";
	private MessageSourceAccessor messages;
	public static final String STEP1 = "1";
	public static final String STEP2 = "2";
	public static final String MESSAGES_KEY = "successMessages";
	public static final String Message = "message";

	public void addMessage(ResultMessage message, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("message", message);
	}
	
	public ModelAndView getAutoView(HttpServletRequest request) throws Exception {
		
		String requestURI = request.getRequestURI();

		String contextPath = request.getContextPath();

		requestURI = requestURI.replace(".do", "");
		int cxtIndex = requestURI.indexOf(contextPath);
		
		if (cxtIndex != -1) {
			requestURI = requestURI.substring(cxtIndex + contextPath.length());
		}

		String[] paths = requestURI.split("[/]");

		if ((paths != null) && (paths.length == 5)) {
			String jspPath = "/" + paths[1] + "/" + paths[2] + "/" + paths[3]
					+ StringUtil.makeFirstLetterUpperCase(paths[4]) + ".jsp";
			return new ModelAndView(jspPath);
		}
		if ((paths != null) && (paths.length == 4)) {
			String jspPath = "/" + paths[1] + "/" + paths[2]
					+ StringUtil.makeFirstLetterUpperCase(paths[3]) + ".jsp";
			return new ModelAndView(jspPath);
		}

		throw new Exception("url:[" + requestURI
				+ "] is not in this pattern:[/子系统/包名/表对应实体名/实体操作方法名.do]");
	}

	@Autowired
	public void setMessages(MessageSource messageSource) {
		this.messages = new MessageSourceAccessor(messageSource);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveError(HttpServletRequest request, String error) {
		List errors = (List) request.getSession().getAttribute("errors");
		if (errors == null) {
			errors = new ArrayList();
		}
		errors.add(error);
		request.getSession().setAttribute("errors", errors);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveMessage(HttpServletRequest request, String msg) {
		List messages = (List) request.getSession().getAttribute(
				"successMessages");

		if (messages == null) {
			messages = new ArrayList();
		}

		messages.add(msg);
		request.getSession().setAttribute("successMessages", messages);
	}

	public String getText(String msgKey, Locale locale) {
		return this.messages.getMessage(msgKey, locale);
	}

	public String getText(String msgKey, String arg, Locale locale) {
		return getText(msgKey, new Object[] { arg }, locale);
	}

	public String getText(String msgKey, Object[] args, Locale locale) {
		return this.messages.getMessage(msgKey, args, locale);
	}

	public String getText(String msgKey, Object[] args) {
		return this.messages.getMessage(msgKey, args, new Locale("zh_CN"));
	}

	public String getText(String msgKey) {
		return this.messages.getMessage(msgKey, new Locale("zh_CN"));
	}

	protected String getText(String msgKey, String arg,
			HttpServletRequest request) {
		Locale locale = RequestUtil.getLocal(request);
		return getText(msgKey, arg, locale);
	}

	protected String getText(String msgKey, Object[] args,
			HttpServletRequest request) {
		Locale locale = RequestUtil.getLocal(request);
		return getText(msgKey, args, locale);
	}

	protected void writeResultMessage(PrintWriter writer, String resultMsg,
			int successFail) {
		ResultMessage resultObj = new ResultMessage(successFail, resultMsg);
		writer.print(resultObj);
	}

	protected void writeResultMessage(PrintWriter writer,
			ResultMessage resultMessage) {
		writer.print(resultMessage);
	}

	protected void saveResultMessage(HttpSession session, String msg,
			int successFail) {
		ResultMessage resultMsg = new ResultMessage(successFail, msg);
		session.setAttribute("message", resultMsg);
	}

	protected void saveSuccessResultMessage(HttpSession session, String msg) {
		saveResultMessage(session, msg, 1);
	}

	protected void saveFailResultMessage(HttpSession session, String msg) {
		saveResultMessage(session, msg, 0);
	}
}
