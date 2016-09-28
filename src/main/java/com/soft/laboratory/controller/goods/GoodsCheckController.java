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
import com.soft.laboratory.model.goods.Goods;
import com.soft.laboratory.model.user.SysUser;
import com.soft.laboratory.service.goods.GoodsLogService;
import com.soft.laboratory.service.goods.GoodsService;
@Controller
@RequestMapping({ "/goods/check" })
public class GoodsCheckController extends GenericController {
	
	@Resource
	private GoodsService goodsService;
	
	
	/**
	 * 结账列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
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
	
	@SuppressWarnings("unchecked")
	@RequestMapping({ "getByCode" })
	@ResponseBody
	public void getByCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String code = request.getParameter("code");
		Goods goods = goodsService.getByCode(code);
		if(null == goods){
			response.getWriter().print("");
			return;
		}
		Goods chechGoods = goodsToCheckGoods(goods);
			/*JsonConfig jsonConfig = new JsonConfig();

			jsonConfig.setExcludes(new String[]{"type","factory","user"});//除去dept属性

			JSONObject json =JSONObject.fromObject(goods, jsonConfig);

			s = json.toString();
			System.out.println(s);*/
		//return chechGoods;
		String json =JSONObject.fromObject(chechGoods).toString();
		System.out.println(json);
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
		return checkGoods;
	}
	
}
