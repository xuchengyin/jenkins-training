package com.uinnova.test.step_definitions.testcase.dmv.wall;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.dmv.ci.QueryAppWall;
import com.uinnova.test.step_definitions.api.dmv.config.QueryAppWallOrderConfig;
import com.uinnova.test.step_definitions.api.dmv.config.SaveAppWallOrderConfig;
import com.uinnova.test.step_definitions.api.dmv.diagram.QueryDiagramInfoByAppRltCiCode;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * 编写时间:2018-04-18
 * 编写人:sunsl
 * 功能介绍:查询应用墙上的应用
 */
public class Scenario_wall {
	private JSONObject result;
	/*================Scenario: Wall_应用墙_查询应用墙上的应用==============*/
	@When("^查询应用墙上的应用$")
    public void queryAppWall(){
    	QueryAppWall queryAppWall = new QueryAppWall();
    	result = queryAppWall.queryAppWall();
    	assertTrue(result.getBoolean("success"));
    }
	
	@Then("^应用墙上的分类为\"(.*)\"属性为\"(.*)\"的CI被成功查询出来$")
	public void checkQueryAppWall(String classCode,String attrName){
		String sql = "SELECT ID,CI_CODE,CLASS_ID FROM cc_ci WHERE CLASS_ID = (SELECT ID from CC_CI_CLASS WHERE CLASS_CODE = '" + classCode + "' AND DATA_STATUS = 1 AND DOMAIN_ID = " + QaUtil.domain_id + ") AND DATA_STATUS= 1 AND DOMAIN_ID = " + QaUtil.domain_id + " ORDER BY ID";
		List list = JdbcUtil.executeQuery(sql);
		JSONArray data = result.getJSONArray("data");
		assertEquals(list.size(),data.length());
		String sql2 = "SELECT SV_0 FROM cc_ci_short_attr_0 WHERE DATA_STATUS = 1 AND DOMAIN_ID = " + QaUtil.domain_id + " AND ID IN ("
	                 +" SELECT ID FROM CC_CI_PRO_INDEX WHERE DOMAIN_ID = "+ QaUtil.domain_id + " AND CLASS_ID = (SELECT ID  FROM cc_ci_class WHERE CLASS_CODE = '"+ classCode +"' AND DATA_STATUS = 1  AND DOMAIN_ID = "+ QaUtil.domain_id + ")"
	                 +"AND ATTR_ID = (SELECT ID FROM cc_ci_attr_def WHERE CLASS_ID =(SELECT ID FROM cc_ci_class WHERE CLASS_CODE = '" + classCode + "' AND DATA_STATUS = 1 AND DOMAIN_ID = "+ QaUtil.domain_id+")"
		              +"AND PRO_NAME = '"+attrName+"' AND DATA_STATUS = 1 AND DOMAIN_ID = "+QaUtil.domain_id +")) ORDER BY ID";
		List list2 = JdbcUtil.executeQuery(sql2);
		
		if(data.length() > 0){
			for (int i = 0; i < data.length(); i ++){
				HashMap map = (HashMap)list.get(i);
				HashMap map2 = (HashMap)list2.get(i);
				JSONObject obj = (JSONObject)data.get(i);
				JSONArray attrs = (JSONArray)obj.getJSONArray("attrs");
				if(attrs.length()>0){
					for(int j = 1; j <attrs.length();j++){
						JSONObject attrObj = (JSONObject)attrs.get(i);
						assertEquals(attrName,attrObj.getString("key"));
						assertEquals(map2.get("SV_0"),attrObj.getString("value"));
					}
				}
				JSONObject ci = obj.getJSONObject("ci");
				assertEquals(map.get("ID").toString(),ci.getBigDecimal("id").toString());
				assertEquals(map.get("CI_CODE"),ci.getString("ciCode"));
				assertEquals(map.get("CLASS_ID").toString(),ci.getBigDecimal("classId").toString());
			}
		}
	}
	
	/*===============Scenario: Wall_应用墙_根据code查询组合视图===========*/
	@When("^根据CICODE为\"(.*)\"查询组合视图$")
	public void queryDiagramInfoByAppRltCiCode(String ciCode){
		QueryDiagramInfoByAppRltCiCode queryDiagramInfoByAppRltCiCode = new QueryDiagramInfoByAppRltCiCode();
		result = queryDiagramInfoByAppRltCiCode.queryDiagramInfoByAppRltCiCode(ciCode);
		assertTrue(result.getBoolean("success"));
	}
	
	@Then("^根据CICODE为\"(.*)\"成功查询组合视图$")
	public void checkQueryDiagramInfoByAppRltCiCode(String ciCode){
		String sql = "SELECT ID FROM vc_diagram WHERE APP_RLT_CI_CODE = '" + ciCode + "' AND STATUS = 1 AND DATA_STATUS = 1 AND DIAGRAM_TYPE = 2 AND DOMAIN_ID = " + QaUtil.domain_id;
		List list = JdbcUtil.executeQuery(sql);
		
	}
	
	/*==========Scenario Outline: Wall_应用墙_保存应用墙排序信息=========*/
	@When("^给应用墙保存\"(.*)\"排序信息$")
	public void saveAppWallOrderConfig(String param){
		SaveAppWallOrderConfig saveAppWallOrderConfig = new SaveAppWallOrderConfig();
		result = saveAppWallOrderConfig.saveAppWallOrderConfig(param);
		assertTrue(result.getBoolean("success"));
	}
	
	@Then("^应用墙成功保存\"(.*)\"的排序信息$")
	public void checkSaveAppWallOrderConfig(String param){
		QueryAppWallOrderConfig  queryAppWallOrderConfig = new QueryAppWallOrderConfig();
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result = queryAppWallOrderConfig.queryAppWallOrderConfig();
		String data = result.getString("data");
		assertEquals(param,data);
	}
}
