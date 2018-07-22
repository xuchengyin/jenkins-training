package com.uinnova.test.step_definitions.testcase.base.monitor.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.base.monitor.event.SaveBatch;
import com.uinnova.test.step_definitions.api.base.monitor.severity.QuerySeverityDropList;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author wsl
 * 模拟告警
 *
 */
public class Scenario_event {

	@When("^发送模拟告警:告警对象\"(.*)\"告警指标\"(.*)\"告警级别\"(.*)\"告警详情\"(.*)\"告警状态\"(.*)\"$")
	public void sendEvent(String sourceCIName, String sourceAlertKey, String severity, String summary, String status){
		SaveBatch saveBatch = new SaveBatch();

		if ("打开".compareToIgnoreCase(status)==0){
			status ="1";
		}else if ("关闭".compareToIgnoreCase(status)==0){
			status ="2";
		}else{
			fail("输入告警状态不对");
		}
		
		JSONObject result = saveBatch.saveBatch(sourceCIName, sourceAlertKey, severity, summary, status);
		assertTrue(result.getBoolean("success"));

	}

	@Then("^数据库存在或不存在一条告警记录:告警对象\"(.*)\"告警指标\"(.*)\"告警级别\"(.*)\"告警详情\"(.*)\"告警状态\"(.*)\"$")
	public void checkSendEvent(String sourceCIName, String sourceAlertKey, String severity, String summary, String status){
		QuerySeverityDropList qServityDropList= new QuerySeverityDropList();
		JSONObject servityResult = qServityDropList.querySeverityDropList();
		JSONArray servityArr = servityResult.getJSONArray("data");
		int severityId=0;
		for (int i=0; i<servityArr.length(); i++){
			JSONObject tempObj = servityArr.getJSONObject(i);
			if (severity.compareToIgnoreCase(tempObj.getString("chineseName"))==0){
				severityId = tempObj.getInt("severity");
				break;
			}
		}
		int iStatus =0;
		if ("打开".compareToIgnoreCase(status)==0){
			iStatus =1;
		}else if ("关闭".compareToIgnoreCase(status)==0){
			iStatus =2;
		}else{
			fail("输入告警状态不对");
		}
		String sql = "select IDENTIFIER from mon_eap_event_memory where SEVERITY ="+severityId+" and SUMMARY='"+summary+"' and status="+iStatus+" and SOURCEALERTKEY='"+sourceAlertKey+
				"' and SOURCECINAME='"+sourceCIName+"' and  CINAME='"+sourceCIName+"'";
		List eventL = JdbcUtil.executeQuery(sql);
		if (iStatus ==1)
			assertEquals(eventL.size(), 1);
		if (iStatus ==2)//关闭 是从数据表中删除
			assertEquals(eventL.size(), 0);
	}
	
}
