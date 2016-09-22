package com.soft.core.poi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * 使用Excel模板操作Excel
 * @author lfd
 * 2013-12-09
 */
public class ExcelTemplate {
	public static final String START = "start" ;
	public static final String END = "end" ;
	public static final String STYLES = "styles" ;
	public static final String DEFAULTSTYLE = "defaultStyles" ;
	public static final String SERNUMS = "sernums" ;
	private int lastRowNum ; //模板最后一行
	private int initRowNum ; //模板初始行
	private int initColNum ; //模板初始列
	private int curRowNum ; //当前行
	private int curColNum ; //当前列
	private float defaultHeight ; //默认行高
	private int serColNum ; //序号所在的列
	private CellStyle defauleStyle = null ; //默认样式
	private Map<Integer, CellStyle> styles = null ;
	private static ExcelTemplate excel = new ExcelTemplate() ;
	private Workbook workbook = null ;
	private Sheet sheet = null ;
	private Row curRow = null ; //当前行
	
	private ExcelTemplate() {}
	
	public static ExcelTemplate getInstance() {
		return ExcelTemplate.excel ;
	}
	
	/**
	 * 根据InputStream读取模板
	 * @param stream InputStream
	 * @return ExcelTemplate
	 */
	public ExcelTemplate readTemplate2path(InputStream stream) {
		try {
			workbook = WorkbookFactory.create(stream) ;
			initTemplate() ;
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeStream(stream) ;
		}
		return ExcelTemplate.excel ;
	}
	
	/**
	 * 根据classpath读取模板
	 * @param path classpath
	 * @return ExcelTemplate
	 */
	public ExcelTemplate readTemplate2path(String path) {
		return readTemplate2path(path, true) ;
	}
	
	/**
	 * 输入模板路径,根据路径读取模板.
	 * @param path 路径
	 * @param hasClasspath true:模板来自classpath false:模板来自系统路径.
	 * @return ExcelTemplate
	 */
	public ExcelTemplate readTemplate2path(String path, boolean hasClasspath) {
		InputStream stream = null ;
		try {
			if(hasClasspath) {
				if(path != null && !path.startsWith("/")) {
					path = new StringBuffer(path).insert(0, "/").toString() ;
				}
				System.out.println(path);
				stream = ExcelTemplate.class.getResourceAsStream(path) ;
				if(stream==null){
					System.out.println("---------------------------");
				}
				
				workbook = WorkbookFactory.create(stream) ;
			} else {
				workbook = WorkbookFactory.create(new File(path)) ;
			}
			initTemplate() ;
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeStream(stream) ;
		}
		return ExcelTemplate.excel ;
	}
	
	/**
	 * 创建新的一行
	 * @return ExcelTemplate
	 */
	public ExcelTemplate newRow() {
		if(lastRowNum > curRowNum && curRowNum != initRowNum) {
			sheet.shiftRows(curRowNum, lastRowNum, 1, true, true) ; //移动行
			lastRowNum ++ ;
		}
		curRow = sheet.createRow(curRowNum) ;
		curRow.setHeightInPoints(defaultHeight) ;
		curRowNum ++ ;
		curColNum = initColNum ;
		return ExcelTemplate.excel ;
	}
	
	public Workbook getWorkbook() {
		return workbook;
	}

	public Sheet getSheet() {
		return sheet;
	}

	/**
	 * 获取行
	 * @param rowNum 行号
	 * @return Row
	 */
	public Row getRow(int rowNum) {
		return sheet.getRow(rowNum) ;
	}
	
	/**
	 * 获取列
	 * @param row Row
	 * @param cellNum 列号
	 * @return Cell
	 */
	public Cell getCell(Row row, int cellNum) {
		return row.getCell(cellNum) ;
	}
	
	public ExcelTemplate newCol(String value) {
		Cell cell = curRow.createCell(curColNum) ;
		//设置样式
		setStyle(cell) ;
		cell.setCellValue(value) ;
		curColNum ++ ;
		return ExcelTemplate.excel ;
	}
	
	public ExcelTemplate newCol(int value) {
		Cell cell = curRow.createCell(curColNum) ;
		//设置样式
		setStyle(cell) ;
		cell.setCellValue(value) ;
		curColNum ++ ;
		return ExcelTemplate.excel ;
	}
	
	public ExcelTemplate newCol(boolean value) {
		Cell cell = curRow.createCell(curColNum) ;
		//设置样式
		setStyle(cell) ;
		cell.setCellValue(value) ;
		curColNum ++ ;
		return ExcelTemplate.excel ;
	}
	
	public ExcelTemplate newCol(double value) {
		Cell cell = curRow.createCell(curColNum) ;
		//设置样式
		setStyle(cell) ;
		cell.setCellValue(value) ;
		curColNum ++ ;
		return ExcelTemplate.excel ;
	}
	
	public ExcelTemplate newCol(Date value) {
		Cell cell = curRow.createCell(curColNum) ;
		//设置样式
		setStyle(cell) ;
		cell.setCellValue(value) ;
		curColNum ++ ;
		return ExcelTemplate.excel ;
	}
	
	public ExcelTemplate newCol(Calendar value) {
		Cell cell = curRow.createCell(curColNum) ;
		//设置样式
		setStyle(cell) ;
		cell.setCellValue(value) ;
		curColNum ++ ;
		return ExcelTemplate.excel ;
	}
	
	public ExcelTemplate newCol(RichTextString value) {
		Cell cell = curRow.createCell(curColNum) ;
		//设置样式
		setStyle(cell) ;
		cell.setCellValue(value) ;
		curColNum ++ ;
		return ExcelTemplate.excel ;
	}
	
	/**
	 * 插入序号
	 * @return ExcelTemplate
	 */
	public ExcelTemplate insertSer() {
		Row row = null ;
		Cell cell = null ;
		int count = 0 ;
		for(int i=initRowNum; i<curRowNum; i++) {
			row = sheet.getRow(i) ;
			cell = row.createCell(serColNum) ;
			setStyle(cell) ;
			cell.setCellValue(++count) ;
		}
		return ExcelTemplate.excel ;
	}
	
	/**
	 * 替换点位符数据
	 * @param datas 数据集合
	 * @return ExcelTemplate
	 */
	public ExcelTemplate replaceFind(Map<String, String> datas) {
		String value = null ;
		String key = null ;
		for(Row row : sheet) {
			for(Cell cell : row) {
				if(Cell.CELL_TYPE_STRING != cell.getCellType()) continue ;
				value = cell.getStringCellValue().trim() ;
				if(value != null && value.startsWith("#")) {
					key = value.substring(1) ;
					if(datas.containsKey(key)) {
						cell.setCellValue(datas.get(key)) ;
					}
				}
			}
		}
		return ExcelTemplate.excel ;
	}
	
	/**
	 * 将数据输出到流.
	 * @param stream
	 */
	public void write2Stream(OutputStream stream) {
		try {
			workbook.write(stream) ;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeStream(stream) ;
		}
	}
	
	/**
	 * 根据path指定路径输出数据到Excel
	 * @param path 路径
	 */
	public void write2path(String path) {
		OutputStream stream = null ;
		try {
			stream = new FileOutputStream(path) ;
			workbook.write(stream) ;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeStream(stream) ;
		}
	}
	
	/**
	 * 初始化模板数据
	 */
	private void initTemplate() {
		sheet = workbook.getSheetAt(0) ;
		styles = new HashMap<Integer, CellStyle>() ;
		initConfig() ;
		lastRowNum = sheet.getLastRowNum() ;
	}
	
	/**
	 * 初始化模板配置数据
	 */
	private void initConfig() {
		String value = null ;
		boolean hasEnd = false ;
		for(Row row : sheet) {
			for(Cell cell : row) {
				if(Cell.CELL_TYPE_STRING != cell.getCellType()) continue ;
				value = cell.getStringCellValue().trim() ;
				if(END.equals(value)) {
					sheet.removeRow(row) ;
					hasEnd = true ;
					break ;
				}
				if(SERNUMS.equals(value)) {
					styles.put(cell.getColumnIndex(), cell.getCellStyle()) ;
					serColNum = cell.getColumnIndex() ;
					continue ;
				}
				if(DEFAULTSTYLE.equals(value)) {
					defauleStyle = cell.getCellStyle() ; 
					continue ;
				}
				if(START.equals(value)) {
					initRowNum = row.getRowNum() ;
					initColNum = cell.getColumnIndex() ;
					curRowNum = initRowNum ;
					curColNum = initColNum ;
					defaultHeight = row.getHeightInPoints() ;
					if(defauleStyle == null) defauleStyle = cell.getCellStyle() ;  //当没有默认样式时,将开始列调协为默认样式.
					continue ;
				}
				if(STYLES.equals(value)) {
					styles.put(cell.getColumnIndex(), cell.getCellStyle()) ;
					continue ;
				}
			}
			if(hasEnd) break ;
		}
	}
	
	/**
	 *  设置样式
	 * @param cell Cell
	 */
	private void setStyle(Cell cell) {
		int colIndex = cell.getColumnIndex() ;
		if(styles.containsKey(colIndex)) {
			cell.setCellStyle(styles.get(colIndex)) ;
		} else {
			cell.setCellStyle(defauleStyle) ;
		}
	}
	
	/**
	 * 关闭输入流
	 * @param stream InputStream
	 */
	private void closeStream(InputStream stream) {
		try {
			if(stream != null) {
				stream.close() ;
				stream = null ;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭输出流
	 * @param stream OutputStream
	 */
	private void closeStream(OutputStream stream) {
		try {
			if(stream != null) {
				stream.close() ;
				stream = null ;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
