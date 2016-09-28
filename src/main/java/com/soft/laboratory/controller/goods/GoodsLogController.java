package com.soft.laboratory.controller.goods;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.soft.core.controller.GenericController;
import com.soft.core.page.Page;
import com.soft.core.syscontext.SystemContext;
import com.soft.core.util.DateFormatUtil;
import com.soft.core.util.RequestUtil;
import com.soft.laboratory.model.goods.GoodsInfo;
import com.soft.laboratory.model.goods.GoodsLog;
import com.soft.laboratory.model.user.SysUser;
import com.soft.laboratory.service.goods.GoodsLogService;
@Controller
@RequestMapping({ "/goods/goodsLog" })
public class GoodsLogController extends GenericController {

	@Resource
	private GoodsLogService goodsLogService;
	
	
	/**
	 * 保存，更新商品
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "save" })
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String checkType = request.getParameter("checkType");
		String countMoney = request.getParameter("countMoney");
		String countCredit = request.getParameter("countCredit");
		String[] goodsInfo = RequestUtil.getStringAryByStr(request, "goodsInfo");
		
		SysUser loginUser =SystemContext.getCurrentUser(request);
		String userId = loginUser.getId();
		List<GoodsInfo> list = new ArrayList<GoodsInfo>();
		GoodsLog log = new GoodsLog();
		try{
			
			log.setCreateTime(DateFormatUtil.getNowByString(""));
			log.setCreateUserId(userId);
			log.setCheckType(checkType);
			log.setCountMoney(countMoney);
			log.setCountCredit(countCredit);
			for(String str : goodsInfo){
				GoodsInfo info = new GoodsInfo();
				String[] strs = str.split("#");
				info.setCode(strs[0]);
				info.setName(strs[1]);
				info.setMoney(strs[2]);
				info.setCredit(strs[3]);
				info.setNumber(strs[4]);
				info.setPayType(strs[5]);
				list.add(info);
			}
			String goodsInfoJson =JSONArray.fromObject(list).toString();
			log.setGoodsInfo(goodsInfoJson);
			goodsLogService.add(log);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 得到所有的商品日志
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping({ "list" })
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		GoodsLog log = new  GoodsLog();
		SysUser loginUser =SystemContext.getCurrentUser(request);
		String id = loginUser.getId();
		int page =1;
		int total = 0;
		if(request.getParameter("page")!=null){
			page = Integer.parseInt(request.getParameter("page"));
			total = Integer.parseInt(request.getParameter("totalCount"));
		}else{
			total = goodsLogService.getTotal(log); 
		}	

		Page pagination = new Page(page, total);
		String order ="order by d.id desc";
		List<GoodsLog> list = goodsLogService.listByPage(pagination,log,order);
		ModelAndView mv= getAutoView(request);
		mv.addObject("page",pagination).addObject("GoodsLog", list);
		return mv;		
	}
	
}
