package com.soft.laboratory.controller.system;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soft.core.controller.GenericController;
import com.soft.core.syscontext.SystemContext;
import com.soft.laboratory.model.message.InnerMessage;
import com.soft.laboratory.model.system.SystemTip;
import com.soft.laboratory.model.system.TipContent;
import com.soft.laboratory.model.user.SysUser;
import com.soft.laboratory.service.message.MessageService;

/**
 * 此类为 页面首页通知信息控制类:类描述
 * 
 */
@Controller
@RequestMapping({"/system/systemTip"})
public class SystemTipController extends GenericController{
	
	@Resource
	private MessageService messageService;
	
	@RequestMapping({"message"})
	@ResponseBody
	public SystemTip message(HttpSession session){
		SystemTip tip = new SystemTip();
		SysUser loginUser = SystemContext.getCurrentUser(session);
		List<InnerMessage> list = this.messageService.getNotReadMsg(loginUser.getId());
		
		tip.setCount(list.size());
		List<TipContent> tipContentList = new ArrayList<TipContent>();
		tip.setList(tipContentList);
		//未读消息:查询未读信息(前十条)
		int length = 10;
		if(list.size() < length){
			length = list.size();
		}
		for(int i=0;i<length;i++){
			TipContent con= new TipContent();
			con.addId(list.get(i).getId()).addTitle(list.get(i).getSubject()+"<----"+list.get(i).getFromUser().getName());
			tipContentList.add(con);
		}
		return tip;
	}

}