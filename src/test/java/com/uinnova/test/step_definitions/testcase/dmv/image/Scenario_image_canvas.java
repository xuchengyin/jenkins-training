package com.uinnova.test.step_definitions.testcase.dmv.image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.dmv.ci.QueryPageByAllIndex;
import com.uinnova.test.step_definitions.api.dmv.ci.QueryPageByIndex;
import com.uinnova.test.step_definitions.api.dmv.ci.QueryRelatedCiPage;
import com.uinnova.test.step_definitions.api.dmv.ciRlt.QueryRltInfoByRelatedCiCode;
import com.uinnova.test.step_definitions.api.dmv.diagram.GetConfigInfo;
import com.uinnova.test.step_definitions.api.dmv.diagram.QueryDiagramInfoById;
import com.uinnova.test.step_definitions.api.dmv.diagram.UpdateDiagramContent;
import com.uinnova.test.step_definitions.api.dmv.diagramVersion.RemoveDiagramVersionById;
import com.uinnova.test.step_definitions.api.dmv.diagramVersion.UpdateDiagramVersionDesc;
import com.uinnova.test.step_definitions.api.dmv.image.QueryImageInfoListByCdt;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.base.CiUtil;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;
import com.uinnova.test.step_definitions.utils.common.TxtUtil;
import com.uinnova.test.step_definitions.utils.dmv.DiagramUtil;
import com.uinnova.test.step_definitions.utils.dmv.MyUtil;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 *编写时间:2017-12-25
 *编写人:sunsl 
 *功能介绍:绘图-新建视图-ci画图：画布功能类
 */
public class Scenario_image_canvas {
  JSONObject result;
  /*==============Scenario Outline: Image_画布左侧菜单_搜索图标================*/
  @When("^根据图标关键字\"(.*)\"来搜索图标$")
  public void queryImageInfoListByCdt(String searchKey){
	  QueryImageInfoListByCdt  qi = new QueryImageInfoListByCdt();
	  result = qi.queryImageInfoListByCdt(searchKey);
	  assertTrue(result.getBoolean("success"));
  }
  
  @Then("^返回所有包含\"(.*)\"关键字的图片$")
  public void checkQueryImageInfoListByCdt(String searchKey){
	  String sql = "SELECT IMG_NAME FROM cc_image WHERE IMG_NAME like '%" + searchKey+"%'AND DIR_ID in (1, 10001, 10111,11003) AND DATA_STATUS =1  and DOMAIN_ID = "+ QaUtil.domain_id+" order by ID";
	  List searhList = JdbcUtil.executeQuery(sql);
	  JSONArray data = result.getJSONArray("data");
	  if(searhList.size()==data.length()){
		  for(int i = 0; i< data.length(); i ++){
			  HashMap imageHashMap = (HashMap)searhList.get(i);
			  JSONObject obj = (JSONObject)data.get(i);
			  JSONArray imageInfos = obj.getJSONArray("imageInfos");
			  for(int j = 0; j < imageInfos.length(); j++){
				  JSONObject obj2 = (JSONObject)imageInfos.get(j);
				  JSONObject image = obj2.getJSONObject("image");
				  String imageName = image.getString("imgName");
				  assertEquals(imageHashMap.get("IMG_NAME"),imageName);
			  }
			  
		  }
	  }
  }
  
  /*========================Scenario Outline: Image_画布右侧菜单_数据开关=================*/
  @When("^为视图\"(.*)\"设置数据开关为\"(.*)\"$")
  public void updateDiagramContent(String diagramName,String dataUpType){
	  UpdateDiagramContent up = new UpdateDiagramContent();
	  result = up.updateDiagramContentDataUpType(diagramName,dataUpType);
	  assertTrue(result.getBoolean("success"));
  }
  
  @Then("^为视图\"(.*)\"的CI\"(.*)\"成功设置数据开关\"(.*)\"$")
  public void checkUpdateDiagramContent(String diagramName,String ciCode,String dataUpType){
	  CiUtil ciUtil = new CiUtil();
	  BigDecimal ciId = ciUtil.getCiId(ciCode);
	  QueryDiagramInfoById queryDiagramInfoById  = new QueryDiagramInfoById();
	  result = queryDiagramInfoById.queryDiagramInfoById(diagramName, true);
	  JSONObject dataDiagram = result.getJSONObject("data");
	  JSONArray diagramEles = dataDiagram.getJSONArray("diagramEles");
	  for(int i = 0; i < diagramEles.length(); i ++){
		  JSONObject obj = (JSONObject)diagramEles.get(i);
		  if (obj.getBigDecimal("eleId")!=ciId){
			  assertTrue(true);
		  }
	  }
	  String sql ="SELECT DATA_UP_TYPE FROM vc_diagram Where NAME = '" + diagramName + "' AND USER_ID = "+QaUtil.user_id+" AND STATUS =1 AND DATA_STATUS = 1 and DOMAIN_ID = "+ QaUtil.domain_id;
	  List dataUpTypeList = JdbcUtil.executeQuery(sql);
	  HashMap datUpTypeHashMap = (HashMap)dataUpTypeList.get(0);
	  JSONObject data = result.getJSONObject("data");
	  JSONObject diagram = data.getJSONObject("diagram");
	  assertEquals(((BigDecimal)datUpTypeHashMap.get("DATA_UP_TYPE")).intValue(),diagram.getInt("dataUpType"));
  }
  
  /*=============Scenario: Image_画布左侧菜单_去除宝马形状,写入配置文件=======================*/
  
  @When("^读取配置文件$")
  public void getConfigInfo(){
	  GetConfigInfo getConfigInfo = new GetConfigInfo();
	  result = getConfigInfo.getConfigInfo();
	  assertTrue(result.getBoolean("success"));
  }
  
  @Then("^成功读取配置文件$")
  public void checkGetConfigInfo(){
	  JSONObject data = result.getJSONObject("data");
	  assertEquals(0,data.getInt("isBmwProject"));
  }
  
  
  /*==================Scenario Outline: Image_画布右侧菜单_配置扫描======================*/
  @When("^为关键字\"(.*)\"配置扫描$")
  public void queryPageByAllIndex(String words){
	  QueryPageByAllIndex  queryPageByAllIndex = new QueryPageByAllIndex();
	  result = queryPageByAllIndex.queryPageByAllIndex(words);
	  assertTrue(result.getBoolean("success"));
  }
  
  @Then("为关键字\"(.*)\"成功配置扫描$")
  public void checkQueryPageByAllIndex(String words){
	  String sql = "";
	  if(words.matches("^[a-zA-Z]*")){
		  words = words.toUpperCase();
		   sql ="SELECT * FROM cc_ci Where Data_Status = 1  and DOMAIN_ID = "+ QaUtil.domain_id+" AND ID IN "
			  		+ "(SELECT DISTINCT ID FROM cc_ci_pro_index where  idx_0 like '" + words+"' and DOMAIN_ID = "+ QaUtil.domain_id+")";
	  } else if (words.contains(",")){
		  String [] str = words.split(",");
		  String tempStr ="";
		  for(int i = 0;i <str.length; i++){
			  if (i ==0){
			    tempStr = " AND idx_0 like '" + str[i] +"'";
			  }else{
				  tempStr = tempStr+" AND idx_0 like '" + str[i] +"'";
			  }
		  }
		   sql ="SELECT * FROM cc_ci Where Data_Status = 1 AND and DOMAIN_ID = "+ QaUtil.domain_id+" AND ID IN "
		  		     + "(SELECT DISTINCT ID FROM cc_ci_pro_index where  DOMAIN_ID = "+ QaUtil.domain_id  +tempStr +")";
	  }else{
		  sql ="SELECT * FROM cc_ci Where Data_Status = 1  and DOMAIN_ID = "+ QaUtil.domain_id+" AND ID IN "
			  		+ "(SELECT DISTINCT ID FROM cc_ci_pro_index where  idx_0 like '" + words+"' and DOMAIN_ID = "+ QaUtil.domain_id+")";
	  }
	  
	  List qpList = JdbcUtil.executeQuery(sql);
      JSONObject data = result.getJSONObject("data");
      Integer totalRows = data.getInt("totalRows");
      if (qpList.size() == totalRows){
    	  assertTrue(true);
      }
  }

  /*====================Scenario Outline: Image_画布右侧菜单_标签=====================*/
  @Then("^成功新建标签\"(.*)\"$")
  public void checkCreateTag(String tagName){
	  String sql = "SELECT ID FROM vc_tag Where TAG_NAME = '" + tagName + "' AND DATA_STATUS =1 and DOMAIN_ID = "+ QaUtil.domain_id;
	  List tagList = JdbcUtil.executeQuery(sql);
	  assertEquals(tagList.size(),1);
  }
  
  /*===============Scenario Outline: Image_画布左侧菜单_CI配置查询==================*/
  @When("^根据CI关键字\"(.*)\"来搜索CI$")
  public void queryPageByIndex(String searchKey){
	  QueryPageByIndex  queryPageByIndex = new QueryPageByIndex();
	  result = queryPageByIndex.queryPageByIndex(searchKey);
	  assertTrue(result.getBoolean("success"));
  }
  
   @Then("^根据CI关键字\"(.*)\"成功来搜索CI$")
   public void checkQueryPageByIndex(String searchKey){
	   //String sql = "Select cc_ci.ID from cc_ci,cc_ci_class where cc_ci.CI_CODE like '%" + searchKey +"%' and cc_ci.CLASS_ID = cc_ci_class.ID and cc_ci_class.DIR_ID !=1 and cc_ci.DATA_STATUS = 1 and cc_ci.DOMAIN_ID = " + QaUtil.domain_id;
	   String sql = "SELECT COUNT(1) AS CNT FROM cc_ci WHERE DATA_STATUS =1 AND DOMAIN_ID = "+ QaUtil.domain_id +" AND ID IN (SELECT DISTINCT ID FROM cc_ci_pro_index WHERE DOMAIN_ID = "+QaUtil.domain_id+" AND IDX_0 LIKE '%" +searchKey + "%')";
	   List list = JdbcUtil.executeQuery(sql);
	   Integer count = 0;
	   if(list != null && list.size() >0){
		   HashMap map = (HashMap)list.get(0);
		   count = result.getJSONObject("data").getInt("totalCiCount");
		   assertEquals(map.get("CNT").toString(),count.toString());
	   }else{
		   fail();
	   }
	  
   }
   
   /*==================Scenario Outline: Image_新建视图新建版本,更新版本,删除版本==============*/
   @When("^将视图\"(.*)\"的版本\"(.*)\"更新成\"(.*)\"版本号更新为\"(.*)\"$")
   public void updateDiagramVersionDesc(String diagramName,String versionDesc,String updateDesc,String updateVersionNo){
	   if(versionDesc.indexOf(".") > 0){
		   String filePath = Scenario_image_canvas.class.getResource("/").getPath() + "testData/dmv/version/" + versionDesc;
		   versionDesc = (new TxtUtil()).readTxt(filePath);
	   }
	   UpdateDiagramVersionDesc updateDiagramVersionDesc = new UpdateDiagramVersionDesc();
	   result = updateDiagramVersionDesc.updateDiagramVersionDesc(diagramName, versionDesc, updateDesc,updateVersionNo);
	   assertTrue(result.getBoolean("success"));
   }
   
   @Then("^成功将视图\"(.*)\"的版本\"(.*)\"更新成\"(.*)\"版本号更新为\"(.*)\"$")
   public void checkUpdateDiagramVersionDesc(String diagramName,String versionDesc,String updateDesc,String updateVersionNo){	   
	   DiagramUtil  diagramUtil = new DiagramUtil();
	   BigDecimal diagramId = diagramUtil.getDiagramIdByName(diagramName);
	   String sql = "SELECT ID,VERSION_NO FROM vc_diagram_version where DIAGRAM_ID = " + diagramId + " AND VERSION_DESC ='" + updateDesc + "' AND STATUS = 1 AND DATA_STATUS = 1";
	   List list = JdbcUtil.executeQuery(sql);
	   assertEquals(1,list.size());	
	   HashMap map = (HashMap)list.get(0);
	   assertEquals(updateVersionNo,map.get("VERSION_NO"));
   }
   
   @When("^删除视图11\"(.*)\"的版本\"(.*)\"$")
   public void removeDiagramVersionById(String diagramName,String versionDesc){
	   RemoveDiagramVersionById  removeDiagramVersionById = new RemoveDiagramVersionById();
	   result = removeDiagramVersionById.removeDiagramVersionById(diagramName, versionDesc);
	   assertTrue(result.getBoolean("success"));
   }
   
   @Then("^成功删除视图\"(.*)\"的版本\"(.*)\"$")
   public void checkRemoveDiagramVersionById(String diagramName,String versionDesc){
	   DiagramUtil diagramUtil = new DiagramUtil();
	   BigDecimal versionId = diagramUtil.getVersionIdByVersionDesc(diagramName, versionDesc);
	   assertEquals(0,versionId.intValue());
   }
   
   /*=====================Scenario: Image_画布右侧菜单_历史=================*/
   @When("^给视图\"(.*)\"创建了\"(.*)\"的历史记录$")
   public void saveDiagramVersion(String diagramName, String upRelation){
	   MyUtil myutil = new MyUtil();
	   result = myutil.saveDiagramVersion2(diagramName,upRelation);
	   assertTrue(result.getBoolean("success"));
   }
   
   @Then("^成功给视图\"(.*)\"创建了\"(.*)\"的历史记录$")
   public void checkSaveDiagramVersion(String diagramName,String upRelation){
	   DiagramUtil diagramUtil = new DiagramUtil();
	   BigDecimal diagramId = diagramUtil.getDiagramIdByName(diagramName);
	   String sql = "SELECT ID FROM vc_diagram_version WHERE DIAGRAM_ID = " + diagramId + " AND VERSION_DESC ='" + upRelation + "' AND DATA_STATUS = 1 AND DOMAIN_ID = " + QaUtil.domain_id;
	   List list = JdbcUtil.executeQuery(sql);
	   assertEquals(1,list.size());
   }
   
   /*=================Scenario: Image_画布左侧菜单_查询CI=================*/
   @When("^查询CICODE为\"(.*)\"的CI$")
   public void queryRelatedCiPage(String ciCode){
	   QueryRelatedCiPage queryRelatedCiPage = new QueryRelatedCiPage();
	   result = queryRelatedCiPage.queryRelatedCiPage(ciCode);
	   assertTrue(result.getBoolean("success"));
   }
   
   @Then("^正确查出分类为\"(.*)\"CICODE为\"(.*)\"的CI$")
   public void checkQueryRelatedCiPage(String className,String ciCode){
	   CiClassUtil ciClassUtil = new CiClassUtil();
	   BigDecimal classId = ciClassUtil.getClassIdByClassName(className, new BigDecimal(1));
	   
	   String sql ="SELECT ID FROM cc_ci_pro_index WHERE IDX_0 = '" +ciCode+ "' AND DOMAIN_ID = " + QaUtil.domain_id +" AND CLASS_ID = " + classId;
	   List list = JdbcUtil.executeQuery(sql);
	   JSONObject data = result.getJSONObject("data");
	   int totalRows = data.getInt("totalRows");
	   assertEquals(list.size(),totalRows);	
	   String ciSql = "SELECT ID,CI_CODE,CLASS_ID from cc_ci where CLASS_ID = " + classId +" AND DATA_STATUS = 1 AND DOMAIN_ID = " + QaUtil.domain_id + " order by ID";
	   List ciList = JdbcUtil.executeQuery(ciSql);
	   JSONArray dataArray = data.getJSONArray("data");
	   HashMap ciMap = new HashMap();
	   for(int i = 0; i < ciList.size(); i++){
		   HashMap map = (HashMap)ciList.get(i);
		   ciMap.put(map.get("ID").toString(), map);
	   }
	   for(int i = 0; i < dataArray.length(); i ++){
		   JSONObject obj = dataArray.getJSONObject(i);
		   JSONObject ci = obj.getJSONObject("ci");
		   BigDecimal id = ci.getBigDecimal("id");
		   HashMap map = (HashMap)ciMap.get(id.toString());
		   assertEquals(ci.getBigDecimal("id"),map.get("ID"));
		   assertEquals(ci.getString("ciCode"),map.get("CI_CODE"));
		   assertEquals(ci.getBigDecimal("classId"),map.get("CLASS_ID"));
	   }
   }
   
   /*====Scenario Outline: Image_画布左侧菜单_查询路由关系====*/
   @When("^查询CICODE为\"(.*)\"的路由关系$")
   public void queryRltInfoByRelatedCiCode(String ciCode){
	   QueryRltInfoByRelatedCiCode  qrbc = new QueryRltInfoByRelatedCiCode();
	   result = qrbc.queryRltInfoByRelatedCiCode(ciCode);
	   assertTrue(result.getBoolean("success"));
   }
   
   @Then("正确查出分类为\"(.*)\"CICODE为\"(.*)\"的路由关系$")
   public void checkQueryRltInfoByRelatedCiCode(String className,String ciCode){
	   CiClassUtil ciClassUtil = new CiClassUtil();
	   BigDecimal classId = ciClassUtil.getClassIdByClassName(className, new BigDecimal(1));
	   String sql ="SELECT ID FROM cc_ci_pro_index WHERE IDX_0 = '" +ciCode+ "' AND DOMAIN_ID = " + QaUtil.domain_id +" AND CLASS_ID = " + classId;
	   List list = JdbcUtil.executeQuery(sql);
	   JSONArray data = result.getJSONArray("data");
	  // int totalRows = data.getInt("totalRows");
	   assertEquals(list.size(),data.length());	
   }
}
