package com.uinnova.test.step_definitions.testcase.dmv.workbench;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.dmv.diagram.QueryWorkbenchDiagramInfo;
import com.uinnova.test.step_definitions.api.dmv.diagram.ShareDiagram;
import com.uinnova.test.step_definitions.utils.base.UserUtil;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

import cucumber.api.Delimiter;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * 编写时间:2018-04-04
 * 编写人:sunsl
 * 功能介绍:DMV_工作台_搜索测试用例类
 */
public class Scenario_workbench {
	private JSONObject result;
   /*==============Scenario: DMV_工作台_搜索全部============*/
   @When("^搜索登录名为\"(.*)\"全部视图$")
   public void queryWorkbenchDiagramInfoByAll(String userName){
	  QueryWorkbenchDiagramInfo  queryWorkbenchDiagramInfo = new QueryWorkbenchDiagramInfo();
	  result = queryWorkbenchDiagramInfo.queryWorkbenchDiagramInfo(24, 0);
	  //System.out.println("result=======" +result);
	  assertTrue(result.getBoolean("success"));
   }
   
  @Then("^成功搜索登录名为\"(.*)\"全部视图$")
  public void checkQueryWorkbenchDiagramInfoByAll(String userName){
	  UserUtil userUtil = new UserUtil();
	  String userId = userUtil.getUserId(userName);
	  BigDecimal userIdB = new BigDecimal(userId);
	  String sql = "SELECT ID FROM VC_DIAGRAM WHERE DATA_STATUS = 1 AND DOMAIN_ID ="+ QaUtil.domain_id + " AND STATUS =1 AND ( ID IN(SELECT DIAGRAM_ID FROM VC_DIAGRAM_GROUP WHERE DATA_STATUS = 1 AND DOMAIN_ID = " + QaUtil.domain_id + " AND GROUP_ID IN (SELECT GROUP_ID FROM VC_GROUP_USER WHERE USER_ID = " + userIdB  +" AND DOMAIN_ID = "+ QaUtil.domain_id +" AND DATA_STATUS = 1)) OR ID IN(SELECT DIAGRAM_ID FROM VC_DIAGRAM_ENSH WHERE USER_ID = " +userIdB +" AND DOMAIN_ID = " + QaUtil.domain_id +") or USER_ID = "+ userIdB + ")";
	  List list = JdbcUtil.executeQuery(sql);
	  JSONObject data = result.getJSONObject("data");
	  int totalRows = data.getInt("totalRows");
	  assertEquals(list.size(),totalRows);
  }
  
  /*============Scenario: DMV_工作台_搜索我创建的===========*/
  @When("^搜索登录名为\"(.*)\"创建的视图$")
  public void queryWorkbenchDiagramInfoByMyCreate(String userName){
	  QueryWorkbenchDiagramInfo  queryWorkbenchDiagramInfo = new QueryWorkbenchDiagramInfo();
	  result = queryWorkbenchDiagramInfo.queryWorkbenchDiagramInfo(100, 1);
	  assertTrue(result.getBoolean("success"));
  }
  
  @Then("^成功搜索登录名为\"(.*)\"创建的视图$")
  public void checkQueryWorkbenchDiagramInfoByMyCreate(String userName){
	  UserUtil userUtil = new UserUtil();
	  String userId = userUtil.getUserId(userName);
	  BigDecimal userIdB = new BigDecimal(userId);
	  String sql = "SELECT ID FROM VC_DIAGRAM WHERE STATUS = 1 AND DATA_STATUS = 1 AND DOMAIN_ID =" + QaUtil.domain_id + " AND USER_ID =" + userIdB;
	  List list = JdbcUtil.executeQuery(sql);
	  JSONObject data = result.getJSONObject("data");
	  int totalRows = data.getInt("totalRows");
	  assertEquals(list.size(),totalRows);
	  
  }
  
  /*============Scenario: DMV_工作台_搜索我协作的===========*/
  @When("^搜索登录名为\"(.*)\"协作的视图$")
  public void queryWorkbenchDiagramInfoByMyCollect(String userName){
	  QueryWorkbenchDiagramInfo  queryWorkbenchDiagramInfo = new QueryWorkbenchDiagramInfo();
	  result = queryWorkbenchDiagramInfo.queryWorkbenchDiagramInfo(100, 2);
	  assertTrue(result.getBoolean("success"));
  }
  
  @Then("^成功搜索登录名为\"(.*)\"协作的视图$")
  public void checkQueryWorkbenchDiagramInfoByMyCollect(String userName){
	  UserUtil userUtil = new UserUtil();
	  String userId = userUtil.getUserId(userName);
	  BigDecimal userIdB = new BigDecimal(userId);
	  String sql ="SELECT ID FROM VC_DIAGRAM WHERE DATA_STATUS = 1 AND DOMAIN_ID = "+ QaUtil.domain_id + " AND STATUS =1 AND ID IN (SELECT DIAGRAM_ID FROM VC_DIAGRAM_GROUP WHERE DATA_STATUS = 1 AND DOMAIN_ID = " + QaUtil.domain_id + " AND GROUP_ID IN(SELECT GROUP_ID FROM VC_GROUP_USER WHERE USER_ID =" + userIdB + " AND DOMAIN_ID = " +QaUtil.domain_id + " AND DATA_STATUS = 1))";
	  List list = JdbcUtil.executeQuery(sql);
	  JSONObject data = result.getJSONObject("data");
	  int totalRows = data.getInt("totalRows");
	  assertEquals(list.size(),totalRows);
  }
  
  /*===============Scenario: DMV_工作台_搜索我分享到广场的==========*/
  @And("^将多个视图\"(.*)\"发布到广场$")
  public void shareDiagram(@Delimiter(",") List<String> diagramList){
	  ShareDiagram sd = new ShareDiagram();
	  for(int i = 0; i<diagramList.size();i++){
		String name = diagramList.get(i);
	    result = sd.shareDiagram(name);
	    assertTrue(result.getBoolean("success"));
	  }
  }
  
  @When("^搜索登录名为\"(.*)\"分享到广场的视图$")
  public void queryWorkbenchDiagramInfoByMyShare(String userName){
	  QueryWorkbenchDiagramInfo  queryWorkbenchDiagramInfo = new QueryWorkbenchDiagramInfo();
	  result = queryWorkbenchDiagramInfo.queryWorkbenchDiagramInfo(100, 3);
	  assertTrue(result.getBoolean("success"));
  }
  
  @Then("^成功搜索登录名为\"(.*)\"分享到广场的视图$")
  public void checkQueryWorkbenchDiagramInfoByMyShare(String userName){
	  UserUtil userUtil = new UserUtil();
	  String userId = userUtil.getUserId(userName);
	  BigDecimal userIdB = new BigDecimal(userId);
	  String sql = "SELECT ID from VC_DIAGRAM where STATUS = 1 AND DATA_STATUS = 1 AND DOMAIN_ID = " + QaUtil.domain_id + " AND IS_OPEN = 1 AND USER_ID = " + userIdB;
      List list = JdbcUtil.executeQuery(sql);
      JSONObject data = result.getJSONObject("data");
	  int totalRows = data.getInt("totalRows");
	  assertEquals(list.size(),totalRows);
  }
  
  /*============Scenario: DMV_工作台_搜索我收藏的============*/
  @When("^搜索登录名为\"(.*)\"收藏的视图$")
  public void queryWorkbenchDiagramInfoByMyCollection(String userName){
	  QueryWorkbenchDiagramInfo  queryWorkbenchDiagramInfo = new QueryWorkbenchDiagramInfo();
	  result = queryWorkbenchDiagramInfo.queryWorkbenchDiagramInfo(100, 4);
	  assertTrue(result.getBoolean("success"));
  }
  
  @Then("^成功搜索登录名为\"(.*)\"收藏的视图$")
  public void checkQueryWorkbenchDiagramInfoByMyCollection(String userName){
	  UserUtil userUtil = new UserUtil();
	  String userId = userUtil.getUserId(userName);
	  BigDecimal userIdB = new BigDecimal(userId);
	  String sql = "SELECT ID FROM VC_DIAGRAM WHERE DATA_STATUS = 1 AND DOMAIN_ID = " + QaUtil.domain_id +" AND STATUS =1 AND ID IN(SELECT DIAGRAM_ID FROM VC_DIAGRAM_ENSH WHERE USER_ID = "+ userIdB +" AND DOMAIN_ID =" + QaUtil.domain_id +")";
	  List list = JdbcUtil.executeQuery(sql);
	  JSONObject data = result.getJSONObject("data");
	  int totalRows = data.getInt("totalRows");
	  assertEquals(list.size(),totalRows);
  }
  
}
