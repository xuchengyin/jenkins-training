package com.uinnova.test.step_definitions.testcase.dmv.image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.base.search.ci.SearchInfoList;
import com.uinnova.test.step_definitions.api.cmv.sys.data.QueryRoleAllDataAuth;
import com.uinnova.test.step_definitions.api.cmv.sys.data.SaveRoleDataAuth;
import com.uinnova.test.step_definitions.api.dmv.ci.QueryById;
import com.uinnova.test.step_definitions.api.dmv.ci.QueryList;
import com.uinnova.test.step_definitions.api.dmv.ci.SaveOrUpdate;
import com.uinnova.test.step_definitions.api.dmv.ci.SearchInfoByRuleItem;
import com.uinnova.test.step_definitions.api.dmv.ciRlt.QueryCiBetweenRlt;
import com.uinnova.test.step_definitions.api.dmv.ciRlt.QueryRltInfoList;
import com.uinnova.test.step_definitions.api.dmv.ciRlt.QueryUpAndDownRlt;
import com.uinnova.test.step_definitions.api.dmv.comb.QueryDigramInfoAndEventByIds;
import com.uinnova.test.step_definitions.api.dmv.diagram.QueryDiagramInfoById;
import com.uinnova.test.step_definitions.api.dmv.diagram.QueryRelatedDiagramEventByDiagramId;
import com.uinnova.test.step_definitions.api.dmv.diagram.SaveOrUpdateDiagram;
import com.uinnova.test.step_definitions.api.dmv.diagram.UpdateDiagramContent;
import com.uinnova.test.step_definitions.api.dmv.sys.data.QueryUserAllDataAuth;
import com.uinnova.test.step_definitions.utils.base.AuthUtil;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.base.CiUtil;
import com.uinnova.test.step_definitions.utils.base.RltClassUtil;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;
import com.uinnova.test.step_definitions.utils.dmv.Ci3dPointUtil;
import com.uinnova.test.step_definitions.utils.dmv.DiagramUtil;
import com.uinnova.test.step_definitions.utils.dmv.XmlUtil;

import cucumber.api.Delimiter;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author wsl
 * 2017-12-22 DMV-绘图-新建视图-CI画图
 *
 */
public class Scenario_image_CI extends Scenario_image_Base{
	private JSONArray ciCodesArray = new JSONArray();//查看配置时用
    private JSONObject result = new JSONObject();
	/**********************解除CI、创建CI************************/
	@When("在视图\"(.*)\"中右键CI\"(.*)\"解除CI$")
	public void ciGetRidOfCi(String diagramName, String ciCode) throws DocumentException{
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		UpdateDiagramContent ud = new UpdateDiagramContent();
		JSONObject result = ud.updateDiagramContentCIGetRidOfCi(diagramName, ciCode);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
	}

	@Then("在视图\"(.*)\"中右键CI\"(.*)\"成功解除CI$")
	public void checkCiGetRidOfCi(String diagramName, String ciCode) throws DocumentException{
		JSONObject result = getDiagramInfoByName(diagramName);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
		JSONObject oldParam = result.getJSONObject("data");
		JSONArray diagramEles = new JSONArray();
		//检查diagramEles
		if(oldParam.has("diagramEles")){
			diagramEles = (JSONArray) oldParam.get("diagramEles");
		}
		boolean hasFlag = false;
		CiUtil ciUtil = new CiUtil();
		BigDecimal ciId = ciUtil.getCiId(ciCode);
		for (int i=0; i<diagramEles.length(); i++){
			JSONObject tmp = (JSONObject) diagramEles.get(i);
			if (tmp.getBigDecimal("eleId") == ciId){
				hasFlag = true;
				break;
			}
		}
		assertFalse(hasFlag);
		int diagramId = oldParam.getJSONObject("diagram").getInt("id");
		String sql ="select Id from vc_diagram_ele where ele_Id="+ciId+" and diagram_ID="+diagramId +" and DOMAIN_ID = "+ QaUtil.domain_id;
		ArrayList list = (ArrayList) JdbcUtil.executeQuery(sql);
		assertEquals(0, list.size());

		//检查xml
		String xml = oldParam.getString("xml");
		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement();            
		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); 
		// 遍历UserObject节点
		while (userObjectIters.hasNext()) {
			Element userObjectEle= (Element) userObjectIters.next();
			if (userObjectEle.attribute("code")!=null){
				if (ciCode.compareToIgnoreCase(userObjectEle.attributeValue("code"))==0){
					String dataId = userObjectEle.attributeValue("data-id");
					assertTrue(dataId.isEmpty());
					Element tempMxCell = userObjectEle.element("mxCell");
					assertNull(tempMxCell.attribute("utcode"));
					assertNull(tempMxCell.attribute("utdata-id"));
					break;
				}
			}
		}
		//检查ci3dPoint
		boolean hasNode = false;
		JSONObject ci3dPoint = new JSONObject(oldParam.getString("ci3dPoint"));
		JSONArray nodes = ci3dPoint.getJSONArray("nodes");
		for (int i=0; i<nodes.length(); i++){
			JSONObject tempObj = nodes.getJSONObject(i);
			if (ciCode.compareToIgnoreCase(tempObj.getString("name"))==0){
				assertFalse(tempObj.has("ciId"));
				assertFalse(tempObj.has("code"));
				assertFalse(tempObj.has("data-id"));
				hasNode = true;
				break;
			}
		}
		assertTrue(hasNode);
	}

	/**
	 * @param diagramName
	 * @param ciCode
	 * @throws DocumentException
	 * CI绘图解除CI后再创建CI
	 */
	@When("在视图\"(.*)\"中右键CI\"(.*)\"创建CI\"(.*)\"坐标为\"(.*)\"\"(.*)\"$")
	public void ciCreateCi(String diagramName, String oldCiCode,String newCiCode, String x, String y) throws DocumentException {
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		UpdateDiagramContent ud = new UpdateDiagramContent();
		JSONObject result = ud.updateDiagramContentCICreateCi(diagramName, oldCiCode, newCiCode, x, y);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
	}

	@Then("在视图\"(.*)\"中右键CI\"(.*)\"成功创建CI\"(.*)\"$")
	public void checkCiCreateCi(String diagramName, String oldCiCode,String newCiCode) throws DocumentException{
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		JSONObject result = getDiagramInfoByName(diagramName);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
		JSONObject oldParam = result.getJSONObject("data");
		JSONArray diagramEles = new JSONArray();
		//检查diagramEles
		if(oldParam.has("diagramEles")){
			diagramEles = (JSONArray) oldParam.get("diagramEles");
		}
		boolean hasFlag = false;
		CiUtil ciUtil = new CiUtil();
		BigDecimal ciId = ciUtil.getCiId(newCiCode);
		for (int i=0; i<diagramEles.length(); i++){
			JSONObject tmp = (JSONObject) diagramEles.get(i);
			if (tmp.getBigDecimal("eleId").compareTo(ciId)==0){
				hasFlag = true;
				break;
			}
		}
		assertTrue(hasFlag);

		//检查数据库
		BigDecimal diagramId = oldParam.getJSONObject("diagram").getBigDecimal("id");
		String sql ="select Id from vc_diagram_ele where ele_Id="+ciId+" and diagram_ID="+diagramId +" and DOMAIN_ID = "+ QaUtil.domain_id;
		ArrayList list = (ArrayList) JdbcUtil.executeQuery(sql);
		assertEquals(1, list.size());

		//检查xml
		String xml = oldParam.getString("xml");
		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement();            
		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); 
		// 遍历UserObject节点
		while (userObjectIters.hasNext()) {
			Element userObjectEle= (Element) userObjectIters.next();
			if (userObjectEle.attribute("code")!=null){
				if (oldCiCode.compareToIgnoreCase(userObjectEle.attributeValue("code"))==0){
					String dataId = userObjectEle.attributeValue("data-id");
					assertTrue(dataId.compareToIgnoreCase("CI_"+String.valueOf(ciId))==0);
					Element tempMxCell = userObjectEle.element("mxCell");
					assertEquals(newCiCode, tempMxCell.attributeValue("utcode"));
					assertEquals("ci_"+String.valueOf(ciId),tempMxCell.attributeValue("utdata-id"));
					break;
				}
			}
		}
		//检查ci3dPoint
		boolean hasNode = false;
		JSONObject ci3dPoint = new JSONObject(oldParam.getString("ci3dPoint"));
		JSONArray nodes = ci3dPoint.getJSONArray("nodes");
		for (int i=0; i<nodes.length(); i++){
			JSONObject tempObj = nodes.getJSONObject(i);
			if (tempObj.has("code")){
				if (newCiCode.compareToIgnoreCase(tempObj.getString("code"))==0){
					assertTrue(tempObj.has("ciId"));
					assertTrue(tempObj.has("code"));
					assertTrue(tempObj.has("data-id"));
					assertEquals(tempObj.getString("code"),newCiCode);
					hasNode = true;
					break;
				}
			}
		}
		assertTrue(hasNode);

	}

	/******************************解除、创建CI结束****************/

	/******************************右键-关系绘图***************/
	@When("^在视图\"(.*)\"中右键CI\"(.*)\"关系绘图$")
	public void ciRelationDraw(String diagramName, String ciCode) throws DocumentException {
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		UpdateDiagramContent ud = new UpdateDiagramContent();
		JSONObject result = ud.updateDiagramContentCIRelationDraw(diagramName, ciCode);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
	}

	@Then("^在视图\"(.*)\"中右键CI\"(.*)\"关系绘图成功$")
	public void chekcCiRelationDraw(String diagramName, String ciCode) throws DocumentException {
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		JSONObject result = getDiagramInfoByName(diagramName);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
		JSONObject oldParam = result.getJSONObject("data");

		JSONArray diagramEles = new JSONArray();
		if(oldParam.has("diagramEles")){
			diagramEles = (JSONArray) oldParam.get("diagramEles");
		}
		String xml = oldParam.getString("xml");
		String ci3dPoint = oldParam.getString("ci3dPoint");

		XmlUtil xmlUtil = new XmlUtil();
		Ci3dPointUtil ci3dPointUtil = new Ci3dPointUtil();
		DiagramUtil diagramUtil = new DiagramUtil();
		QueryUpAndDownRlt queryUpAndDownRlt = new QueryUpAndDownRlt();
		JSONObject upDownRltResult = queryUpAndDownRlt.queryUpAndDownRlt(ciCode, "5", "5");
		assertTrue(upDownRltResult.getBoolean("success"));
		assertEquals(upDownRltResult.getInt("code"), -1);
		assertTrue(upDownRltResult.has("data"));
		JSONArray  upDownRltData= upDownRltResult.getJSONArray("data");
		List idList = new ArrayList<>();
		for (int i=0; i<upDownRltData.length(); i++){
			JSONObject tempObj = (JSONObject) upDownRltData.get(i);
			JSONObject sourceCiInfo =  tempObj.getJSONObject("sourceCiInfo");
			JSONObject targetCiInfo =  tempObj.getJSONObject("targetCiInfo");

			String sourceCiCode = sourceCiInfo.getJSONObject("ci").getString("ciCode");
			String targetCiCode = targetCiInfo.getJSONObject("ci").getString("ciCode");

			//检查diagramEles
			assertTrue(diagramUtil.hasElement(diagramEles, sourceCiCode));
			assertTrue(diagramUtil.hasElement(diagramEles, targetCiCode));
			//检查xml
			assertTrue(xmlUtil.hasCiCode(xml, targetCiCode));
			assertTrue(xmlUtil.hasCiCode(xml, sourceCiCode));
			//检查ci3dPoint
			ci3dPointUtil.hasCiCode(ci3dPoint, targetCiCode);
			ci3dPointUtil.hasCiCode(ci3dPoint, sourceCiCode);
			//检查svg
			idList.add(targetCiInfo.getJSONObject("ci").getBigDecimal("id"));

			//检查关系
			JSONObject ciRlt =  tempObj.getJSONObject("ciRlt");
			assertTrue(xmlUtil.hasRlt(xml, String.valueOf(ciRlt.getBigDecimal("id"))));

		}

		//判断数据库视图元素表
		String ids ="";
		assertNotNull(idList);
		assertTrue(idList.size()>0);
		for (int i=0; i<idList.size(); i++){
			if (ids.isEmpty())
				ids = String.valueOf(idList.get(i));
			else
				ids+= ","+String.valueOf(idList.get(i));
		}
		String sql ="select Id from vc_diagram_ele where ele_Id in ("+ids+") and diagram_ID="+oldParam.getJSONObject("diagram").getBigDecimal("id") +" and DOMAIN_ID = "+ QaUtil.domain_id;
		ArrayList list = (ArrayList) JdbcUtil.executeQuery(sql);
		assertEquals(idList.size(), list.size());

	}

	/******************************右键-关系绘图结束***************/

	/******************************右键-添加、修改、取消钻取视图****************/
	@When("^在视图\"(.*)\"中给CI\"(.*)\"添加钻取视图\"(.*)\"$")
	public void ciAddDrillDown(String diagramName, String ciCode, String drillDiagramName) throws DocumentException{
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		UpdateDiagramContent ud = new UpdateDiagramContent();
		JSONObject result = ud.updateDiagramContentCIAddDrillDown(diagramName, ciCode, drillDiagramName);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
	}

	@Then("^在视图\"(.*)\"中给CI\"(.*)\"成功添加钻取视图\"(.*)\"$")
	public void checkCiAddDrillDown(String diagramName, String ciCode, String drillDiagramName) throws DocumentException{
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
		boolean hasCICode = false;
		List<String> containerList = new ArrayList<String>();
		while (userObjectIters.hasNext()) {
			Element userObjectEle= (Element) userObjectIters.next();
			if (userObjectEle.attribute("code")!=null){
				if (ciCode.compareToIgnoreCase(userObjectEle.attributeValue("code"))==0){

					assertNotNull(userObjectEle.attribute("direction"));
					assertEquals("down", userObjectEle.attributeValue("direction"));
					assertNotNull(userObjectEle.attribute("view"));
					assertTrue(userObjectEle.attributeValue("view").contains(String.valueOf(drillDiagramId)));
					Element tempMxCell = userObjectEle.element("mxCell");
					assertNotNull(tempMxCell.attribute("utdirection"));
					assertEquals("down", tempMxCell.attributeValue("utdirection"));
					assertNotNull(tempMxCell.attribute("utview"));
					assertTrue(tempMxCell.attributeValue("utview").contains(String.valueOf(drillDiagramId)));
					hasCICode = true;
				}
			}
		}
		assertTrue(hasCICode);
		//检查ci3dPoint
		boolean hasNode = false;
		JSONObject ci3dPoint = new JSONObject(oldParam.getString("ci3dPoint"));
		JSONArray nodes = ci3dPoint.getJSONArray("nodes");
		for (int i=0; i<nodes.length(); i++){
			JSONObject tempObj = nodes.getJSONObject(i);
			if (tempObj.has("code"))
			{
				if (ciCode.compareToIgnoreCase(tempObj.getString("code"))==0){
					assertTrue(tempObj.has("view"));
					assertTrue(tempObj.has("direction"));
					assertTrue(tempObj.has("relation"));
					assertTrue(tempObj.getString("view").contains(String.valueOf(drillDiagramId)));
					assertEquals("down", tempObj.getString("direction"));
					JSONObject relObj = tempObj.getJSONObject("relation");
					assertTrue(relObj.has("viewId"));
					assertTrue(relObj.has("direction"));
					assertTrue(relObj.getString("viewId").contains(String.valueOf(drillDiagramId)));
					assertEquals("down", relObj.getString("direction"));
					hasNode = true;
					break;
				}
			}
		}
		assertTrue(hasNode);
	}

	@When("^在视图\"(.*)\"中给CI\"(.*)\"删除钻取视图\"(.*)\"$")
	public void ciRemoveDrillDown(String diagramName, String ciCode, String drillDiagramName) throws DocumentException{
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		UpdateDiagramContent ud = new UpdateDiagramContent();
		JSONObject result = ud.updateDiagramContentCIRemoveDrillDown(diagramName, ciCode, drillDiagramName);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
	}

	@Then("^在视图\"(.*)\"中给CI\"(.*)\"成功删除钻取视图\"(.*)\"$")
	public void checkCiRemoveDrillDown(String diagramName, String ciCode, String drillDiagramName) throws DocumentException{
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
		boolean hasCICode = false;
		List<String> containerList = new ArrayList<String>();
		while (userObjectIters.hasNext()) {
			Element userObjectEle= (Element) userObjectIters.next();
			if (userObjectEle.attribute("code")!=null){
				if (ciCode.compareToIgnoreCase(userObjectEle.attributeValue("code"))==0){
					assertNotNull(userObjectEle.attribute("direction"));
					assertEquals("down", userObjectEle.attributeValue("direction"));
					assertNotNull(userObjectEle.attribute("view"));
					assertFalse(userObjectEle.attributeValue("view").contains(String.valueOf(drillDiagramId)));
					Element tempMxCell = userObjectEle.element("mxCell");
					assertNotNull(tempMxCell.attribute("utdirection"));
					assertEquals("down", tempMxCell.attributeValue("utdirection"));
					assertNotNull(tempMxCell.attribute("utview"));
					assertFalse(tempMxCell.attributeValue("utview").contains(String.valueOf(drillDiagramId)));
					hasCICode = true;
				}
			}
		}
		assertTrue(hasCICode);
		//检查ci3dPoint
		boolean hasNode = false;
		JSONObject ci3dPoint = new JSONObject(oldParam.getString("ci3dPoint"));
		JSONArray nodes = ci3dPoint.getJSONArray("nodes");
		for (int i=0; i<nodes.length(); i++){
			JSONObject tempObj = nodes.getJSONObject(i);
			if (tempObj.has("code"))
			{
				if (ciCode.compareToIgnoreCase(tempObj.getString("code"))==0){
					assertTrue(tempObj.has("view"));
					assertTrue(tempObj.has("direction"));
					assertTrue(tempObj.has("relation"));
					assertFalse(tempObj.getString("view").contains(String.valueOf(drillDiagramId)));
					assertEquals("down", tempObj.getString("direction"));
					JSONObject relObj = tempObj.getJSONObject("relation");
					assertTrue(relObj.has("viewId"));
					assertTrue(relObj.has("direction"));
					assertFalse(relObj.getString("viewId").contains(String.valueOf(drillDiagramId)));
					assertEquals("down", relObj.getString("direction"));
					hasNode = true;
					break;
				}
			}
		}
		assertTrue(hasNode);
	}
	/******************************右键-添加、修改、删除钻取视图结束****************/

	/******************************右键-挂载、编辑、取消挂载数据***************/
	@When("^视图\"(.*)\"中CI\"(.*)\"挂载数据\"(.*)\"$")
	public void ciMountDynamicNode(String diagramName, String ciCode,String dynamicCiCode) throws DocumentException {
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		List<String> dynamicCiCodeList = new ArrayList<String>();
		dynamicCiCodeList.add(dynamicCiCode);
		UpdateDiagramContent ud = new UpdateDiagramContent();
		JSONObject result = ud.updateDiagramContentMountDynamicNode("ci", diagramName, ciCode, dynamicCiCodeList);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
	}

	@Then("^视图\"(.*)\"中CI\"(.*)\"成功挂载数据\"(.*)\"$")
	public void chekcCiMountDynamicNode(String diagramName, String ciCode,String dynamicCiCode) throws DocumentException {
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		JSONObject result = getDiagramInfoByName(diagramName);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
		JSONObject oldParam = result.getJSONObject("data");
		CiUtil ciUtil = new CiUtil();
		BigDecimal dynamicCiId = ciUtil.getCiId(dynamicCiCode);

		//检查xml
		String xml = oldParam.getString("xml");
		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement();            
		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); 
		// 遍历UserObject节点
		//取容器
		boolean hasCICode = false;
		List<String> containerList = new ArrayList<String>();
		while (userObjectIters.hasNext()) {
			Element userObjectEle= (Element) userObjectIters.next();
			if (userObjectEle.attribute("code")!=null){
				if (ciCode.compareToIgnoreCase(userObjectEle.attributeValue("code"))==0){
					Element tempMxCell = userObjectEle.element("mxCell");
					assertNotNull(tempMxCell.attribute("uttags-info"));
					assertNotNull(tempMxCell.attribute("uttags-params-info"));
					assertNotNull(tempMxCell.attribute("uttags-selection-cis"));
					assertNotNull(tempMxCell.attribute("utchild-num"));
					String childNum = tempMxCell.attributeValue("utchild-num");
					assertTrue(Integer.valueOf(childNum)>0);
					assertTrue(tempMxCell.attributeValue("uttags-selection-cis").contains(String.valueOf(dynamicCiId)));
					boolean hasChild = false;
					String temp = tempMxCell.attributeValue("uttags-selection-cis");
					assertTrue(temp.contains(String.valueOf(dynamicCiId)));
					hasCICode = true;
					break;
				}
			}
		}
		assertTrue(hasCICode);
		//检查ci3dPoint
		boolean hasNode = false;
		JSONObject ci3dPoint = new JSONObject(oldParam.getString("ci3dPoint"));
		JSONArray nodes = ci3dPoint.getJSONArray("nodes");
		for (int i=0; i<nodes.length(); i++){
			JSONObject tempObj = nodes.getJSONObject(i);
			if (tempObj.has("code"))
			{
				if (ciCode.compareToIgnoreCase(tempObj.getString("code"))==0){
					assertTrue(tempObj.has("tags-info"));
					assertTrue(tempObj.has("tags-params-info"));
					assertTrue(tempObj.has("tags-selection-cis"));
					assertTrue(tempObj.has("child-num"));
					assertTrue(tempObj.getInt("child-num")>0);
					JSONArray temp = tempObj.getJSONArray("tags-selection-cis");
					List cisList = new ArrayList<>();
					boolean hasChild = false;
					for (int k=0; k<temp.length(); k++){
						String s = temp.getString(k);
						if (s.compareToIgnoreCase(String.valueOf(dynamicCiId))==0){
							hasChild =true;
							break;
						}
					}
					assertTrue(hasChild);
					hasNode = true;
					break;
				}
			}
		}
		assertTrue(hasNode);
	}
	@When("^视图\"(.*)\"中CI\"(.*)\"取消挂载数据$")
	public void ciRemoveMountDynamicNode(String diagramName, String ciCode) throws DocumentException {
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		UpdateDiagramContent ud = new UpdateDiagramContent();
		JSONObject result = ud.updateDiagramContentRemoveMountDynamicNode("ci", diagramName, ciCode);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
	}
	@Then("^视图\"(.*)\"中CI\"(.*)\"成功取消挂载数据$")
	public void chekcCiRemoveMountDynamicNode(String diagramName, String ciCode) throws DocumentException {
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
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
		boolean hasCICode = false;
		List<String> containerList = new ArrayList<String>();
		while (userObjectIters.hasNext()) {
			Element userObjectEle= (Element) userObjectIters.next();
			if (userObjectEle.attribute("code")!=null){
				if (ciCode.compareToIgnoreCase(userObjectEle.attributeValue("code"))==0){
					Element tempMxCell = userObjectEle.element("mxCell");
					assertNull(tempMxCell.attribute("uttags-info"));
					assertNull(tempMxCell.attribute("uttags-params-info"));
					assertNotNull(tempMxCell.attribute("uttags-selection-cis"));
					assertNotNull(tempMxCell.attribute("utchild-num"));
					String childNum = tempMxCell.attributeValue("utchild-num");
					assertTrue(Integer.valueOf(childNum)>0);
					String temp = tempMxCell.attributeValue("uttags-selection-cis");
					assertTrue("[]".compareToIgnoreCase(temp)==0);
					hasCICode = true;
					break;
				}
			}
		}
		assertTrue(hasCICode);
		//检查ci3dPoint
		boolean hasNode = false;
		JSONObject ci3dPoint = new JSONObject(oldParam.getString("ci3dPoint"));
		JSONArray nodes = ci3dPoint.getJSONArray("nodes");
		for (int i=0; i<nodes.length(); i++){
			JSONObject tempObj = nodes.getJSONObject(i);
			if (tempObj.has("code"))
			{
				if (ciCode.compareToIgnoreCase(tempObj.getString("code"))==0){
					assertFalse(tempObj.has("tags-info"));
					assertFalse(tempObj.has("tags-params-info"));
					assertTrue(tempObj.has("tags-selection-cis"));
					assertTrue(tempObj.has("child-num"));
					assertTrue(tempObj.getInt("child-num")>0);
					JSONArray temp = tempObj.getJSONArray("tags-selection-cis");
					assertTrue(temp.length()==0);
					hasNode = true;
					break;
				}
			}
		}
		assertTrue(hasNode);
		
	}

	/******************************右键-挂载、编辑、取消挂载数据***************/
	
    /*======================Scenario Outline:Image_CI_RightClick_CI根据条件挂载数据============*/
	@When("^视图\"(.*)\"中CI\"(.*)\"挂载数据分类为\"(.*)\"属性名为\"(.*)\"的属性值为\"(.*)\"的CI$")
	public void SearchInfoByRuleItem(String diagramName,String ciCode,String className,String attrName,String attrValue){
		SearchInfoByRuleItem searchInfoByRuleItem = new SearchInfoByRuleItem();
		result = searchInfoByRuleItem.searchInfoByRuleItem(className, attrName,attrValue);
		assertTrue(result.getBoolean("success"));
	}
	
	@Then("^视图\"(.*)\"中CI\"(.*)\"成功挂载数据分类为\"(.*)\"属性名为\"(.*)\"的属性值为\"(.*)\"的CI$")
	public void checkSearchInfoByRuleItem(String diagramName,String ciCode,String className,String attrName,String attrValue){
		CiClassUtil  ciClassUtil = new CiClassUtil();
		BigDecimal classId = ciClassUtil.getCiClassId(className);
		BigDecimal attrId = ciClassUtil.getAttrIdByAttrName(className, attrName);
		String sql = "SELECT ID  FROM cc_ci_pro_index WHERE ATTR_ID = " + attrId + " AND CLASS_ID = " + classId + " AND CI_TYPE = 1 AND IDX_0 = '" + attrValue + "' AND DOMAIN_ID =" + QaUtil.domain_id;
		List list = JdbcUtil.executeQuery(sql);
		int totalRows = result.getJSONObject("data").getInt("totalRows");
		JSONArray data = result.getJSONObject("data").getJSONArray("data");
		JSONObject obj = null;
		if(data.length() > 0){
		    obj = (JSONObject)data.get(0);
		}
		assertEquals(list.size(),totalRows);
		String sql1 = "SELECT ID,CLASS_CODE,CLASS_NAME,CLASS_PATH,CI_TYPE,IS_LEAF FROM cc_ci_class WHERE ID = " + classId + " AND DATA_STATUS = 1 AND DOMAIN_ID = " + QaUtil.domain_id;
		List list1 = JdbcUtil.executeQuery(sql1);
		JSONObject ciClass = obj.getJSONObject("ciClass");
		if(list1.size() >0 ){
			HashMap map = (HashMap)list1.get(0);
			assertEquals(map.get("CLASS_CODE"),ciClass.getString("classCode"));
			assertEquals(map.get("CLASS_NAME"),ciClass.getString("className"));
			assertEquals(map.get("CLASS_PATH"),ciClass.getString("classPath"));
			assertEquals(((BigDecimal)map.get("CI_TYPE")).intValue(),ciClass.getInt("ciType"));
			assertEquals(((BigDecimal)map.get("IS_LEAF")).intValue(),ciClass.getInt("isLeaf"));
		}
		
	}
	
	
		

	/******************************右键-设置label位置****************/
	@When("视图\"(.*)\"中CI\"(.*)\"label位置设置为\"(.*)\"$")
	public void ciLabelSetup(String diagramName, String ciCode, String labelPosition) throws DocumentException{
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		UpdateDiagramContent ud = new UpdateDiagramContent();
		JSONObject result = ud.updateDiagramContentCILabelSetup(diagramName, ciCode, labelPosition);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
	}

	@Then("视图\"(.*)\"中CI\"(.*)\"label位置成功设置为\"(.*)\"$")
	public void checkCiLabelSetup(String diagramName, String ciCode, String labelPosition) throws DocumentException{
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
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
		while (userObjectIters.hasNext()) {
			Element userObjectEle= (Element) userObjectIters.next();
			String tempCiCode = userObjectEle.attributeValue("code");
			if (tempCiCode!=null && !tempCiCode.isEmpty())
				if (ciCode.compareToIgnoreCase(tempCiCode)==0)
				{
					Element tempMxCell = userObjectEle.element("mxCell");
					String newStyle =tempMxCell.attributeValue("style");
					if ("上".compareToIgnoreCase(labelPosition)==0){
						assertTrue(newStyle.contains("labelPosition=center;verticalLabelPosition=top;align=center;verticalAlign=bottom;"));
					}
					if ("下".compareToIgnoreCase(labelPosition)==0){
						assertTrue(newStyle.contains("labelPosition=center;verticalLabelPosition=bottom;align=center;verticalAlign=top;"));
					}
					if ("左".compareToIgnoreCase(labelPosition)==0){
						assertTrue(newStyle.contains("labelPosition=left;verticalLabelPosition=middle;align=right;verticalAlign=middle;"));
					}
					if ("右".compareToIgnoreCase(labelPosition)==0){
						assertTrue(newStyle.contains("labelPosition=right;verticalLabelPosition=middle;align=left;verticalAlign=middle;"));
					}
					break;
				}
		}
		//检查svg
		//检查ci3dPoint
		String ci3dPoint = oldParam.getString("ci3dPoint");
	}
	/******************************右键-设置label位置结束****************/




	/******************************右键-添加至容器****************/

	@When("^在视图\"(.*)\"中将CI\"(.*)\"添加至容器$")
	public void ciAddToContainer(String diagramName, String ciCode) throws DocumentException{
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		UpdateDiagramContent ud = new UpdateDiagramContent();
		JSONObject result = ud.updateDiagramContentAddCIToContainer(diagramName, ciCode);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
	}

	@Then("^在视图\"(.*)\"中将CI\"(.*)\"添加至容器成功$")
	public void checkCiAddToContainer(String diagramName, String ciCode) throws DocumentException{
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
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
		boolean hasContainer = false;
		boolean hasCICode = false;
		String parentId = "";
		List<String> containerList = new ArrayList<String>();
		while (userObjectIters.hasNext()) {
			Element userObjectEle= (Element) userObjectIters.next();
			if (userObjectEle.attribute("label")!=null){
				if ("Container".compareToIgnoreCase(userObjectEle.attributeValue("label"))==0){
					String containerId = userObjectEle.attributeValue("id");
					Element tempMxCell = userObjectEle.element("mxCell");
					assertNotNull(tempMxCell.attribute("utchild-num"));
					containerList.add(containerId);
					hasContainer = true;
				}
			}
			if (userObjectEle.attribute("code")!=null){
				if (ciCode.compareToIgnoreCase(userObjectEle.attributeValue("code"))==0){
					Element tempMxCell = userObjectEle.element("mxCell");
					assertNotNull(tempMxCell.attribute("parent"));
					parentId = tempMxCell.attributeValue("parent");
					hasCICode = true;
				}
			}
		}
		assertTrue(hasContainer);
		assertTrue(hasCICode);
		assertTrue(containerList.contains(parentId));

		//检查ci3dPoint
		JSONObject ci3dPoint = new JSONObject(oldParam.getString("ci3dPoint"));
		JSONArray containers = ci3dPoint.getJSONArray("containers");
		assertNotNull(containers);
		assertTrue(containers.length()>=1);

		//检查svg

	}

	/******************************右键-添加至容器结束****************/

	/******************************右键-显示关系****************/
	@When("^在视图\"(.*)\"中\"(.*)\"右键显示到\"(.*)\"的关系\"(.*)\"$")
	public void displayRlt(String diagramName, String sourceCiCode, String targetCiCode, String rltCls) throws DocumentException{
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		UpdateDiagramContent ud = new UpdateDiagramContent();
		JSONObject result = ud.updateDiagramContentHideOrDisplayRlt(diagramName, sourceCiCode, targetCiCode, rltCls, "1");
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
	}

	@Then("^在视图\"(.*)\"中\"(.*)\"右键显示到\"(.*)\"的关系\"(.*)\"成功$")
	public void checkDisplayRlt(String diagramName, String sourceCiCode, String targetCiCode, String rltClassName) throws DocumentException{
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
					assertEquals("1",mxTempEle.attributeValue("showStatus"));
					hasRlt = true;
					break;
				}

			}
		}
		assertTrue(hasRlt);
	}
	/******************************右键-显示关系结束****************/

	/******************************右键-还原关系****************/
	@When("^在视图\"(.*)\"中\"(.*)\"右键还原与\"(.*)\"的关系$")
	public void ciReductionRlt(String diagramName, String ciCode1, String ciCode2) throws DocumentException{
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		UpdateDiagramContent ud = new UpdateDiagramContent();
		JSONObject result = ud.updateDiagramContentCiReductionRlt(diagramName, ciCode1, ciCode2);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
	}

	@Then("^成功在视图\"(.*)\"中\"(.*)\"右键还原与\"(.*)\"的关系$")
	public void checkCiReductionRlt(String diagramName, String ciCode1, String ciCode2) throws DocumentException{
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		JSONObject result = getDiagramInfoByName(diagramName);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
		JSONObject oldParam = result.getJSONObject("data");

		String xml = oldParam.getString("xml");
		String ci3dPoint = oldParam.getString("ci3dPoint");

		XmlUtil xmlUtil = new XmlUtil();
		QueryCiBetweenRlt queryCiBetweenRlt = new QueryCiBetweenRlt();
		JSONObject rltResult = queryCiBetweenRlt.queryMultiCiBetweenRlt(ciCode1,ciCode2);
		assertTrue(rltResult.getBoolean("success"));
		assertEquals(rltResult.getInt("code"), -1);
		assertTrue(rltResult.has("data"));
		JSONArray rltDataArr = rltResult.getJSONArray("data");
		CiClassUtil ciClsUtil = new CiClassUtil();
		String sourceCiCode ="";
		String targetCiCode = "";
		for (int i=0; i<rltDataArr.length(); i++){
			JSONObject ciRlts = (JSONObject) rltDataArr.get(i);
			JSONObject tempObj = ciRlts.getJSONObject("ciRlt");
			sourceCiCode = tempObj.getString("sourceCiCode");
			targetCiCode = tempObj.getString("targetCiCode");
			//检查关系
			assertTrue(xmlUtil.hasRlt(xml, String.valueOf(tempObj.getBigDecimal("id"))));
		}
		assertNotNull(sourceCiCode);
		assertNotNull(targetCiCode);
		String sql ="select Id from  cc_ci_rlt  where SOURCE_CI_CODE='"+sourceCiCode+"' and TARGET_CI_CODE='"+targetCiCode+"' and DATA_STATUS=1 and DOMAIN_ID = "+ QaUtil.domain_id;
		ArrayList list = (ArrayList) JdbcUtil.executeQuery(sql);
		assertEquals(rltDataArr.length(), list.size());

	}

	/******************************右键-查看配置****************/
	@When("^在视图\"(.*)\"中查看配置$")
	public void queryCI(String diagramName){
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		JSONObject diagramResult = getDiagramInfoByName(diagramName);
		assertTrue(diagramResult.getBoolean("success"));
		assertEquals(diagramResult.getInt("code"), -1);
		JSONObject oldParam = diagramResult.getJSONObject("data");
		JSONArray diagramEles = new JSONArray();
		if (oldParam.has("diagramEles")){
			diagramEles = oldParam.getJSONArray("diagramEles");
		}
		JSONArray ciCodes = new JSONArray();
		CiUtil ciu = new CiUtil();
		for (int i=0; i<diagramEles.length(); i++){
			JSONObject eleObj = diagramEles.getJSONObject(i);
			ciCodes.put(ciu.getCiCodeById(eleObj.getBigDecimal("eleId")));
		}
		JSONArray ciQ = new JSONArray();
		ciQ.put("ATTR");
		ciQ.put("CLASS");
		QueryList queryList = new QueryList();
		JSONObject result = queryList.queryList(ciQ,ciCodes);
		assertTrue(result.getBoolean("success"));
		ciCodesArray = result.getJSONArray("data");
		assertTrue(ciCodesArray.length()>=1);
	}

	@Then("^成功在视图\"(.*)\"中查看配置$")
	public void checkQueryCI(String diagramName){
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}

		JSONObject diagramResult = getDiagramInfoByName(diagramName);
		assertTrue(diagramResult.getBoolean("success"));
		assertEquals(diagramResult.getInt("code"), -1);
		JSONObject oldParam = diagramResult.getJSONObject("data");
		JSONArray diagramEles = new JSONArray();
		if (oldParam.has("diagramEles")){
			diagramEles = oldParam.getJSONArray("diagramEles");
		}
		JSONArray ciCodes = new JSONArray();
		String ids ="";
		for (int i=0; i<diagramEles.length(); i++){
			JSONObject eleObj = diagramEles.getJSONObject(i);
			if (ids.isEmpty())
				ids = String.valueOf(eleObj.getBigDecimal("eleId"));
			else
				ids+=","+ String.valueOf(eleObj.getBigDecimal("eleId"));
		}

		String ciSql = "select  ID, CI_CODE, CI_DESC, CLASS_ID, SOURCE_ID, OWNER_ID,    ORG_ID, SUB_CLASS, CI_VERSION, CUSTOM_1, CUSTOM_2, CUSTOM_3,    CUSTOM_4, CUSTOM_5, CUSTOM_6, DOMAIN_ID, DATA_STATUS, CREATOR,    MODIFIER, CREATE_TIME, MODIFY_TIME     from CC_CI     where    ID in ("+ids+")       and  DATA_STATUS=1 and DOMAIN_ID="+QaUtil.domain_id +" order by id";
		List ciList = JdbcUtil.executeQuery(ciSql);
		assertEquals(ciCodesArray.length(), ciList.size());
		String ciClsql = "select ID, CLASS_CODE, CLASS_NAME, CLASS_STD_CODE, DIR_ID, CI_TYPE,CLASS_KIND, PARENT_ID, CLASS_LVL, CLASS_PATH, ORDER_NO, IS_LEAF,    ICON, CLASS_DESC, CLASS_COLOR, DISP_FIELD_IDS, DISP_FIELD_NAMES, COST_TYPE,    LINE_TYPE, LINE_BORDER, LINE_COLOR, LINE_DIRECT, LINE_ANIME, LINE_DISP_TYPE,    LINE_LABEL_ALIGN, DOMAIN_ID, DATA_STATUS, CREATOR, MODIFIER, CREATE_TIME,    MODIFY_TIME " 
				+" from CC_CI_CLASS   " 
				+" where  ID in (select CLASS_ID from CC_CI  where    ID in ("+ids+")  and  DATA_STATUS=1 and DOMAIN_ID="+QaUtil.domain_id +")"// order by id)    "                                                                                                                                                                                                                                      
				+" and    DOMAIN_ID ="+QaUtil.domain_id+"   and    DATA_STATUS = 1 ";// order by ID  ";
		List ciClsList = JdbcUtil.executeQuery(ciClsql);
		assertEquals(ciCodesArray.length(), ciClsList.size());
		for (int i=0; i<ciCodesArray.length(); i++){
			JSONObject tempObj = ciCodesArray.getJSONObject(i);
			JSONObject ciClassObj = tempObj.getJSONObject("ciClass");
			JSONObject ciObj = tempObj.getJSONObject("ci");
			JSONArray attrs = tempObj.getJSONArray("attrs");
			Map dbCiMap = (Map) ciList.get(i);
			Map dbCiClsMap = (Map) ciClsList.get(i);
			//ci
			assertEquals(ciObj.getBigDecimal("id"),dbCiMap.get("ID"));
			assertEquals(ciObj.getBigDecimal("classId"),dbCiMap.get("CLASS_ID"));
			assertEquals(ciObj.getBigDecimal("ownerId"),dbCiMap.get("OWNER_ID"));
			assertEquals(ciObj.getBigDecimal("dataStatus"),dbCiMap.get("DATA_STATUS"));
			assertEquals(ciObj.getBigDecimal("sourceId"),dbCiMap.get("SOURCE_ID"));
			assertEquals(ciObj.getBigDecimal("domainId"),dbCiMap.get("DOMAIN_ID"));
			assertEquals(ciObj.getString("ciCode"),dbCiMap.get("CI_CODE"));
			assertEquals(ciObj.getString("creator"),dbCiMap.get("CREATOR"));
			assertEquals(ciObj.getBigDecimal("createTime"),dbCiMap.get("CREATE_TIME"));
			assertEquals(ciObj.getString("modifier"),dbCiMap.get("MODIFIER"));
			assertEquals(ciObj.getBigDecimal("modifyTime"),dbCiMap.get("MODIFY_TIME"));
			//ciCLs
			assertEquals(ciClassObj.getBigDecimal("id"),dbCiClsMap.get("ID"));
			assertEquals(ciClassObj.getBigDecimal("ciType"),dbCiClsMap.get("CI_TYPE"));
			assertEquals(ciClassObj.getBigDecimal("dirId"),dbCiClsMap.get("DIR_ID"));
			assertEquals(ciClassObj.getBigDecimal("classLvl"),dbCiClsMap.get("CLASS_LVL"));
			assertEquals(ciClassObj.getBigDecimal("isLeaf"),dbCiClsMap.get("IS_LEAF"));
			assertEquals(ciObj.getBigDecimal("dataStatus"),dbCiMap.get("DATA_STATUS"));
			assertEquals(ciClassObj.getBigDecimal("domainId"),dbCiClsMap.get("DOMAIN_ID"));
			assertEquals(ciClassObj.getBigDecimal("parentId"),dbCiClsMap.get("PARENT_ID"));
			assertEquals(ciClassObj.getString("classStdCode"),dbCiClsMap.get("CLASS_STD_CODE"));
			assertEquals(ciClassObj.getString("classCode"),dbCiClsMap.get("CLASS_CODE"));
			assertEquals(ciClassObj.getString("classColor"),dbCiClsMap.get("CLASS_COLOR"));
			assertEquals(ciClassObj.getString("className"),dbCiClsMap.get("CLASS_NAME"));
			assertEquals(ciClassObj.getString("classPath"),dbCiClsMap.get("CLASS_PATH"));
			if(dbCiClsMap.get("CLASS_DESC") != null){
			    assertEquals(ciClassObj.getString("classDesc"),dbCiClsMap.get("CLASS_DESC"));
			}
			assertTrue(ciClassObj.getString("icon").contains((CharSequence) dbCiClsMap.get("ICON")));
			assertEquals(ciClassObj.getString("creator"),dbCiClsMap.get("CREATOR"));
			assertEquals(ciClassObj.getBigDecimal("createTime"),dbCiClsMap.get("CREATE_TIME"));
			assertEquals(ciClassObj.getString("modifier"),dbCiClsMap.get("MODIFIER"));
			assertEquals(ciClassObj.getBigDecimal("modifyTime"),dbCiClsMap.get("MODIFY_TIME"));
			//attrs没做比较
		}
	}
	/******************************右键-查看配置结束****************/
	/*=============Scenario Outline: Image_CI_创建关系================*/
	@When("^在\"(.*)\"关系下,创建源分类为\"(.*)\"源对象为\"(.*)\"到目标分类为\"(.*)\",目标对象为\"(.*)\"的关系$")
	public void queryRltInfoList(String rltClassName,String sourceCiClass,String sourceCiCode, String targetCiClass,String targetCiCode){
		QueryRltInfoList  queryRltInfoList = new QueryRltInfoList();
		result = queryRltInfoList.queryRltInfoList(rltClassName,sourceCiClass, sourceCiCode, targetCiClass,targetCiCode);
		assertTrue(result.getBoolean("success"));
	}
	
	@Then("^成功在\"(.*)\"关系下,创建源分类为\"(.*)\"源对象为\"(.*)\"到目标分类为\"(.*)\",目标对象为\"(.*)\"的关系$")
	public void checkQueryRltInfoList(String rltClassName,String sourceCiClass,String sourceCiCode,String targetCiClass,String targetCiCode){
		String sql1 = "SELECT ID, PRO_NAME from  cc_ci_attr_def where CLASS_ID in (SELECT ID from cc_ci_class where CLASS_NAME ='"+rltClassName +"' and CI_TYPE = '2' and DATA_STATUS = 1 AND DOMAIN_ID = " + QaUtil.domain_id + ") and DATA_STATUS = 1 AND DOMAIN_ID = " + QaUtil.domain_id; 
		String sql2 = "SELECT ID, IV_0,IV_1 from cc_ci_int_attr_0 where CLASS_ID in (SELECT ID from cc_ci_class where CLASS_NAME ='"+rltClassName +"' and CI_TYPE = '2' and DATA_STATUS = 1 and DOMAIN_ID =" + QaUtil.domain_id + ") and CI_TYPE ='2' and DATA_STATUS =1  and DOMAIN_ID =" + QaUtil.domain_id;
		List list1 = JdbcUtil.executeQuery(sql1);
		List list2 = JdbcUtil.executeQuery(sql2);
		JSONObject dbObj = new JSONObject();
		HashMap map1 = new HashMap();
		HashMap map2 = new HashMap();
		HashMap map3 = new HashMap();
		if(list1 != null && list1.size() > 0){
			 map1 = (HashMap)list1.get(0);
			 map2 = (HashMap)list1.get(1);
		}
		if(list2 != null && list2.size() >0){
			 map3 = (HashMap)list2.get(0);
		}
		dbObj.put((String)map1.get("PRO_NAME"), map3.get("IV_0"));
		dbObj.put((String)map2.get("PRO_NAME"), map3.get("IV_1"));
		JSONArray data = result.getJSONArray("data");
		BigDecimal rltClsId =  (new RltClassUtil()).getRltClassId(rltClassName); 
		BigDecimal sourceCiClassId = (new CiClassUtil()).getCiClassId(sourceCiClass);
		BigDecimal targetCiClassId = (new CiClassUtil()).getCiClassId(targetCiClass);
		BigDecimal sourceCiId = (new SearchInfoList()).searchInfoList_getSingleCiId(sourceCiClass, sourceCiCode);
		BigDecimal targetCiId = (new SearchInfoList()).searchInfoList_getSingleCiId(targetCiClass, targetCiCode); 
		if(data.length() > 0){
			JSONObject obj = (JSONObject)data.get(0);
			JSONObject attrs = obj.getJSONObject("attrs");
			assertEquals(dbObj.get("源端口").toString(),attrs.get("源端口"));
			assertEquals(dbObj.get("目标端口").toString(),attrs.get("目标端口"));
			JSONObject ciRlt = obj.getJSONObject("ciRlt");
			assertEquals(sourceCiClassId,new BigDecimal((Long)ciRlt.get("sourceClassId")));
			assertEquals(targetCiClassId,new BigDecimal((Long)ciRlt.get("targetClassId")));
			assertEquals(sourceCiId,new BigDecimal((Long)ciRlt.get("sourceCiId")));
			assertEquals(targetCiId,new BigDecimal((Long)ciRlt.get("targetCiId")));
			assertEquals(sourceCiCode,ciRlt.get("sourceCiCode"));
			assertEquals(targetCiCode,ciRlt.get("targetCiCode"));
			JSONObject rltClassInfo = obj.getJSONObject("rltClassInfo");
			JSONArray attrDefs = rltClassInfo.getJSONArray("attrDefs");
			if(attrDefs.length() > 0){
			    JSONObject attrDefsObj1 = (JSONObject)attrDefs.get(0);
			    assertEquals((String)map1.get("PRO_NAME"),attrDefsObj1.get("proName"));
			    JSONObject attrDefsObj2 = (JSONObject)attrDefs.get(1);
			    assertEquals((String)map2.get("PRO_NAME"),attrDefsObj2.get("proName"));
			}
			JSONObject ciClass = rltClassInfo.getJSONObject("ciClass");
			assertEquals(rltClsId,new BigDecimal((Long)ciClass.get("id")));
			assertEquals(rltClassName,ciClass.get("className"));
			assertEquals(2,ciClass.get("ciType"));
		}
	}
	
	/*====Scenario Outline: Image_CI_RightClick_下钻视图的告警传递到当前视图=======*/
	@When("^下钻视图的告警传递到\"(.*)\"视图$")
	public void queryDigramInfoAndEventByIds(@Delimiter(",")List<String> diagramNameList){
		QueryDigramInfoAndEventByIds queryDigramInfoAndEventByIds = new QueryDigramInfoAndEventByIds();
		result = queryDigramInfoAndEventByIds.queryDigramInfoAndEventByIds(diagramNameList);
		assertTrue(result.getBoolean("success"));
	}
	
	@Then("^下钻视图的CI\"(.*)\"告警成功传递到\"(.*)\"视图$")
	public void checkQueryDigramInfoAndEventByIds(String ciCode,@Delimiter(",") List<String>diagramNameList){
		String sql = "SELECT IDENTIFIER,SEVERITY, CINAME,TALLY FROM MON_EAP_EVENT_MEMORY WHERE CINAME in ('" + ciCode + "') AND STATUS = 1";
		List list = JdbcUtil.executeQuery(sql);
		JSONArray data = result.getJSONArray("data");
		DiagramUtil diagramUtil = new DiagramUtil();
		HashMap map = new HashMap();
		if(list.size()>0){
			map = (HashMap)list.get(0);
		}
		String diagramIds = "";
		for(int i = 0; i < diagramNameList.size();i++){
			String diagramName = diagramNameList.get(i);
			BigDecimal diagramId = diagramUtil.getDiagramIdByName(diagramName);
			diagramIds = diagramIds + diagramId;
		}
		
		for(int j = 0; j < data.length();j++){
			JSONObject obj = (JSONObject)data.get(j);
			String diagramIdstr = obj.getBigDecimal("diagramId").toString();
			if(diagramIds.contains(diagramIdstr)){
				assertTrue(true);
			}
			JSONArray eventStatistics = obj.getJSONArray("eventStatistics");
			int totalCount = obj.getInt("totalCount");
			assertEquals(map.get("TALLY").toString(),new Integer(totalCount).toString());
			if(eventStatistics.length()>0){
				 JSONObject eventStatisticsObj = (JSONObject)eventStatistics.get(0);
				 assertEquals(map.get("CINAME"),eventStatisticsObj.getString("ciCode"));
				 
				 assertEquals(map.get("SEVERITY").toString(),new Integer(eventStatisticsObj.getInt("severity")).toString());
			 }
		}
		
	}
	
	/*===========Scenario: Image_CI_RightClick_编辑CI==========*/
	@And("^给角色\"(.*)\"的分类\"(.*)\"设置编辑权限$")
	public void saveRoleDataAuth(String roleName,String className){
		QueryRoleAllDataAuth queryRoleAllDataAuth = new QueryRoleAllDataAuth();
		result = queryRoleAllDataAuth.queryRoleAllDataAuth(roleName);
		JSONObject data = result.getJSONObject("data");
		JSONArray dataAuths = data.getJSONArray("dataAuths");
		SaveRoleDataAuth saveRoleDataAuth = new SaveRoleDataAuth();
		result = saveRoleDataAuth.saveRoleDataAuth(roleName, className, dataAuths);
		assertTrue(result.getBoolean("success"));
	}
	
	@When("^查询角色所有数据权限$")
	public void queryUserAllDataAuth(){
		QueryUserAllDataAuth queryUserAllDataAuth = new QueryUserAllDataAuth();
		result = queryUserAllDataAuth.queryUserAllDataAuth();
		assertTrue(result.getBoolean("success"));		
	}
	
	@Then("^分类\"(.*)\"的权限为编辑权限$")
	public void checkQueryUserAllDataAuth(String className){
		CiClassUtil ciClassUtil = new CiClassUtil();
		BigDecimal classId = ciClassUtil.getCiClassId(className);
		String sql = "SELECT DATA_RES_ID,AUTH_TYPE FROM cc_ci_role_data where DATA_RES_ID =" + classId + " AND DATA_STATUS =1";
		List list = JdbcUtil.executeQuery(sql);
		HashMap map = new HashMap();
		if(list.size() > 0){
			map = (HashMap)list.get(0);
		}
		//JSONObject data = result.getJSONObject("data");
		JSONArray dataAuths = result.getJSONArray("data");
		for(int i = 0; i < dataAuths.length();i++){
			JSONObject obj = (JSONObject)dataAuths.get(i);
			if(classId.compareTo(obj.getBigDecimal("id"))==0){
				if (((BigDecimal)map.get("AUTH_TYPE")).compareTo(new BigDecimal(110))==0){
				   assertTrue(obj.getBoolean("edit"));
				}
			}
		}
	}
	
	
	@When("^在视图\"(.*)\"中右键编辑分类为\"(.*)\"的CI\"(.*)\"的属性\"(.*)\"的值为\"(.*)\"$")
	public void saveOrUpdateEditCi(String diagramName,String ciClassName,String ciCode,String proName,String proValue){
		QueryById  queryById = new QueryById();
		result = queryById.queryById(ciCode);
		JSONArray attrs = result.getJSONObject("data").getJSONArray("attrs");
		SaveOrUpdate saveOrUpdate = new SaveOrUpdate();
		result = saveOrUpdate.saveOrUpdateEditCi(ciClassName, attrs, ciCode,proName,proValue);
		assertTrue(result.getBoolean("success"));
	}
	
	@Then("^在视图\"(.*)\"中右键成功编辑分类为\"(.*)\"的CI\"(.*)\"的属性\"(.*)\"的值为\"(.*)\"$")
	public void checkSaveOrUpdateEditCi(String diagramName,String ciClassName,String ciCode,String proName,String proValue){
		QueryById queryById = new QueryById();
		result = queryById.queryById(ciCode);
		JSONArray attrs = result.getJSONObject("data").getJSONArray("attrs");
		for(int i = 0; i < attrs.length(); i ++){
		   JSONObject obj = (JSONObject)attrs.get(i);
		   if(proName.equals(obj.getString("key"))){
			   assertEquals(proValue,obj.getString("value"));
			   break;
		   }
		}
	}

	@And("^删除角色\"(.*)\"的数据设置$")
	public void removeRoleDataAuth(String roleName){
		AuthUtil authUtil = new AuthUtil();
		String roleId = authUtil.getRoleId(roleName);
		String sql = "UPDATE cc_ci_role_data SET DATA_STATUS = 0 WHERE ROLE_ID = " + new BigDecimal(roleId) + " AND DATA_STATUS =1";
		JdbcUtil.executeUpdate(sql);
	}
	
   /*====Scenario Outline: Image_CI_RightClick_查关联视图告警====*/
   @When("^在视图\"(.*)\"中添加下钻视图\"(.*)\"$")
   public void saveOrUpdateDiagram(String diagramName,@Delimiter(",")List<String> diagramNameList){
	   DiagramUtil diagramUtil = new DiagramUtil();
	   BigDecimal diagramId = diagramUtil.getDiagramIdByName(diagramName);
	   QueryDiagramInfoById queryDiagramInfoById = new QueryDiagramInfoById();
	   result = queryDiagramInfoById.queryDiagramInfoById(diagramName, true);
	   SaveOrUpdateDiagram saveOrUpdateDiagram = new SaveOrUpdateDiagram();
	   result = saveOrUpdateDiagram.saveOrUpdateDiagram(result,diagramNameList);
	   assertTrue(result.getBoolean("success"));
   }
   
   @Then("^在视图\"(.*)\"中成功添加下钻视图\"(.*)\"$")
   public void queryRelatedDiagramEventByDiagramId(String diagramName,@Delimiter(",")List<String> diagramNameList){
	   QueryRelatedDiagramEventByDiagramId  qrdebd = new QueryRelatedDiagramEventByDiagramId();
	   result = qrdebd.queryRelatedDiagramEventByDiagramId(diagramName);
	   JSONArray data = result.getJSONArray("data");
	   DiagramUtil diagramUtil = new DiagramUtil();
	   String BigDecimalIdStr = "";
	   for(int j = 0; j < diagramNameList.size(); j++){
		   String name = diagramNameList.get(j);
		   BigDecimal diagramId = diagramUtil.getDiagramIdByName(name);
		   BigDecimalIdStr = BigDecimalIdStr + diagramId.toString();
	   }
	   for(int i = 0; i < data.length(); i ++){
		   JSONObject obj = data.getJSONObject(i);
		   if(BigDecimalIdStr.contains(obj.getBigDecimal("diagramId").toString())){
			   assertTrue(true);
		   }
		   //assertEquals(3,obj.getInt("maxEventLevel"));
	   }
   }
}
