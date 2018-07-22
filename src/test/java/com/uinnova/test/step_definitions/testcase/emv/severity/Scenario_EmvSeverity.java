package com.uinnova.test.step_definitions.testcase.emv.severity;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.cmv.monitor.severity.QuerySeverityList;
import com.uinnova.test.step_definitions.api.cmv.monitor.severity.RemoveSeverityByIds;
import com.uinnova.test.step_definitions.api.cmv.monitor.severity.SaveOrUpdateSeverity;
import com.uinnova.test.step_definitions.api.dmv.group.AddImage;
import com.uinnova.test.step_definitions.api.dmv.group.RemoveGroupById;
import com.uinnova.test.step_definitions.api.dmv.group.SaveOrUpdateGroupInfo;
import com.uinnova.test.step_definitions.api.emv.group.DeleteGroup;
import com.uinnova.test.step_definitions.api.emv.group.SaveGroup;
import com.uinnova.test.step_definitions.api.emv.rule.UserList;
import com.uinnova.test.step_definitions.api.emv.severity.DelSeverity;
import com.uinnova.test.step_definitions.api.emv.severity.SaveSeverity;
import com.uinnova.test.step_definitions.api.emv.severity.SeverityList;
import com.uinnova.test.step_definitions.api.emv.severity.UploadVoice;
import com.uinnova.test.step_definitions.api.emv.severity.ValidateSeverity;
import com.uinnova.test.step_definitions.testcase.base.monitor.severity.Scenario_severity;
import com.uinnova.test.step_definitions.testcase.dmv.group.Scenario_group;
import com.uinnova.test.step_definitions.utils.EMV.EmvGroupUtil;
import com.uinnova.test.step_definitions.utils.EMV.EmvSeverityUtil;
import com.uinnova.test.step_definitions.utils.base.SeverityUtil;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;
import com.uinnova.test.step_definitions.utils.dmv.GroupUtil;

import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
/**
 * @author yll
 * 
 */
public class Scenario_EmvSeverity {
	
	@When("^新建级别数值为\"(.*)\"，颜色值为\"(.*)\"，告警声音为\"(.*)\"，中文名为\"(.*)\"，英文名为\"(.*)\"的事件级别$")
	  public void saveSeverity(String severity,String color,String voiceName,String chineseName,String englishName){
		  String filePath = Scenario_EmvSeverity.class.getResource("/").getPath()+"testData/emv/severity/" + voiceName;
		  UploadVoice uploadVoice = new UploadVoice();
		  String voiceUrl = uploadVoice.uploadVoice(filePath).getJSONObject("data").getString("voiceUrl");
		  SaveSeverity  saveSeverity = new SaveSeverity();
		  JSONObject result = saveSeverity.saveSeverity(severity, color, chineseName,voiceName,voiceUrl,englishName, "");//这的传参要与saveSeverity方法里面的保持一致
		  assertTrue(result.getBoolean("success"));
		  //serverityList.add(severity);
	  }
	  
	  @Then("^字典表存在级别数值为\"(.*)\"，颜色值为\"(.*)\"，告警声音为\"(.*)\"，中文名为\"(.*)\"，英文名为\"(.*)\"的事件级别$")
	  public void checkSaveSeverity(String severity,String color,String voiceName,String chineseName,String englishName){
		  SeverityList querySeverityList = new SeverityList();
		  JSONArray result = querySeverityList.severityList().getJSONArray("data");
		  //通过接口去查询是否有新增的级别 
		  for(int i = 0; i< result.length(); i++){
			  JSONObject obj = (JSONObject)result.get(i);
			  if(obj.getBigDecimal("severity").compareTo(new BigDecimal(severity))==0){
				  assertEquals(color,obj.getString("color"));
	              assertEquals(voiceName,obj.getString("voiceName"));
	              assertEquals(chineseName,obj.getString("chineseName"));
	              assertEquals(englishName,obj.getString("englishName"));
				  break;
			  }
		  }
	  }
	  @And  ("^再次新建级别数值为\"(.*)\"的事件级别，创建失败，kw=\"(.*)\"$")
	  public void validateSeverity(String severity, String kw)
	  {
		  ValidateSeverity  validateSeverity = new ValidateSeverity();
		  JSONObject result = validateSeverity.validateSeverityAgain(severity, kw);
		  //{"code":500,"success":false,"message":"null[4]已存在"}
		  //assertTrue(result.getBoolean("success"));
		  assertEquals(null, result);
	  }
	  @When ("^将级别数值为\"(.*)\"事件级别修改为\"(.*)\"，颜色值修改为\"(.*)\"，告警声音修改为\"(.*)\"，中文名修改为\"(.*)\"，英文名修改为\"(.*)\"的事件级别$")
	  public void updateSeverity(String severity,String updateSeverity,String updateColor,String updateVoiceName,String updateChineseName,String updateEnglishName){
		  String filePath = Scenario_EmvSeverity.class.getResource("/").getPath()+"testData/emv/severity/" + updateVoiceName;
		  UploadVoice uploadVoice = new UploadVoice();
		  String voiceUrl = uploadVoice.uploadVoice(filePath).getJSONObject("data").getString("voiceUrl");
		  System.out.println(voiceUrl);
		  SaveSeverity  saveOrUpdateSeverity = new SaveSeverity();
		  JSONObject result = saveOrUpdateSeverity.saveSeverity(severity, updateColor, updateChineseName,updateVoiceName,voiceUrl, updateEnglishName,updateSeverity);
		  System.out.println(result);
		  assertTrue(result.getBoolean("success"));
//		  serverityList.remove(severity);
//		  serverityList.add(updateSeverity);
	  }
		@Then ("^系统中存在级别数值为\"(.*)\"，颜色值为\"(.*)\"，告警声音为\"(.*)\"，中文名为\"(.*)\"，英文名为\"(.*)\"的事件级别$")
		public void checkUpdateSeverity(String updateSeverity,String updateColor,String updateVoiceName,String updateChineseName,String updateEnglishName){
			  SeverityList querySeverityList = new SeverityList();
			  JSONArray result = querySeverityList.severityList().getJSONArray("data");
			  System.out.println("545454555454"+result);
			  //通过接口去查询是否有新增的级别 
			  for(int i = 0; i< result.length(); i++){
				  JSONObject obj = (JSONObject)result.get(i);
				  if(obj.getBigDecimal("severity").compareTo(new BigDecimal(updateSeverity))==0){
					  assertEquals(updateColor,obj.getString("color"));
		              assertEquals(updateVoiceName,obj.getString("voiceName"));
		              assertEquals(updateChineseName,obj.getString("chineseName"));
		              assertEquals(updateEnglishName,obj.getString("englishName"));
					  break;
				  }
			  }
		  }
		@When ("^删除级别数值为\"(.*)\"的事件级别$")
		public void delSeverityById(String updateSeverity){
			DelSeverity delSeverityByIds = new DelSeverity();
			JSONObject result = delSeverityByIds.delSeverity(updateSeverity);
			assertTrue(result.getBoolean("success"));
			//serverityList.remove(updateSeverity);
		  }
		@Then ("^系统中不存在级别数值为\"(.*)\"的事件级别$")
		public void checkDelSeverityById(String updateSeverity){
			EmvSeverityUtil severityUtil = new EmvSeverityUtil();
			  BigDecimal id = severityUtil.getIdByServerity(updateSeverity);
			  System.out.println(id);
			  assertEquals(id,new BigDecimal(0));
		  }
		  
		
}
