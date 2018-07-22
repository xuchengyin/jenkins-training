package com.uinnova.test.step_definitions.testcase.noah.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.noah.rest.EventtomqImport;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * 编写时间:2018-2-07
 * 编写人:yll
 * 功能介绍:推送告警至mq
 * 备注：推送的告警内容需要放在JSON对象中，由于是推送至NOAH中，而NOAH目前只接受数组型的（若是推送至EP的话，是可以直接JSON对象转换成字符串），所以还需要转换成数组型的，由于mq只接受字符串的，所以还需要再转换成字符串
 */
public class Scenario_eventToMq {
	@When("^来自事件源\"(.*)\",数据源的事件序列号为\"(.*)\"的,告警对象\"(.*)\"的告警指标为\"(.*)\",发生了事件级别为\"(.*)\",源事件级别为\"(.*)\",状态为\"(.*)\",告警内容为\"(.*)\"的告警")
	public void eventtomqImport(String sourceID,String sourceEventID,String sourceCIName,String sourceAlertKey,String severity
			,String sourceSeverity,String status,String summary){
		EventtomqImport eventtomqImport=new EventtomqImport();
		JSONObject obj = new JSONObject();
		List<JSONObject> list01 = new ArrayList<JSONObject>();
		//Map<String,Object> map = new HashMap<String,Object>();
		obj.put("SourceID", sourceID);
		obj.put("SourceEventID", sourceEventID);
		obj.put("SourceCIName", sourceCIName);
		obj.put("SourceAlertKey", sourceAlertKey);
		obj.put("Severity", severity);
		obj.put("SourceSeverity", sourceSeverity);
		obj.put("Status", status);
		obj.put("Summary", summary);
		obj.put("SourceIdentifier", "Identifier");
		list01.add(obj);
		JSONObject result = eventtomqImport.eventtomqImport(list01.toString());
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);

	}
	@Then("^告警对象\"(.*)\"的告警指标\"(.*)\"成功入库$")
	public void queryalarm(String sourceCIName,String sourceAlertKey){
		String sql="SELECT * FROM mon_eap_event_memory where SOURCECINAME = '" +sourceCIName+ "' AND SOURCEALERTKEY = '" +sourceAlertKey+ "' ";
		ArrayList list = JdbcUtil.executeQuery(sql);
		assertEquals(1, list.size());
	}
}
