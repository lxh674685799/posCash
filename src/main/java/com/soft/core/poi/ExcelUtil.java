package com.soft.core.poi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



/**
 * 使用对象操作Excel
 * @author lfd
 * 2013-12-10
 */
public class ExcelUtil {
	
	private static ExcelUtil util = new ExcelUtil() ;
	
	private ExcelUtil() {}
	
	public static ExcelUtil getInstance() {
		return ExcelUtil.util ;
	}
	
	/**
	 * 使用输入流将数据填充到Excel表中.
	 * @param stream OutputStream
	 * @param clazz Class
	 * @param entitys 实体集合
	 * @param hasXLS true:Excel 2003  false:2007以上.
	 * @return ExcelUtil
	 */
	public ExcelUtil export2Obj(OutputStream stream, Class<?> clazz, List<?> entitys, boolean hasXLS) {
		Workbook workbook = export(clazz, entitys, hasXLS) ;
		try {
			workbook.write(stream) ;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeStream(stream) ;
		}
		return ExcelUtil.util ;
	}
	
	/**
	 * 根据路径将数据填充到Excel表中.
	 * @param path 路径
	 * @param clazz Class
	 * @param entitys  实体集合
	 * @return ExcelUtil
	 */
	public ExcelUtil export2Obj(String path, Class<?> clazz, List<?> entitys) {
		return export2Obj(path, clazz, entitys, true) ;
	}
	
	/**
	 * 根据路径将数据填充到Excel表中. 
	 * @param path 路径
	 * @param clazz Class
	 * @param entitys 实体集合
	 * @param hasXLS true:为Excel 2003版本 false:为Excel 2007以上版本
	 * @return ExcelUtil
	 */
	public ExcelUtil export2Obj(String path, Class<?> clazz, List<?> entitys, boolean hasXLS) {
		Workbook workbook = export(clazz, entitys, hasXLS) ;
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
		return ExcelUtil.util ;
	}
	
	/**
	 * 根据模板从对象导出数据到Excel
	 * @param template 模板位置(绝对路径)
	 * @param path 输出位置(绝对路径)
	 * @param clazz Class
	 * @param entitys 实体集合
	 * @param headNum 标题所在的行(从1开始)
	 * @return ExcelUtil
	 */
	public ExcelUtil exprot2ObjByTemp(String template, String outpath, Class<?> clazz, List<?> entitys, int headNum) {
		return exprot2ObjByTemp(template, outpath, clazz, entitys, headNum, null, false) ;
	}
	
	/**
	 * 根据模板从对象导出数据到Excel
	 * @param template 模板位置(绝对路径)
	 * @param outpath 输出位置(绝对路径)
	 * @param clazz Class
	 * @param entitys 实体集合
	 * @param headNum 标题所在的行(从1开始)
	 * @param datas 模板的点位符数据
	 * @param hasSerNum 是否需要要输出行号
	 * @return ExcelUtil
	 */
	public ExcelUtil exprot2ObjByTemp(String template, String outpath, Class<?> clazz, List<?> entitys, int headNum, Map<String, String> datas, boolean hasSerNum) {
		ExcelTemplate excel = exportTemp(template, clazz, entitys, headNum, datas, hasSerNum) ;
		excel.write2path(outpath) ;
		return ExcelUtil.util ;
	}
	
	/**
	 * 根据模板从对象导出数据到Excel
	 * @param template 模板位置(绝对路径)
	 * @param stream 输出位置(流)
	 * @param clazz Class
	 * @param entitys 实体集合
	 * @param headNum 标题所在的行(从1开始)
	 * @return ExcelUtil
	 */
	public ExcelUtil exprot2ObjByTemp(String template, OutputStream stream, Class<?> clazz, List<?> entitys, int headNum) {
		return exprot2ObjByTemp(template, stream, clazz, entitys, headNum, null, false) ;
	}
	
	/**
	 * 根据模板从对象导出数据到Excel
	 * @param template 模板位置(绝对路径)
	 * @param stream 输出位置(流)
	 * @param clazz Class
	 * @param entitys 实体集合
	 * @param headNum 标题所在的行(从1开始)
	 * @param datas 模板的点位符数据
	 * @param hasSerNum 是否需要要输出行号
	 * @return ExcelUtil
	 */
	public ExcelUtil exprot2ObjByTemp(String template, OutputStream stream, Class<?> clazz, List<?> entitys, int headNum, Map<String, String> datas, boolean hasSerNum) {
		ExcelTemplate excel = exportTemp(template, clazz, entitys, headNum, datas, hasSerNum) ;
		excel.write2Stream(stream) ;
		return ExcelUtil.util ;
	}
	
	/**
	 * 数据所在的位置
	 * @param stream Excel所在的位置
	 * @param clazz Class
	 * @param startLine 标题所在行(从1开始,startLine-1为标题行,tailLine为数据开始行)
	 * @param tailLine 不是数据所占的行数
	 * @return 数据
	 */
	public <T> List<T> readExcel2Obj(InputStream stream, Class<T> clazz, int startLine, int tailLine) {
		List<T> entitys = null ;
		try {
			Workbook workbook = WorkbookFactory.create(stream) ;
			entitys = getEntitys(workbook, clazz, startLine, tailLine) ;
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeStream(stream) ;
		}
		return entitys ;
	}
	
	/**
	 * 数据所在的位置
	 * @param path Excel所在的位置
	 * @param clazz Class
	 * @param startLine 标题所在行(从1开始,startLine-1为标题行,tailLine为数据开始行)
	 * @param tailLine 不是数据所占的行数
	 * @param hasClasspath true:路径为classpath false:path为绝对路径
	 * @return 数据
	 */
	public <T> List<T> readExcel2Obj(String path, Class<T> clazz, int startLine, int tailLine, boolean hasClasspath) {
		Workbook workbook = null ;
		InputStream stream = null ;
		List<T> entitys = null ;
		try {
			if(hasClasspath) {
				if(path != null && !path.startsWith("/")) {
					path = new StringBuffer(path).insert(0, "/").toString() ;
				}
				stream = ExcelTemplate.class.getResourceAsStream(path) ;
				workbook = WorkbookFactory.create(stream) ;
			} else {
				workbook = WorkbookFactory.create(new File(path)) ;
			}
			entitys = getEntitys(workbook, clazz, startLine, tailLine) ;
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} finally {
			closeStream(stream) ;
		}
		return entitys ;
	}
	
	/**
	 * 将数据转换成实体
	 * @param workbook Workbook
	 * @param clazz Class
	 * @param startLine 标题所在行(从1开始,startLine-1为标题行,tailLine为数据开始行)
	 * @param tailLine 不是数据所占的行数
	 * @return
	 */
	private <T> List<T> getEntitys(Workbook workbook, Class<T> clazz, int startLine, int tailLine) {
		List<T> entitys = null ;
		Sheet sheet = workbook.getSheetAt(0) ;
		Map<Integer, ExcelHeader> headers = readHeader(sheet.getRow(startLine - 1), clazz) ;
		int end = sheet.getLastRowNum() - tailLine ; 
		entitys = new ArrayList<T>() ;
		String type = null ;
		try {
			for(int i=startLine; i<=end; i++) {
				Row row = sheet.getRow(i) ;
				T entity = clazz.newInstance() ;
				for(Cell cell : row) {
					
					ExcelHeader header = headers.get(cell.getColumnIndex()) ;
					if(header != null) {
						String methodName = header.getMethodName() ;
						if(methodName.startsWith("is")) {
							methodName = methodName.replaceFirst("is", "set") ;
						} else {
							methodName = methodName.replaceFirst("get", "set") ;
						}
						type = header.getType().toString() ;
//						System.out.println("-------"+header.getType()+methodName);
//						System.out.println(cell.getStringCellValue()+"----------------"+cell.getColumnIndex()+"---"+type);
						Method method = clazz.getDeclaredMethod(methodName, getFieldType(type)) ;
						if(type.equals("class java.util.Date")) {
							method.invoke(entity, cell.getDateCellValue()) ;
						} else if(type.equals("class java.lang.Boolean") || type.equals("boolean")) {
							method.invoke(entity, cell.getBooleanCellValue()) ;
						} else if(type.equals("class java.lang.Integer") || type.equals("int")) {
							method.invoke(entity, (int)cell.getNumericCellValue()) ;
						} else if(type.equals("class java.lang.Double") || type.equals("double")) {
							method.invoke(entity, cell.getNumericCellValue()) ;
						} else if(type.equals("class java.util.Calendar")) {
							Calendar calendar =  Calendar.getInstance() ;
							calendar.setTime(cell.getDateCellValue()) ;
							method.invoke(entity, calendar) ;
						} else {
							method.invoke(entity, cell.getStringCellValue()) ;
						}
					}
				}
				entitys.add(entity) ;
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return entitys ;
	}
	
	/**
	 * 获取对象字段的类型Class
	 * @param type
	 * @return
	 */
	private Class<?> getFieldType(String type) {
		if(type.equals("class java.util.Date")) {
			return Date.class ;
		} else if(type.equals("class java.lang.Boolean")) {
			return Boolean.class ;
		} else if(type.equals("boolean")) {
			return Boolean.TYPE ;
		} else if(type.equals("class java.lang.Integer")) {
			return Integer.class ;
		} else if(type.equals("int")) {
			return Integer.TYPE ;
		} else if(type.equals("class java.lang.Double")) {
			return Double.class ;
		} else if(type.equals("double")) {
			return Double.TYPE ;
		} else if(type.equals("class java.util.Calendar")) {
			return Calendar.class ;
		}
		return String.class ;
	}
	
	/**
	 * 读取模板头信息(标题信息)
	 */
	private Map<Integer, ExcelHeader> readHeader(Row row, Class<?> clazz) {
		List<ExcelHeader> headers = getHeader(clazz) ;
		Map<Integer, ExcelHeader> headerMap = new LinkedHashMap<Integer, ExcelHeader>() ;
		String value = null ;
		for(Cell cell : row) {
			if(cell.getCellType() != Cell.CELL_TYPE_STRING) continue ;
			value = cell.getStringCellValue().trim() ;
			for(ExcelHeader header : headers) {
				if(header.getTitle().equals(value)) {
					headerMap.put(cell.getColumnIndex(), header) ;
					break ;
				}
			}
		}
		return headerMap ;
	}
	
	private ExcelTemplate exportTemp(String template, Class<?> clazz, List<?> entitys, int headNum, Map<String, String> datas, boolean hasSerNum) {
		ExcelTemplate excel = ExcelTemplate.getInstance().readTemplate2path(template) ;
		Map<Integer, ExcelHeader> headerMap = readHeader(excel.getRow(headNum - 1), clazz) ;
		if(headerMap == null || headerMap.isEmpty()) throw new RuntimeException("指定标题行错误!") ;
		try {
			int count = entitys.size() ;
			int number = headerMap.size() ;
			for(int i=0; i<count; i++) {
				Object obj = entitys.get(i) ;
				excel.newRow() ;
				for(int j=0; j<number; j++) {
					ExcelHeader header = headerMap.get(j) ;
					Method method = clazz.getDeclaredMethod(header.getMethodName()) ;
					Object value = method.invoke(obj) ;
					excel.newCol(value.toString()) ;
				}
			}
			if(datas != null && !datas.isEmpty()) excel.replaceFind(datas) ;
			if(hasSerNum) excel.insertSer() ;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return excel ;
	}
	
	private Workbook export(Class<?> clazz, List<?> entitys, boolean hasXLS) {
		Workbook workbook = null ;
		if(hasXLS) {
			workbook = new HSSFWorkbook() ;
		} else {
			workbook = new XSSFWorkbook() ;
		}
		try {
			Sheet sheet = workbook.createSheet() ;			
			//输出标题
			List<ExcelHeader> headers = getHeader(clazz) ;
			Row row = sheet.createRow(0) ;
			int count = headers.size() ;
			for(int i=0; i<count; i++) {
				Cell cell = row.createCell(i) ;
				cell.setCellValue(headers.get(i).getTitle()) ;
			}
			
			//输出数据
			int number = entitys.size() ;
			Method method = null ;
			for(int i=0; i<number; i++) {
				row = sheet.createRow(i+1) ;
				Object obj = entitys.get(i) ;
				for(int j=0; j<count; j++) {
					method = clazz.getDeclaredMethod(headers.get(j).getMethodName()) ;
					Cell cell = row.createCell(j) ;
					String type = headers.get(j).getType().toString() ;
					if(type.equals("class java.util.Date")) {
						Date date = (Date) method.invoke(obj);  
	                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						//cell.setCellValue((Date)method.invoke(obj)) ;
	                    cell.setCellValue(sdf.format(date)) ;
					} else if(type.equals("class java.lang.Boolean") || type.equals("boolean")) {
						cell.setCellValue((Boolean)method.invoke(obj)) ;
					} else if(type.equals("class java.lang.Integer") || type.equals("int")) {
						cell.setCellValue((Integer)method.invoke(obj)) ;
					} else if(type.equals("class java.lang.Double") || type.equals("double")) {
						cell.setCellValue((Double)method.invoke(obj)) ;
					} else if(type.equals("class java.util.Calendar")) {
						cell.setCellValue((Calendar)method.invoke(obj)) ;
					} else {
						cell.setCellValue((String)method.invoke(obj)) ;
					}
				}
			}
			
			//调整宽度(好像没起作用)
			autoColumn(sheet, count);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} 
		return workbook ;
	}
	
	/**
	 * 输出对象标题
	 * @param clazz Class
	 * @return List<ExcelHeader>
	 */
	private List<ExcelHeader> getHeader(Class<?> clazz) {
		Method[] methods = clazz.getDeclaredMethods() ;
		String name = null ;
		List<ExcelHeader> headers = new ArrayList<ExcelHeader>() ;
		for(Method method : methods) {
			name = method.getName() ;
			if(name != null && (name.startsWith("get") || name.startsWith("is"))) {
				if(method.isAnnotationPresent(ExcelSign.class)) {
					ExcelSign sign = method.getAnnotation(ExcelSign.class) ;
					ExcelHeader header = new ExcelHeader(name, sign.title(), sign.order(), method.getGenericReturnType()) ;
					headers.add(header) ;
				}
			}
		}
		Collections.sort(headers) ;
		return headers ;
	}
	
	/**
	 * 调整列宽度
	 * @param sheet
	 * @return
	 */
	private ExcelUtil autoColumn(Sheet sheet, int size) {
		for(int i=0; i<size; i++) {
			sheet.autoSizeColumn(size, true) ;
		}
		return ExcelUtil.util ;
	}
	
	/**
	 * 关闭输出流
	 * @param stream
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
	
	/**
	 * 关闭输入流
	 * @param stream
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
}
