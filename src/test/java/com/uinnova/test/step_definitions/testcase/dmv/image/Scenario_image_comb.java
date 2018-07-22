package com.uinnova.test.step_definitions.testcase.dmv.image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.dmv.diagram.QueryWorkabilityDiagram;
import com.uinnova.test.step_definitions.api.dmv.diagram.RemoveDirByIds;
import com.uinnova.test.step_definitions.api.dmv.diagram.SaveOrUpdateDiagram;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;
import com.uinnova.test.step_definitions.utils.dmv.CombUtil;

import cucumber.api.Delimiter;
import cucumber.api.java.After;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
* 编写时间:2018-01-09
* 编写人:sunsl
* 功能介绍:绘图_组合绘图测试用例类
*/
public class Scenario_image_comb {
	JSONObject result;
	private List<String> diagramNamecombList = new ArrayList<String>(); //用于记录一共新建了多少视图， 用户After方法清理数据
	@After("@delDiagramComb")
	public void delDiagram(){
		if (!diagramNamecombList.isEmpty()){
			for (int i=0; i<diagramNamecombList.size(); i++){
				String diagramName = diagramNamecombList.get(i);
				RemoveDirByIds rd = new RemoveDirByIds();
				JSONObject result = rd.removeDirByIds(diagramName);
				assertTrue(result.getBoolean("success"));
				diagramNamecombList.remove(diagramName);
				i--;
			}
		}
	}
    /*========================Scenario Outline: Comb_组合视图======================*/
	@When("^创建多个视图名称为\"(.*)\",视图描述为\"(.*)\"的视图$")
	public void createDiagram(@Delimiter(",") List<String> diagramNameList,String diagramDesc){
		for(int i = 0; i< diagramNameList.size(); i++){
			String diagramName = diagramNameList.get(i);
			SaveOrUpdateDiagram su = new SaveOrUpdateDiagram();
			result = su.createNewDiagram(diagramName,diagramDesc,"0");
			assertTrue(result.getBoolean("success"));
			diagramNamecombList.add(diagramName);
		}
	}
	
	@Then("^系统中存在多个视图名称为\"(.*)\",视图描述为\"(.*)\"的视图$")
	public void checkCreateDiagram(@Delimiter(",")List<String> diagramNameList,String diagramDesc){
		for(int i = 0; i < diagramNameList.size(); i ++){
			String diagramName = diagramNameList.get(i);
			String sql = "SELECT ID FROM vc_diagram where NAME ='" + diagramName + "' AND DIAGRAM_DESC ='" + diagramDesc +"' AND DIAGRAM_TYPE = 1 AND STATUS = 1 AND DATA_STATUS = 1 AND DOMAIN_ID =" + QaUtil.domain_id;
			List list = JdbcUtil.executeQuery(sql);
			assertEquals(list.size(),1);
		}
	}
	
	@When("^根据多个视图\"(.*)\"创建名称为\"(.*)\",描述为\"(.*)\",组合行为\"(.*)\",组合列为\"(.*)\"的组合视图$")
	public void createComb(@Delimiter(",")List<String>diagramNameList,String name,String diagramDesc,String combRows,String combCols){
		CombUtil combUtil = new CombUtil();
		result = combUtil.createComb(diagramNameList,name,diagramDesc,combRows,combCols);
		diagramNamecombList.add(name);
		assertTrue(result.getBoolean("success"));
	}
	
	@Then("^成功创建名称为\"(.*)\",描述为\"(.*)\",组合行为\"(.*)\",组合列为\"(.*)\"的组合视图$")
	public void checkCreateComb(String name,String diagramDesc,String combRows,String combCols){
		String sql = "SELECT ID FROM vc_diagram WHERE NAME = '" + name +"' AND DIAGRAM_DESC = '" + diagramDesc + "' AND DIAGRAM_TYPE =2 AND COMB_ROWS = " + combRows + " AND COMB_COLS=" + combCols + " AND DATA_STATUS = 1 AND DOMAIN_ID = " + QaUtil.domain_id;
	    List list = JdbcUtil.executeQuery(sql);
	    assertEquals(list.size(),1);
	}
	
	@When("^应用墙中根据多个视图\"(.*)\"创建名称为\"(.*)\",描述为\"(.*)\",组合行为\"(.*)\",组合列为\"(.*)\",关联APP为\"(.*)\"的组合视图$")
	public void createComb2(@Delimiter(",")List<String>diagramNameList,String name,String diagramDesc,String combRows,String combCols,String ciCode){
		CombUtil combUtil = new CombUtil();
		result = combUtil.createComb(diagramNameList,name,diagramDesc,combRows,combCols,ciCode);
		diagramNamecombList.add(name);
		assertTrue(result.getBoolean("success"));
	}
	
	@Then("^应用墙中成功创建名称为\"(.*)\",描述为\"(.*)\",组合行为\"(.*)\",组合列为\"(.*)\",关联APP为\"(.*)\"的组合视图$")
	public void checkCreateComb2(String name,String diagramDesc,String combRows,String combCols,String ciCode){
		String sql = "SELECT ID FROM vc_diagram WHERE NAME = '" + name +"' AND DIAGRAM_DESC = '" + diagramDesc + "' AND DIAGRAM_TYPE =2 AND COMB_ROWS = " + combRows + " AND COMB_COLS=" + combCols + " AND DATA_STATUS = 1 AND DOMAIN_ID = " + QaUtil.domain_id + " AND APP_RLT_CI_CODE ='" + ciCode + "'";
	    List list = JdbcUtil.executeQuery(sql);
	    assertEquals(list.size(),1);
	}
	
		
	@When("^删除多个视图\"(.*)\"$")
	public void removeDiagram(@Delimiter(",")List<String>diagramNameList){
		RemoveDirByIds removeDirByIds = new RemoveDirByIds();
		for(int i = 0; i < diagramNameList.size();i++){
			String diagramName = diagramNameList.get(i);
			result = removeDirByIds.removeDirByIds(diagramName);
			assertTrue(result.getBoolean("success"));
			diagramNamecombList.remove(diagramName);
		}
	}
	
	@Then("^成功删除多个视图\"(.*)\"$")
	public void checkRemoveDiagram(@Delimiter(",")List<String>diagramNameList){
		for(int i =0; i < diagramNameList.size(); i++){
			String diagramName = diagramNameList.get(i);
			String sql = "SELECT ID FROM vc_diagram WHERE NAME = '" + diagramName +"' AND STATUS = 1 AND DIAGRAM_TYPE = 1 AND DATA_STATUS=1 AND DOMAIN_ID = " + QaUtil.domain_id;
			List list = JdbcUtil.executeQuery(sql);
			assertEquals(list.size(),0);
		}
	}
	/*============== Scenario Outline: Comb_搜索视图============*/
	@When("^在组合视图中搜索关键字为\"(.*)\"的单个视图$")
	public void queryWorkabilityDiagram(String searchKey){
		QueryWorkabilityDiagram  queryWorkabilityDiagram = new QueryWorkabilityDiagram();
		result = queryWorkabilityDiagram.queryWorkabilityDiagram(1, searchKey);
		assertTrue(result.getBoolean("success"));
	}
	
	@Then("^在组合视图中成功搜索关键字为\"(.*)\"的单个视图$")
	public void checkQueryWorkabilityDiagram(String searchKey){
		String sql = "SELECT ID FROM vc_diagram WHERE name like '%" + searchKey + "%' AND STATUS =1 AND DIAGRAM_TYPE = 1 AND DATA_STATUS = 1 AND DOMAIN_ID = " + QaUtil.domain_id;
		List list = JdbcUtil.executeQuery(sql);
		assertEquals(list.size(),result.getJSONObject("data").getInt("totalRows"));
	}
}
