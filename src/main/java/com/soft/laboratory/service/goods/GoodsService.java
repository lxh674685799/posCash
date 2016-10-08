package com.soft.laboratory.service.goods;

import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soft.core.db.GenericDao;
import com.soft.core.service.GenericService;
import com.soft.laboratory.dao.goods.GoodsDao;
import com.soft.laboratory.model.goods.Goods;
@Transactional
@Service
public class GoodsService extends GenericService<Goods> {

	@Resource
	private GoodsDao goodsDao;
	
	@Override
	protected GenericDao<Goods> getEntityDao() {
		return goodsDao;
	}
	
	/**
	 * 通过商品编码得到商品
	 */
	public Goods getByCode(String code) {
		return goodsDao.getByCode(code);
	}
	/**
	 * 导出excel
	 * @param list
	 * @return
	 */
	public HSSFWorkbook export(List<Goods> list) {
		
		 String[] excelHeader = { "序号","商品条码", "商品名称", "商品类型","供货商","进货价","库存","销售价（现金）","销售价（积分）","销售价（现金+积分）"}; 
		 
		HSSFWorkbook wb = new HSSFWorkbook();    
        HSSFSheet sheet = wb.createSheet("Campaign");    
        HSSFRow row = sheet.createRow((int) 0);    
        HSSFCellStyle style = wb.createCellStyle();    
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);    
    
        for (int i = 0; i < excelHeader.length; i++) {    
            HSSFCell cell = row.createCell(i);    
            cell.setCellValue(excelHeader[i]);    
            cell.setCellStyle(style);    
            sheet.autoSizeColumn(i);    
        }    
    
        for (int i = 0; i < list.size(); i++) {    
            row = sheet.createRow(i + 1);    
            Goods goods = list.get(i);    
            row.createCell(0).setCellValue(i+1); 
            row.createCell(1).setCellValue(goods.getCode() + "");
            row.createCell(2).setCellValue(goods.getName());    
            row.createCell(3).setCellValue(goods.getType().getName());    
            row.createCell(4).setCellValue(goods.getFactory().getName());
            row.createCell(5).setCellValue(goods.getInPrice());
            row.createCell(6).setCellValue(goods.getInventory());
            row.createCell(7).setCellValue(goods.getMoney());
            row.createCell(8).setCellValue(goods.getCredit());
            row.createCell(9).setCellValue(goods.getMoneyCre() + " + " + goods.getCreditMon());
        }    
        return wb;
	}

}
