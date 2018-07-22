package com.uinnova.test.step_definitions.testcase.dmv.image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.dmv.diagram.RemoveDiagramGroupByDiagramID;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * 编写时间:2017-12-23
 * 编写人:sunsl
 * 功能介绍:新建视图分享视图测试用例
 */
public class Scenario_image_share {
   /*===============Share_分享视图取消分享视图=============*/
	@When("^将名称为\"(.*)\"的视图移出小组$")
	public void removeDiagramGroupByDiagramID(String diagramName){
		RemoveDiagramGroupByDiagramID  rd = new RemoveDiagramGroupByDiagramID();
		JSONObject result = rd.removeDiagramGroupByDiagramID(diagramName);
		assertTrue(result.getBoolean("success"));		
	}
	
	@Then("^名称为\"(.*)\"的 视图成功移出小组$")
	public void checkRemoveDiagramGroupByDiagramID(String diagramName){
		String sql = "SELECT ID FROM vc_diagram_group Where "
				+ "DIAGRAM_ID = (SELECT ID FROM vc_diagram WHERE NAME ='"+ diagramName + "' AND STATUS = 1 AND DATA_STATUS = 1 and DOMAIN_ID = "+ QaUtil.domain_id+")";
		List diagramList = JdbcUtil.executeQuery(sql);
		assertEquals(diagramList.size(), 0);
	}
}
