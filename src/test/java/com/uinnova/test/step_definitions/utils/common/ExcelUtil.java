package com.uinnova.test.step_definitions.utils.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 操作Excel文件工具类
 * @author uinnova
 *
 */
public class ExcelUtil {
	private static final String EXCEL_XLS = "xls";  
	private static final String EXCEL_XLSX = "xlsx";  

	/**
	 * 读取excel文件中名称为sheetName的sheet页
	 * @param filePath
	 * @param sheetName
	 * @return
	 */
	public JSONArray readFromExcel(String filePath, String sheetName){
		JSONArray arr  = new JSONArray();
		try {
			FileInputStream fi = new FileInputStream(filePath);
			Workbook wb = new HSSFWorkbook(fi);
			Sheet sheet = wb.getSheet(sheetName);
			int rowNum = sheet.getLastRowNum();
			for (int i=0; i<=rowNum; i++){
				Row row = sheet.getRow(i);
				JSONObject obj = new JSONObject();
				for(int j=0; j<row.getLastCellNum();j++){
					obj.put(String.valueOf(j), row.getCell(j).toString());
				}
				arr.put(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return arr;
	}

	/**
	 * @param filePath
	 * @param sheetName
	 * @return
	 */
	public JSONArray readFromExcel_useTitleFeildAsKey(String filePath, String sheetName){
		JSONArray arr  = new JSONArray();
		try {
			FileInputStream fi = new FileInputStream(filePath);
			Workbook wb = new HSSFWorkbook(fi);
			Sheet sheet = wb.getSheet(sheetName);
			int rowNum = sheet.getLastRowNum();
			for (int i=0; i<=rowNum; i++){
				Row row = sheet.getRow(i);
				JSONObject obj = new JSONObject();
				for(int j=0; j<row.getLastCellNum();j++){
					obj.put(sheet.getRow(0).getCell(j).toString(), row.getCell(j).toString());
				}
				arr.put(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arr;
	}


	/**
	 * @author ldw
	 * 解决读取时，数字变为科学计数法
	 * @param filePath
	 * @param sheetName
	 * @return
	 */
	public JSONArray readFromExcel_useTitleFeildAsKey_import_ci(String filePath, String sheetName){
		JSONArray arr  = new JSONArray();
		try {
			FileInputStream fi = new FileInputStream(filePath);
			Workbook wb = new HSSFWorkbook(fi);
			Sheet sheet = wb.getSheet(sheetName);
			int rowNum = sheet.getLastRowNum();
			for (int i=0; i<=rowNum; i++){
				Row row = sheet.getRow(i);
				JSONObject obj = new JSONObject();
				for(int j=0; j<row.getLastCellNum();j++){
					obj.put(sheet.getRow(0).getCell(j).toString(), getHSSTextString(row, j));
				}
				arr.put(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arr;
	}

	public String getHSSTextString(Row row, int colNum){  

		Cell cell = row.getCell(colNum);  
		if (null != cell) {  
			switch (cell.getCellType()) {  
			case HSSFCell.CELL_TYPE_NUMERIC:  
				DecimalFormat df = new DecimalFormat("#");//转换成整型  
				return  df.format(cell.getNumericCellValue());  
				//return String.valueOf(cell.getNumericCellValue());  

			case HSSFCell.CELL_TYPE_STRING:  
				return cell.getStringCellValue().trim();  
			case HSSFCell.CELL_TYPE_BLANK: // 空值  
				return "";  
			case HSSFCell.CELL_TYPE_ERROR: // 故障  
				return "";  
			default:  
				return "";  
			}  
		} else {  
			return "";  
		}  
	}

	/**
	 * 获取到excel表的所有sheet名称
	 * @param filePath
	 * @return
	 */
	public static JSONArray GetExcelSheetNames(String filePath)
	{
		JSONArray names = new JSONArray(); 
		try
		{
			FileInputStream fi = new FileInputStream(filePath);
			Workbook wb = new HSSFWorkbook(fi);
			for (int i=0; i<wb.getNumberOfSheets(); i++){
				Sheet sheet = wb.getSheetAt(i);	
				names.put(sheet.getSheetName());
			}
			return names;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/*	*//**
	 * 统计每个接口的耗时并输出到文件
	 * @param apiUrl 接口url
	 * @param countTime 耗时
	 * @param finalXlsxPath 写入的文件
	 */
	public static void writeExcel(String apiUrl, long countTime,String finalXlsxPath){  
		OutputStream out = null;  
		try {  
			// 读取Excel文档  
			File finalXlsxFile = new File(finalXlsxPath);  
			Workbook workBook = getWorkbok(finalXlsxFile);  
			// sheet 对应一个工作页  
			Sheet sheet = workBook.getSheetAt(0);  
			int rowNumber = sheet.getLastRowNum();  // 第一行从0开始算  
			// 在第文件末尾新增一行
			Row row = sheet.createRow(rowNumber+1);  
			Cell first = row.createCell(0);  
			first.setCellValue(apiUrl);  
			Cell second = row.createCell(1);  
			second.setCellValue(countTime);  
			Cell three = row.createCell(2);  
			three.setCellValue(DateUtil.getToday());  
			// 创建文件输出流，准备输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效  
			out =  new FileOutputStream(finalXlsxPath);  
			workBook.write(out);  
		} catch (Exception e) {  
			e.printStackTrace();  
		} finally{  
			try {  
				if(out != null){  
					out.flush();  
					out.close();  
				}  
			} catch (IOException e) {  
				e.printStackTrace();  
			}  
		}  
		System.out.println("数据导出成功");  
	}  


	/**
	 * 清空数据，除了属性列
	 * @param finalXlsxPath
	 */
	public static void cleanExcel(String finalXlsxPath) {  
		OutputStream out = null;  
		try{
			// 读取Excel文档  
			File finalXlsxFile = new File(finalXlsxPath);  
			Workbook workBook = getWorkbok(finalXlsxFile);  
			// sheet 对应一个工作页  
			Sheet sheet = workBook.getSheetAt(0);  
			/** 
			 * 删除原有数据，除了属性列 
			 */  
			int rowNumber = sheet.getLastRowNum();  // 第一行从0开始算  
			System.out.println("原始数据总行数，除属性列：" + rowNumber);  
			for (int i = 1; i <= rowNumber; i++) {  
				Row row = sheet.getRow(i);  
				sheet.removeRow(row);  
			}  
			// 创建文件输出流，输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效  
			out =  new FileOutputStream(finalXlsxPath);  
			workBook.write(out); 
		}catch (IOException e){
			e.printStackTrace();
		}
	}  



	/** 
	 * 判断Excel的版本,获取Workbook 
	 * @param in 
	 * @param filename 
	 * @return 
	 * @throws IOException 
	 */  
	public static Workbook getWorkbok(File file) throws IOException{  
		Workbook wb = null;  
		FileInputStream in = new FileInputStream(file);  
		if(file.getName().endsWith(EXCEL_XLS)){  //Excel 2003  
			wb = new HSSFWorkbook(in);  
		}else if(file.getName().endsWith(EXCEL_XLSX)){  // Excel 2007/2010  
			wb = new XSSFWorkbook(in);  
		}  
		return wb;  
	}  

	/**
	 * 获得文件中某个sheet的第一个表头
	 * @param filePath
	 * @param sheetName
	 * @return
	 */
	public static String getFirstHeadTitle(String filePath, String sheetName){
		String firstHeadTitle = "";
		try {
			FileInputStream fi = new FileInputStream(filePath);
			Workbook wb = new HSSFWorkbook(fi);
			Sheet sheet = wb.getSheet(sheetName);			
			Row row = sheet.getRow(0);
			firstHeadTitle =  row.getCell(0).toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return firstHeadTitle;
	}

	/**
	 * 是否存在某个表头标题
	 * @param filePath
	 * @param sheetName
	 * @param destHeadTitle
	 * @return
	 */
	public static boolean getFirstHeadTitle(String filePath, String sheetName, String destHeadTitle){
		boolean hasHeadTitle = false;
		try {
			FileInputStream fi = new FileInputStream(filePath);
			Workbook wb = new HSSFWorkbook(fi);
			Sheet sheet = wb.getSheet(sheetName);			
			Row row = sheet.getRow(0);

			for(int j=0; j<row.getLastCellNum();j++){
				if (destHeadTitle.compareToIgnoreCase(sheet.getRow(0).getCell(j).toString())==0){
					hasHeadTitle = true;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return hasHeadTitle;
	}
}
