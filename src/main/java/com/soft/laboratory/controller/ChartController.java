package com.soft.laboratory.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.soft.core.controller.GenericController;
import com.soft.core.util.DateFormatUtil;
import com.soft.laboratory.service.ChartService;

import net.sf.json.JSONArray;

/**
 * 图表展示
 * 
 * @author 刘旭辉
 * 
 */
@Controller
@RequestMapping({ "/charts/chart" })
public class ChartController extends GenericController {

	@Resource
	private ChartService chartService;
	
	
	// 跳转统计分析页面
	
	@RequestMapping({ "month" })
	public ModelAndView month(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv= getAutoView(request);
		return mv;	
	}
	
	@RequestMapping({ "emp" })
	public ModelAndView emp(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv= getAutoView(request);
		
		//获取当前时间
		String yearMonth = DateFormatUtil.format(new Date(), "yyyy-MM");
			
		return mv.addObject("yearMonth", yearMonth);	
	}

	/**
	 * 销售情况
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "statisticByMonth" })
	public void statisticByMonth(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String year = request.getParameter("year");
		
		List<Double> totalMoney = new ArrayList<Double>();
		List<Double> totalCredit = new ArrayList<Double>();
		
		Map<String, List<Double>> map = new HashMap<String, List<Double>>();
		
		for(int i =1; i<=12; i++){
			String monthStr = "";
			if(i <10){
				monthStr = "0"+i;
			}else{
				monthStr = i+"";
			}
			Map<String,String> queryMap = chartService.queryByMonth(year+"-"+monthStr);
			totalMoney.add(Double.valueOf(queryMap.get("totalMoney")));
			totalCredit.add(Double.valueOf(queryMap.get("totalCredit")));
		}
		
		map.put("totalMoney", totalMoney);
		map.put("totalCredit", totalCredit);
		PrintWriter out = response.getWriter();

		JSONArray jsonarray = JSONArray.fromObject(map);
		out.println(jsonarray.toString());
	}
	
	/**
	 * 销售情况
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "statisticByEmp" })
	@ResponseBody
	public Map<String, List> statisticByEmp(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String yearMonth = request.getParameter("yearMonth");
		List<Double> totalMoney = new ArrayList<Double>();
		List<Double> totalCredit = new ArrayList<Double>();
		List<String> userNames = new ArrayList<String>();
		
		Map<String, List> map = new HashMap<String, List>();
		
		List list = chartService.queryByEmp(yearMonth);
		
		for(Object object : list){
			Map queryMap = (Map)object;
			totalMoney.add((Double) queryMap.get("totalMoney"));
			totalCredit.add((Double) queryMap.get("totalCredit"));
			userNames.add((String) queryMap.get("userName"));
		}
		
		map.put("totalMoney", totalMoney);
		map.put("totalCredit", totalCredit);
		map.put("userNames", userNames);
	
		return map;
	}
	
}
