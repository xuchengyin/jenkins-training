package com.uinnova.test.step_definitions.testcase.dmv.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;

import com.uinnova.test.step_definitions.api.dmv.file.QueryFileInfoById;
import com.uinnova.test.step_definitions.api.dmv.file.QueryFileInfoPage;
import com.uinnova.test.step_definitions.api.dmv.file.RemoveFileByIds;
import com.uinnova.test.step_definitions.api.dmv.file.UploadFiles;
import com.uinnova.test.step_definitions.utils.common.ExcelUtil;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;
import com.uinnova.test.step_definitions.utils.dmv.FormUtil;

import cucumber.api.Delimiter;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Scenario_file {
	 private JSONObject result;
	  private List<String> fileNameList = new ArrayList<String>();
	  
	  @After("@delFileName")
	  public void delFileName(){
		  RemoveFileByIds removeFileByIds = new RemoveFileByIds();
		  if(!fileNameList.isEmpty()){
			  result = removeFileByIds.removeFileByIds(fileNameList);
			  assertTrue(result.getBoolean("success"));	 
			  fileNameList.clear();
		  }
	  }
	  
	  /*========Scenario Outline: DMV_文件_文件导入,文件查看,文件下载,文件删除=======*/
	  @When("^在文件夹\"(.*)\"内导入\"(.*)\"文件$")
	  public void uploadFiles(String dirName,String fileName){
		 String filePath = Scenario_file.class.getResource("/").getPath()+"testData/dmv/excel/" + fileName;
		  UploadFiles  uploadFiles = new UploadFiles();
		  result = uploadFiles.uploadFiles(filePath,dirName);
		  assertTrue(result.getBoolean("success"));
		  fileNameList.add(fileName);
	  }
	  
	@Then("^成功导入\"(.*)\"的\"(.*)\"文件$")
	public void checkUploadFile(String fileName, String sheetName) {
		String filePath = Scenario_file.class.getResource("/").getPath() + "testData/dmv/excel/" + fileName;
		FormUtil formUitl = new FormUtil();
		BigDecimal id = formUitl.getFormIdByFormName(fileName);
		ExcelUtil excelUtil = new ExcelUtil();
		if (fileName.contains(".xls")) {
			if (sheetName.contains(",")) {
				String[] sheetNames = sheetName.split(",");
				for (int i = 0; i < sheetNames.length; i++) {
					JSONArray arr = excelUtil.readFromExcel(filePath, sheetNames[i]);
					for (int j = 0; j < arr.length(); j++) {
						JSONObject obj = (JSONObject) arr.get(j);
						int n = j + 1;
						String sql = "SELECT ROW_NUM,COL_NUM,VALUE,SHEET_NAME From cc_excel_data where EXCEL_ID=" + id
								+ " AND SHEET_NAME = '" + sheetNames[i] + "' AND ROW_NUM = " + n
								+ " AND DATA_STATUS = 1 AND DOMAIN_ID=" + QaUtil.domain_id;
						List list = JdbcUtil.executeQuery(sql);
						for (int k = 0; k < list.size(); k++) {
							HashMap map = (HashMap) list.get(k);
							assertEquals(obj.get(Integer.toString(k)), map.get("VALUE"));
						}
					}
				}
			} else {
				JSONArray arr = excelUtil.readFromExcel(filePath, sheetName);
				for (int j = 0; j < arr.length(); j++) {
					JSONObject obj = (JSONObject) arr.get(j);
					int m = j + 1;
					String sql = "SELECT ROW_NUM,COL_NUM,VALUE From cc_excel_data where EXCEL_ID=" + id
							+ " AND SHEET_NAME = '" + sheetName + "' AND ROW_NUM = " + m
							+ " AND DATA_STATUS = 1 AND DOMAIN_ID=" + QaUtil.domain_id + " ORDER BY ROW_NUM,COL_NUM";
					List list = JdbcUtil.executeQuery(sql);
					for (int k = 0; k < list.size(); k++) {
						HashMap map = (HashMap) list.get(k);
						if (obj.get(Integer.toString(k)) != null && !obj.get(Integer.toString(k)).equals("")) {
							assertEquals(obj.get(Integer.toString(k)), map.get("VALUE"));
						}
					}
				}
		  }
		  
	}else{
		Assert.assertNotNull(id);
	}
}
	 @When("^查看\"(.*)\"表格$")
	  public void queryFileInfoById(String fileName){
		 if(fileName.contains(".xls")){
		    QueryFileInfoById queryFileInfoById = new QueryFileInfoById();
		    result = queryFileInfoById.queryFileInfoById(fileName);
		    assertTrue(result.getBoolean("success"));
		 }
	  }
	  
	@Then("^成功查看\"(.*)\"表格$")
	public void checkQueryFileInfoById(String fileName) {
		if (fileName.contains(".xls")) {
			String sql = "SELECT ID,SEARCH_FIELD,DIR_ID,UPOR_ID,UPOR_NAME,EXCEL_FULL_NAME,EXCEL_NAME,"
					+ "EXCEL_RANGE,UP_TIME,SHEET_COUNT,EXCEL_SIZE,EXCEL_PATH FROM cc_excel where EXCEL_NAME ='"
					+ fileName + "' AND DATA_STATUS =1 AND DOMAIN_ID =" + QaUtil.domain_id;
			List list = JdbcUtil.executeQuery(sql);
			JSONObject data = result.getJSONObject("data");
			if (list != null && list.size() > 0) {
				HashMap map = (HashMap) list.get(0);
				assertEquals(((BigDecimal) map.get("ID")).intValue(), data.getInt("id"));
				assertEquals(map.get("SEARCH_FIELD"), data.get("searchField"));
				assertEquals(((BigDecimal) map.get("DIR_ID")).intValue(), data.getInt("dirId"));
				assertEquals(((BigDecimal) map.get("UPOR_ID")).intValue(), data.getInt("uporId"));
				assertEquals(map.get("EXCEL_FULL_NAME"), data.get("excelFullName"));
				assertEquals(map.get("EXCEL_NAME"), data.get("excelName"));
				assertEquals(((BigDecimal) map.get("EXCEL_RANGE")).intValue(), data.getInt("excelRange"));
				assertEquals((BigDecimal) map.get("UP_TIME"), new BigDecimal(data.getLong("upTime")));
				assertEquals(map.get("UPOR_NAME"), data.get("uporName"));
				assertEquals(((BigDecimal) map.get("SHEET_COUNT")).intValue(), data.getInt("sheetCount"));
				assertEquals(((BigDecimal) map.get("EXCEL_SIZE")).intValue(), data.getInt("excelSize"));
				assertEquals(map.get("EXCEL_PATH"), data.get("excelPath"));
			}
		 }
	  }
	  
	  @When("^删除\"(.*)\"文件$")
	  public void removeFileByIds(String fileName){
		  RemoveFileByIds removeFileByIds = new RemoveFileByIds();
		  List fileNameList11 = new ArrayList();
		  fileNameList.remove(fileName);
		  fileNameList11.add(fileName);
		  result = removeFileByIds.removeFileByIds(fileNameList11);
		  assertTrue(result.getBoolean("success"));	
	  }
	  
	  @Then("^成功删除\"(.*)\"文件$")
	  public void checkRemoveFileByIds(String fileName){
		  FormUtil formUtil = new FormUtil();
		  BigDecimal id = formUtil.getFormIdByFormName(fileName);
		  assertEquals(id,new BigDecimal(0));
	  }
	  
	  /*=======Scenario Outline:DMV_file_文件搜索==============*/
	  @Given("^向文件夹\"(.*)\"导入多个\"(.*)\"表格$")
	  public void uploadExcelList(String dirName,@Delimiter(",") List<String> fileNameList12){
		  for(int i = 0; i< fileNameList12.size(); i++){
			  String fileName = fileNameList12.get(i);
			  String filePath = Scenario_file.class.getResource("/").getPath()+"testData/dmv/excel/" + fileName;
			 
			  UploadFiles  uploadFiles = new UploadFiles();
			  result = uploadFiles.uploadFiles(filePath,dirName);
			  assertTrue(result.getBoolean("success"));
			  fileNameList.add(fileName);
		  }
	  }
	  @When("^在文件夹\"(.*)\"中搜索名称包含\"(.*)\"的表格$")
	  public void queryFileInfoPage(String dirName,String searchKey){
		  QueryFileInfoPage queryFileInfoPage = new QueryFileInfoPage();
		  result = queryFileInfoPage.queryFileInfoPage(searchKey,dirName);
		  assertTrue(result.getBoolean("success"));
	  }
	  
	  @Then("^^在文件夹\"(.*)\"中包含\"(.*)\"关键字的的表格全部搜索出来$")
	  public void checkQueryFileInfoPage(String dirName,String searchKey){
		  String sql = "SELECT ID FROM cc_excel WHERE SEARCH_FIELD like'%" + searchKey.toUpperCase() +"%' and DATA_STATUS = 1 and DOMAIN_ID = " + QaUtil.domain_id;
	      List list = JdbcUtil.executeQuery(sql);
	      JSONArray file = result.getJSONObject("data").getJSONArray("files");
	      assertEquals(list.size(),file.length());
	  }
	  
	  @When("^删除\"(.*)\"多个表格$")
	  public void removeFileByIdsList(@Delimiter(",")List<String> fileNameList){
		  RemoveFileByIds removeFileByIds = new RemoveFileByIds();
		  result = removeFileByIds.removeFileByIds(fileNameList);
		  assertTrue(result.getBoolean("success"));
		  for(int i = 0; i < fileNameList.size(); i ++){
			  String fileName = fileNameList.get(i);
		      fileNameList.remove(fileName);
		  }
	  }
	  
	  @Then("^成功删除\"(.*)\"多个表格$")
	  public void checkRemoveFileByIdsList(@Delimiter(",")List<String>fileNameList){
		  FormUtil formUtil = new FormUtil();
		  for(int i = 0; i < fileNameList.size();i++){
			 String fileName = fileNameList.get(i);
		     BigDecimal id = formUtil.getFormIdByFormName(fileName);
		     assertEquals(id,new BigDecimal(0));
		  }
	  }
}
