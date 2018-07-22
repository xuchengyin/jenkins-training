package com.uinnova.test.step_definitions.testcase.dmv.image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.dmv.diagram.UpdateDiagramContent;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.base.RltClassUtil;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;
import com.uinnova.test.step_definitions.utils.dmv.DiagramUtil;

import cucumber.api.java.After;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


/**
 * 编写时间:2017-12-25
 * 编写人:sunsl
 * 功能介绍:绘图-新建视图-ci画图：关系右键菜单测试用例
 */
public class Scenario_image_rltClass extends Scenario_image_Base{

	/*=================Scenario: Image_关系右键菜单_创建关系================*/
	/******************************右键连线-创建关系****************/
	@When("^在视图\"(.*)\"中\"(.*)\"到\"(.*)\"创建关系\"(.*)\"$")
	public void rltCreateRlt(String diagramName, String sourceCiCode, String targetCiCode, String newRltCls) throws DocumentException{
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		UpdateDiagramContent ud = new UpdateDiagramContent();
		JSONObject result = ud.updateDiagramContentRltCreateRlt(diagramName, sourceCiCode, targetCiCode, newRltCls);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
	}

	@Then("^在视图\"(.*)\"中\"(.*)\"到\"(.*)\"创建关系\"(.*)\"成功$")
	public void checkRltCreateRlt(String diagramName, String sourceCiCode, String targetCiCode,  String newRltCls) throws DocumentException{
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		RltClassUtil rltClassUtil = new RltClassUtil();
		
		BigDecimal rltClassId = rltClassUtil.getRltClassId(newRltCls);
		String sql = "SELECT ID, CLASS_ID,SOURCE_CI_CODE,TARGET_CI_CODE FROM cc_ci_rlt WHERE CLASS_ID = " 
				+ rltClassId + " AND SOURCE_CI_CODE='" + sourceCiCode + "' AND TARGET_CI_CODE='" + targetCiCode + "' AND DATA_STATUS = 1 and DOMAIN_ID="+QaUtil.domain_id;
		List rltList = JdbcUtil.executeQuery(sql);
		assertEquals(rltList.size(),  1);
		Map map = (Map) rltList.get(0);
		BigDecimal rltId = (BigDecimal) map.get("ID");
		
		JSONObject result = getDiagramInfoByName(diagramName);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
		JSONObject oldParam = result.getJSONObject("data");
		//检查xml
		String xml = oldParam.getString("xml");
		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement();            
		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); 
		// 遍历UserObject节点
		//取容器
		String sourceId = "";
		String targetId = "";
		boolean hasRlt = false;
		while (userObjectIters.hasNext()) {
			Element userObjectEle= (Element) userObjectIters.next();
			String tempCiCode = userObjectEle.attributeValue("code");
			if (tempCiCode!=null && !tempCiCode.isEmpty()){
				if (sourceCiCode.compareToIgnoreCase(tempCiCode)==0){
					sourceId =userObjectEle.attributeValue("id");
				}
				if (targetCiCode.compareToIgnoreCase(tempCiCode)==0){
					targetId =userObjectEle.attributeValue("id");
				}
			}
			Element mxTempEle = userObjectEle.element("mxCell");
			if (mxTempEle.attribute("source")!=null && mxTempEle.attribute("target")!=null && sourceId.compareToIgnoreCase(mxTempEle.attributeValue("source"))==0 && targetId.compareToIgnoreCase(mxTempEle.attributeValue("target"))==0 ){
				if (newRltCls.compareToIgnoreCase(userObjectEle.attributeValue("label")) ==0 ){
					hasRlt = true;
				}

			}
		}
		assertTrue(hasRlt);
	}

	/******************************右键连线-创建关系****************/
	
	/******************************右键连线-删除关系****************/

	@When("^在视图\"(.*)\"中\"(.*)\"到\"(.*)\"的关系\"(.*)\"右键删除关系$")
	public void rltRemoveRlt(String diagramName, String sourceCiCode, String targetCiCode, String rltCls) throws DocumentException{
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		UpdateDiagramContent ud = new UpdateDiagramContent();
		JSONObject result = ud.updateDiagramContentRemoveRlt(diagramName, sourceCiCode, targetCiCode, rltCls);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
	}

	@Then("^在视图\"(.*)\"中\"(.*)\"到\"(.*)\"的关系\"(.*)\"右键删除关系成功$")
	public void checkRltRemoveRlt(String diagramName, String sourceCiCode, String targetCiCode, String rltClassName) throws DocumentException{
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		RltClassUtil rltClassUtil = new RltClassUtil();
		BigDecimal rltClassId = rltClassUtil.getRltClassId(rltClassName);
		String sql = "SELECT ID, CLASS_ID,SOURCE_CI_CODE,TARGET_CI_CODE FROM cc_ci_rlt WHERE CLASS_ID = " 
				+ rltClassId + " AND SOURCE_CI_CODE='" + sourceCiCode + "' AND TARGET_CI_CODE='" + targetCiCode + "' AND DATA_STATUS = 1 and DOMAIN_ID="+QaUtil.domain_id;
		List rltList = JdbcUtil.executeQuery(sql);
		assertEquals(rltList.size(),  1);

		JSONObject result = getDiagramInfoByName(diagramName);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
		JSONObject oldParam = result.getJSONObject("data");
		//检查xml
		String xml = oldParam.getString("xml");
		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement();            
		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); 
		// 遍历UserObject节点
		//取容器
		String sourceId = "";
		String targetId = "";
		boolean hasRlt = false;
		while (userObjectIters.hasNext()) {
			Element userObjectEle= (Element) userObjectIters.next();
			String tempCiCode = userObjectEle.attributeValue("code");
			if (tempCiCode!=null && !tempCiCode.isEmpty()){
				if (sourceCiCode.compareToIgnoreCase(tempCiCode)==0){
					sourceId =userObjectEle.attributeValue("id");
				}
				if (targetCiCode.compareToIgnoreCase(tempCiCode)==0){
					targetId =userObjectEle.attributeValue("id");
				}
			}
			Element  mxTempEle = userObjectEle.element("mxCell");
			if (mxTempEle.attribute("source")!=null && mxTempEle.attribute("target")!=null && sourceId.compareToIgnoreCase(mxTempEle.attributeValue("source"))==0 && targetId.compareToIgnoreCase(mxTempEle.attributeValue("target"))==0 ){
				if (rltClassName.compareToIgnoreCase(userObjectEle.attributeValue("label")) ==0 ){
					hasRlt = true;
					break;
				}
				
			}

		}
		assertFalse(hasRlt);
	}
	/******************************右键-删除关系结束****************/
	
	
	/******************************右键-隐藏关系****************/
	@When("^在视图\"(.*)\"中\"(.*)\"到\"(.*)\"的关系\"(.*)\"右键隐藏关系$")
	public void hideRlt(String diagramName, String sourceCiCode, String targetCiCode, String rltCls) throws DocumentException{
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		UpdateDiagramContent ud = new UpdateDiagramContent();
		JSONObject result = ud.updateDiagramContentHideOrDisplayRlt(diagramName, sourceCiCode, targetCiCode, rltCls, "0");
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
	}

	@Then("^在视图\"(.*)\"中\"(.*)\"到\"(.*)\"的关系\"(.*)\"右键隐藏关系成功$")
	public void checkHideRlt(String diagramName, String sourceCiCode, String targetCiCode, String rltClassName) throws DocumentException{
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		RltClassUtil rltClassUtil = new RltClassUtil();
		BigDecimal rltClassId = rltClassUtil.getRltClassId(rltClassName);
		String sql = "SELECT ID, CLASS_ID,SOURCE_CI_CODE,TARGET_CI_CODE FROM cc_ci_rlt WHERE CLASS_ID = " 
				+ rltClassId + " AND SOURCE_CI_CODE='" + sourceCiCode + "' AND TARGET_CI_CODE='" + targetCiCode + "' AND DATA_STATUS = 1 and DOMAIN_ID="+QaUtil.domain_id;
		List rltList = JdbcUtil.executeQuery(sql);
		assertEquals(rltList.size(),  1);

		JSONObject result = getDiagramInfoByName(diagramName);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
		JSONObject oldParam = result.getJSONObject("data");
		//检查xml
		String xml = oldParam.getString("xml");
		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement();      
		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); 

		// 遍历root节点
		String sourceId = "";
		String targetId = "";
		boolean hasRlt = false;
		while (userObjectIters.hasNext()) {
			Element userObjectEle= (Element) userObjectIters.next();
			String tempCiCode = userObjectEle.attributeValue("code");
			if (tempCiCode!=null && !tempCiCode.isEmpty()){
				if (sourceCiCode.compareToIgnoreCase(tempCiCode)==0){
					sourceId =userObjectEle.attributeValue("id");
				}
				if (targetCiCode.compareToIgnoreCase(tempCiCode)==0){
					targetId =userObjectEle.attributeValue("id");
				}
			}
			Element mxTempEle = userObjectEle.element("mxCell");
			if (mxTempEle.attribute("source")!=null && mxTempEle.attribute("target")!=null && sourceId.compareToIgnoreCase(mxTempEle.attributeValue("source"))==0 && targetId.compareToIgnoreCase(mxTempEle.attributeValue("target"))==0 ){
				if (rltClassName.compareToIgnoreCase(userObjectEle.attributeValue("label")) ==0 ){
					assertEquals("0",mxTempEle.attributeValue("showStatus"));
					hasRlt = true;
					break;
				}
				
			}
		}
		assertFalse(hasRlt);
	}
	/******************************右键-隐藏关系结束****************/
	
	
	/******************************右键-添加、修改、取消钻取视图****************/
	@When("^在视图\"(.*)\"中\"(.*)\"到\"(.*)\"的关系\"(.*)\"右键添加钻取视图\"(.*)\"$")
	public void rltAddDrillDown(String diagramName, String sourceCiCode, String targetCiCode, String rltClassName,  String drillDiagramName) throws DocumentException{
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		UpdateDiagramContent ud = new UpdateDiagramContent();
		JSONObject result = ud.updateDiagramContentRltAddDrillDown(diagramName, sourceCiCode, targetCiCode, rltClassName, drillDiagramName);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
	}

	
	@Then("^在视图\"(.*)\"中\"(.*)\"到\"(.*)\"的关系\"(.*)\"成功添加钻取视图\"(.*)\"$")
	public void checkCiAddDrillDown(String diagramName, String sourceCiCode, String targetCiCode, String rltClassName, String drillDiagramName) throws DocumentException{
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		JSONObject result = getDiagramInfoByName(diagramName);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
		JSONObject oldParam = result.getJSONObject("data");

		DiagramUtil du = new DiagramUtil();
		BigDecimal drillDiagramId = du.getDiagramIdByName(drillDiagramName);

		//检查xml
		String xml = oldParam.getString("xml");
		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement();            
		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); 
		// 遍历UserObject节点
		//取容器
		String sourceId = "";
		String targetId = "";
		boolean hasRlt = false;
		while (userObjectIters.hasNext()) {
			Element userObjectEle= (Element) userObjectIters.next();
			String tempCiCode = userObjectEle.attributeValue("code");
			if (tempCiCode!=null && !tempCiCode.isEmpty()){
				if (sourceCiCode.compareToIgnoreCase(tempCiCode)==0){
					sourceId =userObjectEle.attributeValue("id");
				}
				if (targetCiCode.compareToIgnoreCase(tempCiCode)==0){
					targetId =userObjectEle.attributeValue("id");
				}
			}
			Element mxTempEle = userObjectEle.element("mxCell");

			if (mxTempEle.attribute("source")!=null && mxTempEle.attribute("target")!=null && sourceId.compareToIgnoreCase(mxTempEle.attributeValue("source"))==0 && targetId.compareToIgnoreCase(mxTempEle.attributeValue("target"))==0 ){
				if (rltClassName.compareToIgnoreCase(userObjectEle.attributeValue("label"))==0){
					assertNotNull(userObjectEle.attribute("direction"));
					assertEquals("down", userObjectEle.attributeValue("direction"));
					assertNotNull(userObjectEle.attribute("view"));
					assertTrue(userObjectEle.attributeValue("view").contains(String.valueOf(drillDiagramId)));
					Element tempMxCell = userObjectEle.element("mxCell");
					assertNotNull(tempMxCell.attribute("utdirection"));
					assertEquals("down", tempMxCell.attributeValue("utdirection"));
					assertNotNull(tempMxCell.attribute("utview"));
					assertTrue(tempMxCell.attributeValue("utview").contains(String.valueOf(drillDiagramId)));
					hasRlt = true;
					break;
				}
			}
		}
		assertTrue(hasRlt);
		
	}

	@When("^在视图\"(.*)\"中\"(.*)\"到\"(.*)\"的关系\"(.*)\"右键删除钻取视图\"(.*)\"$")
	public void ciRemoveDrillDown(String diagramName, String sourceCiCode, String targetCiCode, String rltClassName, String drillDiagramName) throws DocumentException{
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		UpdateDiagramContent ud = new UpdateDiagramContent();
		JSONObject result = ud.updateDiagramContentRltRemoveDrillDown(diagramName, sourceCiCode, targetCiCode, rltClassName, drillDiagramName);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
	}

	@Then("^在视图\"(.*)\"中\"(.*)\"到\"(.*)\"的关系\"(.*)\"成功删除钻取视图\"(.*)\"$")
	public void checkCiRemoveDrillDown(String diagramName, String sourceCiCode, String targetCiCode, String rltClassName, String drillDiagramName) throws DocumentException{
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		JSONObject result = getDiagramInfoByName(diagramName);
		assertTrue(result.getBoolean("success"));	
		assertEquals(result.getInt("code"), -1);
		JSONObject oldParam = result.getJSONObject("data");
		DiagramUtil du = new DiagramUtil();
		BigDecimal drillDiagramId = du.getDiagramIdByName(drillDiagramName);
		//检查xml
		String xml = oldParam.getString("xml");
		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement();            
		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); 
		// 遍历UserObject节点
		//取容器
		String sourceId = "";
		String targetId = "";
		boolean hasRlt = false;
		while (userObjectIters.hasNext()) {
			Element userObjectEle= (Element) userObjectIters.next();
			String tempCiCode = userObjectEle.attributeValue("code");
			if (tempCiCode!=null && !tempCiCode.isEmpty()){
				if (sourceCiCode.compareToIgnoreCase(tempCiCode)==0){
					sourceId =userObjectEle.attributeValue("id");
				}
				if (targetCiCode.compareToIgnoreCase(tempCiCode)==0){
					targetId =userObjectEle.attributeValue("id");
				}
			}
			
			Element mxTempEle = userObjectEle.element("mxCell");
			if (mxTempEle.attribute("source")!=null && mxTempEle.attribute("target")!=null && sourceId.compareToIgnoreCase(mxTempEle.attributeValue("source"))==0 && targetId.compareToIgnoreCase(mxTempEle.attributeValue("target"))==0 ){
				if (rltClassName.compareToIgnoreCase(userObjectEle.attributeValue("label"))==0){
					assertNotNull(userObjectEle.attribute("direction"));
					assertEquals("down", userObjectEle.attributeValue("direction"));
					assertNotNull(userObjectEle.attribute("view"));
					assertFalse(userObjectEle.attributeValue("view").contains(String.valueOf(drillDiagramId)));
					Element tempMxCell = userObjectEle.element("mxCell");
					assertNotNull(tempMxCell.attribute("utdirection"));
					assertEquals("down", tempMxCell.attributeValue("utdirection"));
					assertNotNull(tempMxCell.attribute("utview"));
					assertFalse(tempMxCell.attributeValue("utview").contains(String.valueOf(drillDiagramId)));
					hasRlt = true;
					break;
				}
			}
		}
		assertTrue(hasRlt);
		
	}
	/******************************右键-添加、修改、删除钻取视图结束****************/

	 @After("@CleanData")
	 public void CleanData(String diagramName){
		 String[] clsNames = {"importCiData","ciTagData"};
		 for(int i=0;i<clsNames.length;i++){
			 String clsName = clsNames[i];
			 if((new CiClassUtil()).getCiClassId(clsName).compareTo(new BigDecimal(0))>0){
				 
				 Boolean result = (new CiClassUtil()).deleteCiClassAndCi(clsName);
			 }
		 }
	 }
}
