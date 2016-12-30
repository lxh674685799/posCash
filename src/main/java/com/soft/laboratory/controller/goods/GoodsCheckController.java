package com.soft.laboratory.controller.goods;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.soft.core.controller.GenericController;
import com.soft.core.syscontext.Const;
import com.soft.core.syscontext.SystemContext;
import com.soft.core.util.StringUtil;
import com.soft.laboratory.model.goods.Goods;
import com.soft.laboratory.model.user.SysMember;
import com.soft.laboratory.model.user.SysUser;
import com.soft.laboratory.service.goods.GoodsService;
import com.soft.laboratory.service.user.SysMemberService;
@Controller
@RequestMapping({ "/goods/check" })
public class GoodsCheckController extends GenericController {
	
	@Resource
	private GoodsService goodsService;
	
	@Resource
	private SysMemberService memberService;
	
	
	/**
	 * 结账列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "list" })
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response,Goods goods) throws Exception {
		SysUser loginUser =SystemContext.getCurrentUser(request);
		String id = loginUser.getId();
		if(!id.equals(Const.SYSTEM_ADMIN_ID)){
			goods.setUserId(loginUser.getId());
		}
		ModelAndView mv= getAutoView(request);
		return mv;		
	}
	
	@RequestMapping({ "list1" })
	public ModelAndView list1(HttpServletRequest request,
			HttpServletResponse response,Goods goods) throws Exception {
		SysUser loginUser =SystemContext.getCurrentUser(request);
		String id = loginUser.getId();
		if(!id.equals(Const.SYSTEM_ADMIN_ID)){
			goods.setUserId(loginUser.getId());
		}
		ModelAndView mv= getAutoView(request);
		return mv;		
	}
	
	@RequestMapping({ "list2" })
	public ModelAndView list2(HttpServletRequest request,
			HttpServletResponse response,Goods goods) throws Exception {
		SysUser loginUser =SystemContext.getCurrentUser(request);
		String id = loginUser.getId();
		if(!id.equals(Const.SYSTEM_ADMIN_ID)){
			goods.setUserId(loginUser.getId());
		}
		ModelAndView mv= getAutoView(request);
		return mv;		
	}
	
	@RequestMapping({ "getByCode" })
	public void getByCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String code = request.getParameter("code");
		if(StringUtil.isEmpty(code))
			return;
		Goods goods = goodsService.getByCode(code);
		if(null == goods){
			response.getWriter().print("");
			return;
		}
		Goods chechGoods = goodsToCheckGoods(goods);
		String json =JSONObject.fromObject(chechGoods).toString();
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");  
		response.getWriter().print(json);
	}

	private Goods goodsToCheckGoods(Goods goods) {
		Goods checkGoods = new Goods();
		checkGoods.setId(goods.getId());
		checkGoods.setCode(goods.getCode());
		checkGoods.setName(goods.getName());
		checkGoods.setTypeName(goods.getType().getName());
		checkGoods.setInventory(goods.getInventory());
		checkGoods.setInPrice(goods.getInPrice());
		checkGoods.setMoney(goods.getMoney());
		checkGoods.setCredit(goods.getCredit());
		checkGoods.setMoneyCre(goods.getMoneyCre());
		checkGoods.setCreditMon(goods.getCreditMon());
		checkGoods.setFactoryName(goods.getFactory().getName());
		checkGoods.setFactoryId(goods.getFactory().getId());
		checkGoods.setCreateTime(goods.getCreateTime());
		checkGoods.setUserId(goods.getUser().getId());
		checkGoods.setUserName(goods.getUser().getName());
		checkGoods.setVipCreditMon(goods.getVipCreditMon());
		return checkGoods;
	}
	
	
	/**
	 * 输入会员框
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "dialog" })
	public ModelAndView Dialog(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv= getAutoView(request);
		return mv;		
	}
	
	/**
	 * 查找会员
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping({ "getMember" })
	@ResponseBody
	public SysMember getMember(HttpServletRequest request,HttpServletResponse response)throws Exception {
		String phone = request.getParameter("memberPhone");
		SysMember sysMember = memberService.getByPhone(phone.trim());
		return sysMember;
	}
	
}
