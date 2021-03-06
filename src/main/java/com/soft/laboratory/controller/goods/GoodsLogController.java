package com.soft.laboratory.controller.goods;

import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.soft.core.controller.GenericController;
import com.soft.core.page.Page;
import com.soft.core.syscontext.SystemContext;
import com.soft.core.util.DateFormatUtil;
import com.soft.core.util.PrintUtil;
import com.soft.core.util.RequestUtil;
import com.soft.core.util.StringUtil;
import com.soft.laboratory.model.goods.Goods;
import com.soft.laboratory.model.goods.GoodsInfo;
import com.soft.laboratory.model.goods.GoodsLog;
import com.soft.laboratory.model.user.SysMember;
import com.soft.laboratory.model.user.SysUser;
import com.soft.laboratory.service.goods.GoodsInfoService;
import com.soft.laboratory.service.goods.GoodsLogService;
import com.soft.laboratory.service.goods.GoodsService;
import com.soft.laboratory.service.user.SysMemberService;

import net.sf.json.JSONArray;
@Controller
@RequestMapping({ "/goods/goodsLog" })
public class GoodsLogController extends GenericController {

	@Resource
	private GoodsLogService goodsLogService;
	
	@Resource
	private SysMemberService memberService;
	
	@Resource
	private GoodsInfoService goodsInfoService;
	
	@Resource
	private GoodsService goodsService;
	
	
	/**
	 * 保存，更新商品
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "save" })
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String checkType = request.getParameter("checkType");//结账方式
		String countMoney = request.getParameter("countMoney");//结账总金额
		String countCredit = request.getParameter("countCredit");//结账总积分
		String receiveMoney = request.getParameter("receiveMoney");//收入金额
		String changeMoney = request.getParameter("changeMoney");//找零金额
		String receiveCredit = request.getParameter("receiveCredit");//收入积分
		String memberNo = request.getParameter("memberNo");//会员号
		String isUserMember = request.getParameter("isUserMember");
		
		String[] goodsInfo = RequestUtil.getStringAryByStr(request, "goodsInfo");//商品信息
				
		SysUser loginUser =SystemContext.getCurrentUser(request);
		String userId = loginUser.getId();
		List<GoodsInfo> list = new ArrayList<GoodsInfo>();
		GoodsLog log = new GoodsLog();
		Double surplusCredit = 0D;//剩余积分
		try{
			if(StringUtil.isNotEmpty(memberNo)){
				SysMember sysMember =  memberService.getByNos(memberNo);
				Double memCredit = sysMember.getValueMnu();
				surplusCredit = memCredit;//先把会员积分设置为原有积分
				//如果是2 设置会员的剩余积分
				if("2".equals(isUserMember)){
					//如果收入积分不为空 判断 收入积分是否大于总积分 如果小于 就需要使用会员积分继续减去
					if(StringUtil.isNotEmpty(receiveCredit)  && !"0".equals(receiveCredit)){
						if((Double.valueOf(receiveCredit) -  Double.valueOf(countCredit)) < 0 ){
							surplusCredit = memCredit - Double.valueOf(countCredit) + Double.valueOf(receiveCredit);	
						}
					}else{//会员积分直接减去总积分
						surplusCredit = memCredit - Double.valueOf(countCredit);
					}
					sysMember.setValueMnu(surplusCredit);
					memberService.update(sysMember);
				}
			}
			
			log.setCreateTime(DateFormatUtil.getNowByString(""));
			log.setCreateUserId(userId);
			log.setCheckType(checkType);
			log.setCountMoney(countMoney);
			log.setCountCredit(countCredit);
			log.setReceiveMoney(receiveMoney);
			log.setReceiveCredit(receiveCredit);
			log.setChangeMoney(changeMoney);
			log.setMemberNo(memberNo);//
			log.setSurplusCredit(surplusCredit.toString());
			log.setLogType("1");
			int saleNum =0;
			
			for(String str : goodsInfo){
				GoodsInfo info = new GoodsInfo();
				String[] strs = str.split("&#&");
				info.setCode(strs[0]);
				info.setName(strs[1]);
				info.setMoney(strs[2]);
				info.setCredit(strs[3]);
				info.setNumber(strs[4]);
				info.setPayType(strs[5]);
				list.add(info);
				saleNum = saleNum + Integer.valueOf(strs[4]);
			}
			
			
			String goodsInfoJson =JSONArray.fromObject(list).toString();
			log.setGoodsInfo(goodsInfoJson);
			String goodLogId =goodsLogService.add(log);
			
			//保存记录的信息
			for(String str : goodsInfo){
				GoodsInfo info = new GoodsInfo();
				String[] strs = str.split("&#&");
				info.setCode(strs[0]);
				info.setName(strs[1]);
				info.setMoney(strs[2]);
				info.setCredit(strs[3]);
				info.setNumber(strs[4]);
				info.setPayType(checkType);
				info.setLogId(goodLogId);
				info.setCrateDate(DateFormatUtil.formatDate(new Date()));
				
				Goods goods =goodsService.getByCode(strs[0]);
				goods.setSum(goods.getSum()-Integer.valueOf(strs[4]));
				goodsService.update(goods);
				
				goodsInfoService.add(info);
				
			}
			//显示商品名称   商品数量  显示数量  
			
			// 执行打印
			PrintSale(log,list,loginUser,saleNum);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 商品退货
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "save1" })
	public void save1(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String checkType = request.getParameter("checkType");//结账方式
		String countMoney = request.getParameter("countMoney");//结账总金额
		String countCredit = request.getParameter("countCredit");//结账总积分
		String receiveMoney = request.getParameter("receiveMoney");//收入金额
		String changeMoney = request.getParameter("changeMoney");//找零金额
		String receiveCredit = request.getParameter("receiveCredit");//收入积分
		
		String[] goodsInfo = RequestUtil.getStringAryByStr(request, "goodsInfo");//商品信息
				
		SysUser loginUser =SystemContext.getCurrentUser(request);
		String userId = loginUser.getId();
		List<GoodsInfo> list = new ArrayList<GoodsInfo>();
		GoodsLog log = new GoodsLog();
		Double surplusCredit = 0D;//剩余积分
		
		try{		
			log.setCreateTime(DateFormatUtil.getNowByString(""));
			log.setCreateUserId(userId);
			log.setCheckType(checkType);
			log.setCountMoney(countMoney);
			log.setCountCredit(countCredit);
			log.setReceiveMoney(receiveMoney);
			log.setReceiveCredit(receiveCredit);
			log.setChangeMoney(changeMoney);
			log.setMemberNo(null);//
			log.setLogType("2");
			log.setSurplusCredit(surplusCredit.toString());
			int saleNum =0;
			
			for(String str : goodsInfo){
				GoodsInfo info = new GoodsInfo();
				String[] strs = str.split("&#&");
				info.setCode(strs[0]);
				info.setName(strs[1]);
				info.setMoney(strs[2]);
				info.setCredit(strs[3]);
				info.setNumber(strs[4]);
				info.setPayType(strs[5]);
				list.add(info);
				
				saleNum = saleNum + Integer.valueOf(strs[4]);
			}
			
			
			String goodsInfoJson =JSONArray.fromObject(list).toString();
			log.setGoodsInfo(goodsInfoJson);
			String goodLogId =goodsLogService.add(log);
			
			//保存记录的信息
			for(String str : goodsInfo){
				GoodsInfo info = new GoodsInfo();
				String[] strs = str.split("&#&");
				info.setCode(strs[0]);
				info.setName(strs[1]);
				info.setMoney(strs[2]);
				info.setCredit(strs[3]);
				info.setNumber(strs[4]);
				info.setPayType(checkType);
				info.setLogId(goodLogId);
				info.setCrateDate(DateFormatUtil.formatDate(new Date()));
				
				Goods goods =goodsService.getByCode(strs[0]);
				goods.setSum(goods.getSum()+Integer.valueOf(strs[4]));
				goodsService.update(goods);
				
				goodsInfoService.add(info);
				
			}
			//显示商品名称   商品数量  显示数量  
			
			// 执行打印
			PrintSale(log,list,loginUser,saleNum);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 打印小票
	 * @param order
	 * @param num
	 * @param sum
	 * @param practical
	 * @param change
	 */
	private void PrintSale(GoodsLog log,List<GoodsInfo> infos,SysUser loginUser,int saleNum) {  
        try {  
        	
            // 通俗理解就是书、文档  
            Book book = new Book();  
            // 设置成竖打  
            PageFormat pf = new PageFormat();
            pf.setOrientation(PageFormat.PORTRAIT);  
  
            // 通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符。  
            Paper paper = new Paper();  
            paper.setSize(160, 30000);// 纸张大小  
            paper.setImageableArea(0, 0, 160, 30000);// A4(595 X  
                                                        // 842)设置打印区域，其实0，0应该是72，72，因为A4纸的默认X,Y边距是72  
            pf.setPaper(paper);  
  
            book.append(new PrintUtil(infos, loginUser.getAccount(),log,saleNum), pf);  
  
            // 获取打印服务对象  
            PrinterJob job = PrinterJob.getPrinterJob();  
            // 设置打印类  
            job.setPageable(book);  
  
            job.print();  
        } catch (PrinterException e) {  
            e.printStackTrace();  
        }  
    }  
	
	
	/**
	 * 得到所有的商品日志
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "list" })
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		GoodsLog log = new  GoodsLog();

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
	
	/**
	 *统计商品的销售量  怎么展示   商品的名称 
	 */
	@RequestMapping({ "statis" })
	public ModelAndView statis(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String goodName = request.getParameter("goodName");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String payType = request.getParameter("payType");


		if(goodName != null){
			if(goodName.equals("")){
				goodName=null;
			}
		}
		if(startTime != null){
			if(startTime.equals("")){
				startTime=null;
			}
		}

		if(endTime != null){
			if(endTime.equals("")){
				endTime=null;
			}
		}
		
		if(payType != null){
			if(payType.equals("")||payType.equals("0")){
				payType=null;
			}
		}
		
		int page =1;
		int total = 0;
		if(request.getParameter("page")!=null){
			page = Integer.parseInt(request.getParameter("page"));
			total = Integer.parseInt(request.getParameter("totalCount"));
		}else{
			total = goodsInfoService.getTotalDiv(goodName,startTime,endTime,payType); 
		}	

		Page pagination = new Page(page, total);
		List list = goodsInfoService.listByPageByDiv(pagination,goodName,startTime,endTime,payType);
		
		ModelAndView mv= getAutoView(request);
		mv.addObject("page",pagination).addObject("GoodsInfo", list).addObject("endTime", endTime).addObject("startTime", startTime).addObject("goodName", goodName).addObject("payType", payType);
		return mv;		
	}
}
