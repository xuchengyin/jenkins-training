package com.uinnova.test.step_definitions.testcase.base.navigationbar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.base.navigationbar.Projects;

import cucumber.api.java.en.When;

/**
 * @author wsl
 * 读取导航栏
 *
 */
public class Scenario_navigationbar {

	@When("读取导航栏信息")
	public void projects(){
		Projects  projects= new Projects();
		JSONObject result = projects.projects();
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
		JSONArray projectList = result.getJSONArray("data");
		assertNotNull(projectList);
		assertTrue(projectList.length()>1);;
	}
}
