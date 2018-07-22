package com.uinnova.test.step_definitions.testcase.cmv.sys;

import org.json.JSONObject;

import static org.junit.Assert.assertEquals;

import com.uinnova.test.step_definitions.api.cmv.sys.fun.GetSysIntegrationUrl;

import cucumber.api.java.en.When;

public class Scenario_GetSysIntegrationUrl {

	@When("集成页面外部地址为:(.*)")
	public void getSysIntegrationUrl(String url){
		GetSysIntegrationUrl gsiu = new GetSysIntegrationUrl();
		JSONObject result = gsiu.getSysIntegrationUrl();
		assertEquals("对比集成页面外部地址 :", result.getString("data"), url);
	}
}
