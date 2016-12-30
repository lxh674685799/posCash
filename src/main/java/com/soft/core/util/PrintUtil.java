package com.soft.core.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.Calendar;
import java.util.List;

import com.soft.laboratory.model.goods.GoodsInfo;
import com.soft.laboratory.model.goods.GoodsLog;

public class PrintUtil implements Printable {
	
    private List<GoodsInfo> list;  
    private String cashier;  
    private Font font;  
    private String sale_num;  
    private String sale_sum;  
    private String practical;  
    private String changes;  
    private String orders;
    //总点卷
    private String sale_credit;
    //实收点卷
    private String total_credit; 
    
    private GoodsLog goodslog;

    
    // 构造函数  
    public PrintUtil(List<GoodsInfo> infos, String cashier,GoodsLog log,int saleNum) {  
        this.list = infos;  
        // 收银员编号  
        this.cashier = cashier;  
        // 订单标号  
        // 商品总数  
        this.sale_num = saleNum+"";  
        // 总金额  
        this.sale_sum = log.getCountMoney(); 
        // 总金额  卷
        this.total_credit = log.getCountCredit();
        // 
        this.sale_credit = log.getReceiveCredit();
        // 实收  
        this.practical = log.getReceiveMoney();  
        // 找零  
        this.changes = log.getChangeMoney();  
        
        this.goodslog = log;
    } 
    
	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		
	        // 转换成Graphics2D 拿到画笔  
	        Graphics2D g2 = (Graphics2D) graphics;  
	        // 设置打印颜色为黑色  
	        g2.setColor(Color.black);  
	  
	        // 打印起点坐标  
	        double x = pageFormat.getImageableX();  
	        double y = pageFormat.getImageableY();  
	  
	        // 虚线  
	        float[] dash1 = { 4.0f };  
	        // width - 此 BasicStroke 的宽度。此宽度必须大于或等于 0.0f。如果将宽度设置为  
	        // 0.0f，则将笔划呈现为可用于目标设备和抗锯齿提示设置的最细线条。  
	        // cap - BasicStroke 端点的装饰  
	        // join - 应用在路径线段交汇处的装饰  
	        // miterlimit - 斜接处的剪裁限制。miterlimit 必须大于或等于 1.0f。  
	        // dash - 表示虚线模式的数组  
	        // dash_phase - 开始虚线模式的偏移量  
	  
	        // 设置画虚线  
	        g2.setStroke(new BasicStroke(0.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 4.0f, dash1, 0.0f));  
	  
	        // 设置打印字体（字体名称、样式和点大小）（字体名称可以是物理或者逻辑名称）  
	        font = new Font("宋体", Font.PLAIN, 11);  
	        g2.setFont(font);// 设置字体  
	        float heigth = font.getSize2D();// 字体高度  
	        // 标题  
	        g2.drawString("乐分商城", (float) x+50, (float) y + heigth);  
	        float line = 2 * heigth;  
	  
	        font = new Font("宋体", Font.PLAIN, 8);  
	        g2.setFont(font);// 设置字体  
	        heigth = font.getSize2D();// 字体高度  
	  
	        // 显示收银员  
	        g2.drawString("收银员:" + cashier, (float) x, (float) y + line);  
	        // 显示订单号  
//	  
	        line += heigth+5;  
	        // 显示标题  
	        g2.drawString("名称", (float) x , (float) y + line);  
	        g2.drawString("单价", (float) x + 30, (float) y + line);  
	        g2.drawString("点券", (float) x + 60, (float) y + line);  
	        g2.drawString("数量", (float) x + 90, (float) y + line);  
	        line += heigth;  
	        g2.drawLine((int) x, (int) (y + line), (int) x + 160, (int) (y + line));  
	  
	        // 第4行  
	        line += heigth;  
	  
	        // 显示内容  
	        for (int i = 0; i < list.size(); i++) {  
	  
	        	GoodsInfo commodity = list.get(i); 
	            g2.drawString(commodity.getName(), (float) x, (float) y + line);  
	            line += heigth;  	  
	            g2.drawString(commodity.getMoney(), (float) x+30, (float) y + line);  
	            g2.drawString(commodity.getCredit(), (float) x + 60, (float) y + line);  
	            g2.drawString(commodity.getNumber(), (float) x + 90, (float) y + line);  
	            line += heigth;  
	  
	        }  
	        line += heigth;  
	  
	        g2.drawLine((int) x, (int) (y + line), (int) x + 160, (int) (y + line));  
	        line += heigth;  
	  
	        if(goodslog.getLogType().equals("2")){
	        	  g2.drawString("退货商品数:" + sale_num + "件", (float) x, (float) y + line);  
	        }else{
	        	if(goodslog.getCheckType().equals("4")){
		        	  g2.drawString("赠送商品数:" + sale_num + "件", (float) x, (float) y + line);  
		        	  line += heigth; 
		  	        g2.drawString("合计:" + sale_sum + "元，"+ total_credit + "券", (float) x, (float) y + line);  
	        	}else if(goodslog.getCheckType().equals("5")){
	        		 g2.drawString("兑换商品数:" + sale_num + "件", (float) x, (float) y + line);  
	     	        line += heigth; 
	     	        g2.drawString("合计:" + total_credit + "券", (float) x, (float) y + line);  
	     	        line += heigth;  
	     	        g2.drawString("实收:" + sale_credit + "券", (float) x, (float) y + line); 
	     	       
	        	}else{
	        		 g2.drawString("兑换商品数:" + sale_num + "件", (float) x, (float) y + line);  
		     	        line += heigth; 
		     	        g2.drawString("合计:" + sale_sum + "元，"+ total_credit + "券", (float) x, (float) y + line);  
		     	        line += heigth;  
		     	        g2.drawString("实收:" + practical + "元，"+ sale_credit + "券", (float) x, (float) y + line); 
		     	        line += heigth;
		     	        g2.drawString("找零:" + changes + "元", (float) x, (float) y + line); 
		        	}
	        }
	        
	        
	        line += heigth;  
	        g2.drawString("时间:" + Calendar.getInstance().getTime().toLocaleString(), (float) x, (float) y + line);  
	  
	        line += heigth;  
	        g2.drawString("天天平价,日日新鲜", (float) x + 20, (float) y + line);  
	  
	        line += heigth;  
	        g2.drawString("钱票请当面点清，离开柜台恕不负责", (float) x, (float) y + line);  
	        switch (pageIndex) {  
	        case 0:  
	            return PAGE_EXISTS;  
	        default:  
	            return NO_SUCH_PAGE;  
	        }  
	    }  

}
