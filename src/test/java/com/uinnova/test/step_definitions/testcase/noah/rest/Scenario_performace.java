package com.uinnova.test.step_definitions.testcase.noah.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.cmv.monitor.performance.GetPerformanceList;
import com.uinnova.test.step_definitions.api.noah.rest.PerformanceImport;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


/**
 * @author wsl
 * NOAH 推送性能告警
 */
public class Scenario_performace {
	
	@When("给CI为\"(.*)\"推送指标\"(.*)\",描述为\"(.*)\",值为\"(.*)\"")
	public void performanceImport(String ciName, String kpiName, String kpiDesc, String kpiValue){
		PerformanceImport performanceImport = new PerformanceImport();
		JSONObject result = performanceImport.performanceImport(ciName, kpiName, kpiDesc, kpiValue);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
	}
	
}
