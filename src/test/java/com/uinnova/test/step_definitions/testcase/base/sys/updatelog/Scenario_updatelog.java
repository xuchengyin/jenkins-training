package com.uinnova.test.step_definitions.testcase.base.sys.updatelog;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.base.sys.updatelog.QueryUpdateLogText;
import com.uinnova.test.step_definitions.api.base.sys.updatelog.QueryUpdateLogTree;

import cucumber.api.java.en.When;


/**
 * 更新日志
 * @author wsl
 *
 */
public class Scenario_updatelog {
	JSONObject updateLogTree;
	
 	
	@When("^获取日志菜单树$")
	public void queryUpdateLogTree(){
		QueryUpdateLogTree queryUpdateLogTree = new QueryUpdateLogTree();
		updateLogTree = queryUpdateLogTree.queryUpdateLogTree();
		assertTrue(updateLogTree.has("data"));
		JSONArray data = updateLogTree.getJSONArray("data");
		assertNotNull(data);
		assertTrue(data.length()>0);
		for (int i=0; i<data.length(); i++){
			JSONObject dataObj = data.getJSONObject(i);
			assertTrue(dataObj.has("name"));
			assertNotNull(dataObj.getString("name"));
			assertTrue(dataObj.has("modules"));
			JSONArray modules = dataObj.getJSONArray("modules");
			assertNotNull(modules);
			assertTrue(modules.length()>0);
			assertTrue(dataObj.has("date"));
			assertNotNull(dataObj.getString("date"));
			assertTrue(dataObj.has("version"));
			assertNotNull(dataObj.getString("version"));
		}
	}
	
	
	@When("^获取日志正文$")
	public void queryUpdateLogText(){
		QueryUpdateLogText queryUpdateLogText = new QueryUpdateLogText();
		JSONArray treeDdata = updateLogTree.getJSONArray("data");
		for (int i=0; i<treeDdata.length(); i++){
			JSONObject dataObj = treeDdata.getJSONObject(i);
			String name = dataObj.getString("name");
			JSONArray modules = dataObj.getJSONArray("modules");
			for (int m=0; m<modules.length(); m++){
				JSONObject updateLogText = queryUpdateLogText.queryUpdateLogText(name, modules.getString(m));
				assertTrue(updateLogText.has("data"));
				JSONObject  textData = updateLogText.getJSONObject("data");
				assertNotNull(textData);
				assertTrue(textData.has("tit"));
				assertNotNull(textData.getString("tit"));
				assertTrue(textData.has("date"));
				assertNotNull(textData.getString("date"));
				assertTrue(textData.has("contents"));
				JSONArray contents = textData.getJSONArray("contents");
				assertNotNull(contents);
				for (int c=0; c<contents.length(); c++){
					JSONObject conObj = contents.getJSONObject(c);
					assertTrue(conObj.has("tit1"));
					assertNotNull(conObj.getString("tit1"));
					assertTrue(conObj.has("paragraphs"));
					JSONArray paragraphs = conObj.getJSONArray("paragraphs");
					assertNotNull(paragraphs);
				}
			}
		}
		
	}
	
	
}
