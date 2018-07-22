package com.uinnova.test.step_definitions.testcase.noah.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.cmv.monitor.event.QueryEventDetails;
import com.uinnova.test.step_definitions.api.noah.rest.EventImport;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * 编写时间:2017-12-12
 * 编写人:sunsl
 * 功能介绍:推送告警
 */
public class Scenario_event {
	@When("^给事件源为\"(.*)\",SourceEventID为\"(.*)\",CI为\"(.*)\"推送告警\"(.*)\",事件级别为\"(.*)\",值为\"(.*)\",状态为\"(.*)\",主题为\"(.*)\",时间为\"(.*)\"$")
     public void eventImport(String sourceID,String sourceEventID,String sourceCIName,String sourceAlertKey,String severity
    	 ,String sourceSeverity,String status,String summary,String lastOccurrence){
		EventImport eventImport = new EventImport();
		JSONObject obj = new JSONObject();
		List<JSONObject> pbs = new ArrayList<JSONObject>();
		//Map<String,Object> map = new HashMap<String,Object>();
		obj.put("SourceID", sourceID);
		obj.put("SourceEventID", sourceEventID);
		obj.put("SourceCIName", sourceCIName);
		obj.put("SourceAlertKey", sourceAlertKey);
		obj.put("Severity", severity);
		obj.put("SourceSeverity", sourceSeverity);
		obj.put("Status", status);
		obj.put("Summary", summary);
		obj.put("LastOccurrence", lastOccurrence);
		obj.put("tally", "5");
		obj.put("SourceIdentifier", "123");
		pbs.add(obj);
		JSONObject result = eventImport.eventImport(pbs.toString());
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);		
	}
}
