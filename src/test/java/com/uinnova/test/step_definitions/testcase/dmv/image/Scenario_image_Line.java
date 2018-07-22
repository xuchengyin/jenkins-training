package com.uinnova.test.step_definitions.testcase.dmv.image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.dmv.diagram.UpdateDiagramContent;
import com.uinnova.test.step_definitions.utils.base.RltClassUtil;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;
import com.uinnova.test.step_definitions.utils.dmv.DiagramUtil;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author wsl
 * 视图中连线操作
 *
 */
public class Scenario_image_Line extends Scenario_image_Base{

	/********************************给两个CI增加连线***************************************/
	@When("在视图\"(.*)\"中增加\"(.*)\"到\"(.*)\"的连线$")
	public void addCILine(String diagramName, String sourceCiCode, String targetCiCode) throws DocumentException{
		if (diagramName.isEmpty() ){
			fail("视图名称为空");
		}
		UpdateDiagramContent ud = new UpdateDiagramContent();
		JSONObject result = ud.updateDiagramContentAddCILine(diagramName, sourceCiCode, targetCiCode);
		assertTrue(result.getBoolean("success"));
	}

	@Then("成功在视图\"(.*)\"中增加\"(.*)\"到\"(.*)\"的连线$")
	public void checkAddCILine(String diagramName, String sourceCiCode, String targetCiCode) throws DocumentException{
		if (diagramName.isEmpty() ){
			fail("视图名称为空");
		}
		JSONObject result = getDiagramInfoByName(diagramName);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
		JSONObject oldParam = result.getJSONObject("data");
		JSONArray diagramEles = new JSONArray();
		String xml = oldParam.getString("xml");

		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement();      
		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); 
		// 遍历root节点
		String sourceId = "";
		String targetId = "";
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
		}

		boolean hasLine = false;
		List<Element> eleList = rootElement.elements();
		for (int i=0; i<eleList.size(); i++){
			Element tempEle = eleList.get(i);
			if (tempEle.attribute("source")!=null && tempEle.attribute("target")!=null && sourceId.compareToIgnoreCase(tempEle.attributeValue("source"))==0 && targetId.compareToIgnoreCase(tempEle.attributeValue("target"))==0 ){
				hasLine = true;
				break;
			}
		}
		assertTrue(hasLine);
	}
	
	/********************************给两个CI增加连线***************************************/
	@When("在视图\"(.*)\"中删除\"(.*)\"到\"(.*)\"的连线$")
	public void removeCILine(String diagramName, String sourceCiCode, String targetCiCode) throws DocumentException{
		if (diagramName.isEmpty() ){
			fail("视图名称为空");
		}
		UpdateDiagramContent ud = new UpdateDiagramContent();
		JSONObject result = ud.updateDiagramContentRemoveCILine(diagramName, sourceCiCode, targetCiCode);
		assertTrue(result.getBoolean("success"));
	}

	@Then("成功在视图\"(.*)\"中删除\"(.*)\"到\"(.*)\"的连线$")
	public void checkRemoveCILine(String diagramName, String sourceCiCode, String targetCiCode) throws DocumentException{
		if (diagramName.isEmpty() ){
			fail("视图名称为空");
		}
		JSONObject result = getDiagramInfoByName(diagramName);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
		JSONObject oldParam = result.getJSONObject("data");
		JSONArray diagramEles = new JSONArray();
		String xml = oldParam.getString("xml");

		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement();      
		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); 
		// 遍历root节点
		String sourceId = "";
		String targetId = "";
		boolean hasLine = false;
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
				Element  mxTempEle = userObjectEle.element("mxCell");
				if (mxTempEle.attribute("source")!=null && mxTempEle.attribute("target")!=null && sourceId.compareToIgnoreCase(mxTempEle.attributeValue("source"))==0 && targetId.compareToIgnoreCase(mxTempEle.attributeValue("target"))==0 ){
					hasLine = true;
				}
			}
		}

		assertFalse(hasLine);
		
		List<Element> eleList = rootElement.elements();
		for (int i=0; i<eleList.size(); i++){
			Element tempEle = eleList.get(i);
			if (tempEle.attribute("source")!=null && tempEle.attribute("target")!=null && sourceId.compareToIgnoreCase(tempEle.attributeValue("source"))==0 && targetId.compareToIgnoreCase(tempEle.attributeValue("target"))==0 ){
				hasLine = true;
				break;
			}
		}
		assertFalse(hasLine);
	}
	/********************************给两个CI增加连线结束***************************************/

	/********************************给两个CI之间连线箭头反向***************************************/
	@When("在视图\"(.*)\"中增加\"(.*)\"到\"(.*)\"的连线箭头反向$")
	public void lineReverse(String diagramName, String sourceCiCode, String targetCiCode) throws DocumentException{
		if (diagramName.isEmpty() ){
			fail("视图名称为空");
		}
		UpdateDiagramContent ud = new UpdateDiagramContent();
		JSONObject result = ud.updateDiagramContentLineReverse(diagramName, sourceCiCode, targetCiCode);
		assertTrue(result.getBoolean("success"));
	}
	@Then("成功在视图\"(.*)\"中增加\"(.*)\"到\"(.*)\"的连线箭头反向$")
	public void checkLineReverse(String diagramName, String sourceCiCode, String targetCiCode) throws DocumentException{
		if (diagramName.isEmpty() ){
			fail("视图名称为空");
		}
		JSONObject result = getDiagramInfoByName(diagramName);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
		JSONObject oldParam = result.getJSONObject("data");
		JSONArray diagramEles = new JSONArray();
		String xml = oldParam.getString("xml");

		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement();      
		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); 
		// 遍历root节点
		String sourceId = "";
		String targetId = "";
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
		}

		boolean hasLine = false;
		List<Element> eleList = rootElement.elements();
		for (int i=0; i<eleList.size(); i++){
			Element tempEle = eleList.get(i);
			if (tempEle.attribute("source")!=null && tempEle.attribute("target")!=null && sourceId.compareToIgnoreCase(tempEle.attributeValue("target"))==0 && targetId.compareToIgnoreCase(tempEle.attributeValue("source"))==0 ){
				hasLine = true;
				break;
			}
		}
		assertTrue(hasLine);

	}
	/********************************给两个CI之间连线箭头反向结束***************************************/

	/******************************右键连线-创建关系****************/
	@When("^在视图\"(.*)\"中\"(.*)\"到\"(.*)\"的连线右键创建关系\"(.*)\"$")
	public void lineCreateRlt(String diagramName, String sourceCiCode, String targetCiCode, String rltCls) throws DocumentException{
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		UpdateDiagramContent ud = new UpdateDiagramContent();
		JSONObject result = ud.updateDiagramContentLineCreateRlt(diagramName, sourceCiCode, targetCiCode, rltCls);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
	}

	@Then("^在视图\"(.*)\"中\"(.*)\"到\"(.*)\"的连线右键创建关系\"(.*)\"成功$")
	public void checkLineCreateRlt(String diagramName, String sourceCiCode, String targetCiCode, String rltClassName) throws DocumentException{
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		 RltClassUtil rltClassUtil = new RltClassUtil();
		 BigDecimal rltClassId = rltClassUtil.getRltClassId(rltClassName);
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
		boolean hasLine = false;
		List<String> containerList = new ArrayList<String>();
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
				assertNotNull(userObjectEle.attribute("data-id"));
				assertEquals(userObjectEle.attributeValue("data-id"), "relation_"+rltId);
				Element tempMxCell = userObjectEle.element("mxCell");
				assertNotNull(tempMxCell.attribute("utdata-id"));
				assertEquals(tempMxCell.attributeValue("utdata-id"), "relation_"+rltId);
				hasLine = true;
			}

		}
		assertTrue(hasLine);
	}

	/******************************右键连线-创建关系****************/
	
	/******************************右键连线-添加、修改、取消钻取视图****************/
	@When("^在视图\"(.*)\"中给\"(.*)\"到\"(.*)\"的连线添加钻取视图\"(.*)\"$")
	public void ciAddDrillDown(String diagramName, String sourceCiCode, String targetCiCode, String drillDiagramName) throws DocumentException{
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		UpdateDiagramContent ud = new UpdateDiagramContent();
		JSONObject result = ud.updateDiagramContentLineAddDrillDown(diagramName, sourceCiCode, targetCiCode, drillDiagramName);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
	}

	@Then("^在视图\"(.*)\"中给\"(.*)\"到\"(.*)\"的连线成功添加钻取视图\"(.*)\"$")
	public void checkCiAddDrillDown(String diagramName, String sourceCiCode, String targetCiCode, String drillDiagramName) throws DocumentException{
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
		boolean hasLine = false;
		List<String> containerList = new ArrayList<String>();
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
				assertNotNull(userObjectEle.attribute("direction"));
				assertEquals("down", userObjectEle.attributeValue("direction"));
				assertNotNull(userObjectEle.attribute("view"));
				assertTrue(userObjectEle.attributeValue("view").contains(String.valueOf(drillDiagramId)));
				Element tempMxCell = userObjectEle.element("mxCell");
				assertNotNull(tempMxCell.attribute("utdirection"));
				assertEquals("down", tempMxCell.attributeValue("utdirection"));
				assertNotNull(tempMxCell.attribute("utview"));
				assertTrue(tempMxCell.attributeValue("utview").contains(String.valueOf(drillDiagramId)));
				hasLine = true;
			}

		}
		assertTrue(hasLine);
	}

	@When("^在视图\"(.*)\"中给\"(.*)\"到\"(.*)\"的连线删除钻取视图\"(.*)\"$")
	public void ciRemoveDrillDown(String diagramName, String sourceCiCode, String targetCiCode, String drillDiagramName) throws DocumentException{
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		UpdateDiagramContent ud = new UpdateDiagramContent();
		JSONObject result = ud.updateDiagramContentLineRemoveDrillDown(diagramName,  sourceCiCode, targetCiCode, drillDiagramName);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
	}

	@Then("^在视图\"(.*)\"中给\"(.*)\"到\"(.*)\"的连线成功删除钻取视图\"(.*)\"$")
	public void checkCiRemoveDrillDown(String diagramName, String sourceCiCode, String targetCiCode, String drillDiagramName) throws DocumentException{
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
		boolean hasLine = false;
		List<String> containerList = new ArrayList<String>();
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
				assertNotNull(userObjectEle.attribute("direction"));
				assertEquals("down", userObjectEle.attributeValue("direction"));
				assertNotNull(userObjectEle.attribute("view"));
				assertFalse(userObjectEle.attributeValue("view").contains(String.valueOf(drillDiagramId)));
				Element tempMxCell = userObjectEle.element("mxCell");
				assertNotNull(tempMxCell.attribute("utdirection"));
				assertEquals("down", tempMxCell.attributeValue("utdirection"));
				assertNotNull(tempMxCell.attribute("utview"));
				assertFalse(tempMxCell.attributeValue("utview").contains(String.valueOf(drillDiagramId)));
				hasLine = true;
			}

		}
		assertTrue(hasLine);
	}
	/******************************右键连线-添加、修改、删除钻取视图结束****************/
}
