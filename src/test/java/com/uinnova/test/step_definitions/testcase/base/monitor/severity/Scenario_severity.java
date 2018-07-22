package com.uinnova.test.step_definitions.testcase.base.monitor.severity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.cmv.monitor.severity.QuerySeverityList;
import com.uinnova.test.step_definitions.api.cmv.monitor.severity.RemoveSeverityByIds;
import com.uinnova.test.step_definitions.api.cmv.monitor.severity.SaveOrUpdateSeverity;
import com.uinnova.test.step_definitions.api.cmv.monitor.severity.UploadVoice;
import com.uinnova.test.step_definitions.utils.base.SeverityUtil;

import cucumber.api.Delimiter;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * 编写时间:2018-01-19
 * 编写人:sunsl
 * 功能介绍:事件级别定义测试用例类
 */
public class Scenario_severity {
	JSONObject result;
	private ArrayList<String> serverityList = new ArrayList<String>();
	@After("@delServerity")
	public void delServerity(){
		if(!serverityList.isEmpty()){
			RemoveSeverityByIds removeSeverityByIds = new RemoveSeverityByIds();
			result = removeSeverityByIds.removeSeverityByIds(serverityList);
            assertTrue(result.getBoolean("success"));
		}
	}
	
  /*===============Scenario Outline:Severity_事件级别定义增删改查============*/
  @When("^创建级别数值为\"(.*)\"，颜色值为\"(.*)\"，告警声音为\"(.*)\"，中文名为\"(.*)\"，英文名为\"(.*)\"的事件级别定义$")
  public void saveOrUpdateSeverity(String severity,String color,String filename,String chineseName,String englishName){
	  String filePath = Scenario_severity.class.getResource("/").getPath()+"testData/cmv/monitor/severity/" + filename;
	  UploadVoice uploadVoice = new UploadVoice();
	  result = uploadVoice.uploadVoice(filePath);
	  String voiceUrl = result.getString("data");
	  SaveOrUpdateSeverity  saveOrUpdateSeverity = new SaveOrUpdateSeverity();
	  result = saveOrUpdateSeverity.saveOrUpdateSeverity(severity, color, filename, chineseName, englishName, voiceUrl,"");
	  assertTrue(result.getBoolean("success"));
	  serverityList.add(severity);
  }
  
  @Then("^系统中存在级别数值为\"(.*)\"，颜色值为\"(.*)\"，告警声音为\"(.*)\"，中文名为\"(.*)\"，英文名为\"(.*)\"的事件级别定义$")
  public void checkSaveOrUpdateSeverity(String severity,String color,String filename,String chineseName,String englishName){
	  QuerySeverityList querySeverityList = new QuerySeverityList();
	  result = querySeverityList.querySeverityList();
	  JSONArray data = result.getJSONArray("data");
	  for(int i = 0; i< data.length(); i++){
		  JSONObject obj = (JSONObject)data.get(i);
		  if(obj.getBigDecimal("severity").compareTo(new BigDecimal(severity))==0){
			  assertEquals(color,obj.getString("color"));
              assertEquals(filename,obj.getString("voiceName"));
              assertEquals(chineseName,obj.getString("chineseName"));
              assertEquals(englishName,obj.getString("englishName"));
			  break;
		  }
	  }
  }
  
  @When("^将级别数值为\"(.*)\"事件级别修改为\"(.*)\"，颜色值修改为\"(.*)\"，告警声音修改为\"(.*)\"，中文名修改为\"(.*)\"，英文名修改为\"(.*)\"的事件级别定义$")
  public void updateSeverity(String severity,String updateSeverity,String updateColor,String updateFilename,String updateChineseName,String updateEnglishName){
	  String filePath = Scenario_severity.class.getResource("/").getPath()+"testData/cmv/monitor/severity/" + updateFilename;
	  UploadVoice uploadVoice = new UploadVoice();
	  result = uploadVoice.uploadVoice(filePath);
	  String voiceUrl = result.getString("data");
	  SaveOrUpdateSeverity  saveOrUpdateSeverity = new SaveOrUpdateSeverity();
	  result = saveOrUpdateSeverity.saveOrUpdateSeverity(severity, updateColor, updateFilename, updateChineseName, updateEnglishName, voiceUrl, updateSeverity);
	  assertTrue(result.getBoolean("success"));
	  serverityList.remove(severity);
	  serverityList.add(updateSeverity);
  }
  
  @When("^删除级别数值为\"(.*)\"的事件级别定义$")
  public void removeSeverityByIds(String updateSeverity){
	  RemoveSeverityByIds removeSeverityByIds = new RemoveSeverityByIds();
	  result = removeSeverityByIds.removeSeverityByIds(updateSeverity);
	  assertTrue(result.getBoolean("success"));
	  serverityList.remove(updateSeverity);
  }
  
  @Then("^系统中不存在级别数值为\"(.*)\"的事件级别定义$")
  public void checkRemoveSeverityByIds(String updateSeverity){
	  SeverityUtil severityUtil = new SeverityUtil();
	  BigDecimal id = severityUtil.getIdByServerity(updateSeverity);
	  assertEquals(id,new BigDecimal(0));
  }
  
  /*====================Scenario: Severity_删除多个事件级别定义=========*/
  @Given("^创建多个级别数值为\"(.*)\"，颜色值为\"(.*)\"，告警声音为\"(.*)\"，中文名为\"(.*)\"，英文名为\"(.*)\"的事件级别定义$")
  public void saveOrUpdateSeverityDumpli(@Delimiter(",")List<String> severityList, String color, String fileName,String chineseName,String englishName){
	  String filePath = Scenario_severity.class.getResource("/").getPath()+"testData/cmv/monitor/severity/" + fileName;
	  UploadVoice uploadVoice = new UploadVoice();
	  result = uploadVoice.uploadVoice(filePath);
	  String voiceUrl = result.getString("data");
	  SaveOrUpdateSeverity  saveOrUpdateSeverity = new SaveOrUpdateSeverity();
	  for(int i = 0; i < severityList.size(); i ++){
		  String severity = severityList.get(i);
	      result = saveOrUpdateSeverity.saveOrUpdateSeverity(severity, color, fileName, chineseName, englishName, voiceUrl,"");
	      assertTrue(result.getBoolean("success"));
	      serverityList.add(severity);
	  }    
  }
  
  @When("^删除多个级别数值为\"(.*)\"的事件级别定义$")
  public void removeSeverityByIdsDumpli(@Delimiter(",")ArrayList<String> severityList){
	  RemoveSeverityByIds removeSeverityByIds = new RemoveSeverityByIds();
	  result = removeSeverityByIds.removeSeverityByIds(severityList);
	  assertTrue(result.getBoolean("success"));
  }
  
  @Then("^系统中不存在多个级别数值为\"(.*)\"的事件级别定义$")
  public void checkRemoveSeverityByIdsDumpli(@Delimiter(",")ArrayList<String> severityList){
	  for(int i = 0; i< severityList.size(); i++){
		  String severity = severityList.get(i);
		  SeverityUtil severityUtil = new SeverityUtil();
		  BigDecimal id = severityUtil.getIdByServerity(severity);
		  assertEquals(id,new BigDecimal(0));
	  }
  }
}
