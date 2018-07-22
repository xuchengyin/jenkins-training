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

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.contant.Contants;
import com.uinnova.test.step_definitions.api.dmv.ci.QueryPageByIndex;
import com.uinnova.test.step_definitions.api.dmv.diagram.RemoveDiagramRuleById;
import com.uinnova.test.step_definitions.api.dmv.diagram.RemoveDirByIds;
import com.uinnova.test.step_definitions.api.dmv.diagram.SaveOrUpdateDiagram;
import com.uinnova.test.step_definitions.api.dmv.diagram.SaveOrUpdateDiagramRule;
import com.uinnova.test.step_definitions.api.dmv.diagram.UpdateDiagramContent;
import com.uinnova.test.step_definitions.api.dmv.sys.vframe.GetAuthMenuTreeByRootModuCode;
import com.uinnova.test.step_definitions.utils.base.CiUtil;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;
import com.uinnova.test.step_definitions.utils.dmv.DiagramUtil;

import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author wsl
 * 新建视图
 *
 */
public class Scenario_image extends Scenario_image_Base{
	private String diagramNameValue; //给新建的时候不提供视图名称的情况使用
	private List<String> diagramNameList = new ArrayList<String>(); //用于记录一共新建了多少视图， 用户After方法清理数据
	private JSONObject result;
	@After("@delDiagram")
	public void delDiagram(){
		if (!diagramNameList.isEmpty()){
			for (int i=0; i<diagramNameList.size(); i++){
				String diagramName = diagramNameList.get(i);
				RemoveDirByIds rd = new RemoveDirByIds();
				JSONObject result = rd.removeDirByIds(diagramName);
				assertTrue(result.getBoolean("success"));
				diagramNameList.remove(diagramName);
				i--;
			}
		}
	}

	@Given("存在视图名为\"(.*)\"的视图")
	public void checkDiagramIsExists(String diagramName){
		DiagramUtil diagramUtil = new DiagramUtil();
		BigDecimal diagramId = diagramUtil.getDiagramIdByName(diagramName);
		if (diagramId == new BigDecimal(0))
		{
			createDiagram(diagramName, "", "");
		}else
			assertTrue(true);
	}

	/********************************新建删除视图***************************************/
	@When("新建视图\"(.*)\",描述为\"(.*)\",文件夹为\"(.*)\"$")
	public void createDiagram(String diagramName, String diagramDesc, String dirName){
		SaveOrUpdateDiagram su = new SaveOrUpdateDiagram();
		JSONObject result = su.createNewDiagram(diagramName, diagramDesc, dirName);
		assertTrue(result.getBoolean("success"));
		assertTrue(result.getBigDecimal("data").compareTo(new BigDecimal(1))>0);
		BigDecimal diagramId = result.getBigDecimal("data");
		assertTrue(diagramId.compareTo(new BigDecimal(0))>0);

		diagramNameValue = diagramName;
		if (diagramNameValue.isEmpty()){
			DiagramUtil diagramUtil = new DiagramUtil();
			diagramNameValue = diagramUtil.getDiagramNameById(diagramId);
		}
		diagramNameList.add(diagramNameValue);
	}
	
	@And("^再次新建视图\"(.*)\",描述为\"(.*)\",文件夹为\"(.*)\"失败,kw=\"(.*)\"$")
	public void againCreateDiagram(String diagramName, String diagramDesc, String dirName, String kw){
		SaveOrUpdateDiagram su = new SaveOrUpdateDiagram();
		JSONObject result = su.createNewDiagramAgain(diagramName, diagramDesc, dirName, kw);
		assertEquals(null,result);
	}
	
	@Then("^成功新建视图\"(.*)\"$")
	public void checkCreateDiagram(String diagramName){
		if (diagramName.isEmpty() && diagramNameValue.isEmpty()){
			fail("视图名称为空");
		}
		if (diagramName.isEmpty()){
			diagramName = diagramNameValue;
		}
		String sql = "select ID from vc_diagram where DATA_STATUS=1 AND STATUS=1 AND USER_ID ="+ QaUtil.user_id + " AND DOMAIN_ID = "+ QaUtil.domain_id+" AND NAME ='" +diagramName +"'";
		ArrayList list = (ArrayList) JdbcUtil.executeQuery(sql);
		assertEquals(1, list.size());
		JSONObject result = getDiagramInfoByName(diagramName);
		assertTrue(result.getBoolean("success"));
	}

	@When("^删除名称为\"(.*)\"的视图$")
	public void deleteDiagram(String diagramName) {
		if (diagramName.isEmpty() && diagramNameValue.isEmpty()){
			fail("没有设置要删除的视图");
		}
		if (diagramName.isEmpty()){
			diagramName = diagramNameValue;
		}
		RemoveDirByIds rd = new RemoveDirByIds();
		JSONObject result = rd.removeDirByIds(diagramName);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
		diagramNameList.remove(diagramName);
	}

	@Then("^成功删除名称为\"(.*)\"的视图$")
	public void checkDeleteDiagram(String diagramName) {
		if (diagramName.isEmpty() && diagramNameValue.isEmpty()){
			fail("没有设置要删除的视图名称");
		}
		if (diagramName.isEmpty()){
			diagramName = diagramNameValue;
		}
		DiagramUtil dUtil = new DiagramUtil();
		BigDecimal diagramID = dUtil.getDiagramIdByName(diagramName);
		assertEquals(diagramID, new BigDecimal(0));
	}
	/********************************新建删除视图***************************************/


	@When("^保存视图\"(.*)\"$")
	public void saveDiagram(String diagramName){
		if (diagramName.isEmpty() && diagramNameValue.isEmpty()){
			fail("视图名称为空");
		}
		if (diagramName.isEmpty()){
			diagramName = diagramNameValue;
		}
		JSONObject result = getDiagramInfoByName(diagramName);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
		JSONObject oldParam = result.getJSONObject("data");
		oldParam.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
		SaveOrUpdateDiagram su = new SaveOrUpdateDiagram();
		JSONObject newResult = su.saveOrUpdateDiagram(oldParam);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
	}


	@Then("^成功保存视图\"(.*)\"$")
	public void checkSaveDiagram(String diagramName){
		if (diagramName.isEmpty() && diagramNameValue.isEmpty()){
			fail("视图名称为空");
		}
		if (diagramName.isEmpty()){
			diagramName = diagramNameValue;
		}
		String sql = "select * from vc_diagram where DATA_STATUS=1 and status=1 and domain_id="+QaUtil.domain_id  +" and NAME ='" +diagramName+"'";
		ArrayList list = (ArrayList) JdbcUtil.executeQuery(sql);
		assertEquals(1, list.size());
	}

	@When("^另存\"(.*)\"为新名称\"(.*)\",描述为\"(.*)\",文件夹为\"(.*)\"$")
	public void saveAsDiagram(String diagramName, String newDiagranName, String diagramDesc, String dirName){
		if (diagramName.isEmpty() && diagramNameValue.isEmpty()){
			fail("原视图不存在");
		}
		if (diagramName.isEmpty()){
			diagramName = diagramNameValue;
		}
		if (newDiagranName.isEmpty()){
			fail("另存为视图名称不能为空");
		}
		JSONObject result = getDiagramInfoByName(diagramName);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
		JSONObject oldParam = result.getJSONObject("data");
		JSONObject oldDiagram = oldParam.getJSONObject("diagram");
		DiagramUtil dUtil = new DiagramUtil();
		int dirID = dUtil.getDirIdByName(dirName);
		JSONObject param = oldParam;
		JSONObject diagram = oldDiagram;
		diagram.put("diagramDesc", diagramDesc);
		diagram.put("dirId", dirID);
		diagram.put("name", newDiagranName);
		param.put("diagram", diagram);
		param.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
		SaveOrUpdateDiagram su = new SaveOrUpdateDiagram();
		JSONObject resultNew = su.saveOrUpdateDiagram(param);
		assertTrue(resultNew.getBoolean("success"));
		diagramNameList.add(newDiagranName);
	}

	@Then("^成功另存视图为\"(.*)\",描述为\"(.*)\",文件夹为\"(.*)\"$")
	public void checkSaveAsDiagram(String newDiagranName, String diagramDesc, String dirName){
		if (newDiagranName.isEmpty()){
			fail("新视图名称不能为空");
		}
		String sql = "select * from vc_diagram where DATA_STATUS=1 and STATUS=1 and DOMAIN_ID = "+ QaUtil.domain_id+" and NAME ='" +newDiagranName+"'";
		ArrayList list = (ArrayList) JdbcUtil.executeQuery(sql);
		assertEquals(1, list.size());
		HashMap  map = (HashMap) list.get(0);
		assertEquals(newDiagranName, map.get("NAME"));
		assertEquals(diagramDesc, map.get("DIAGRAM_DESC"));
		DiagramUtil dUtil = new DiagramUtil();
		int dirID = dUtil.getDirIdByName(dirName);
		assertEquals(new BigDecimal(dirID), map.get("DIR_ID"));
	}

	/**********************给视图增加CI、删除CI**************************/ 
	@When("^给视图\"(.*)\"增加CI\"(.*)\"坐标为\"(.*)\"\"(.*)\"$")
	public void updateDiagramContentAddCI(String diagramName,String ciCode, String x, String y) throws DocumentException, InterruptedException{
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		UpdateDiagramContent ud = new UpdateDiagramContent();
		JSONObject result = ud.updateDiagramContentAddCI(diagramName, ciCode, x, y);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^成功给视图\"(.*)\"增加CI\"(.*)\"坐标为\"(.*)\"\"(.*)\"$")
	public void checkUpdateDiagramContentAddCI(String diagramName,String ciCode, String x, String y) throws DocumentException{
		if (diagramName.isEmpty()){
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
		BigDecimal ciId = ciUtil.getCiId(ciCode);
		for (int i=0; i<diagramEles.length(); i++){
			JSONObject tmp = (JSONObject) diagramEles.get(i);
			if (tmp.getBigDecimal("eleId").compareTo(ciId)==0){
				hasFlag = true;
				break;
			}
		}
		assertTrue(hasFlag);

		BigDecimal diagramId = oldParam.getJSONObject("diagram").getBigDecimal("id");
		String sql ="select Id from vc_diagram_ele where ele_Id="+ciId+" and diagram_ID="+diagramId +" and DOMAIN_ID = "+ QaUtil.domain_id;
		ArrayList list = (ArrayList) JdbcUtil.executeQuery(sql);
		assertEquals(1, list.size());

		String xml = oldParam.getString("xml");
		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement();            
		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); 
		// 遍历UserObject节点
		boolean hasUserObject = false;
		while (userObjectIters.hasNext()) {
			Element userObjectEle= (Element) userObjectIters.next();
			if (userObjectEle.attribute("code")!=null){
				if (ciCode.compareToIgnoreCase(userObjectEle.attributeValue("code"))==0){
					hasUserObject = true;
					break;
				}
			}
		}
		assertTrue(hasUserObject);

		//检查ci3dPoint
		JSONObject ci3dPoint = new JSONObject(oldParam.getString("ci3dPoint"));
		JSONArray nodes = ci3dPoint.getJSONArray("nodes");
		assertNotNull(nodes);
		assertTrue(nodes.length()>0);
		boolean hasNode = false;
		for (int i=0; i<nodes.length(); i++){
			JSONObject tempObj = nodes.getJSONObject(i);
			if (tempObj.has("code"))
			{
				if (ciCode.compareToIgnoreCase(tempObj.getString("code"))==0){
					hasNode = true;
					break;
				}
			}

		}
		assertTrue(hasNode);

		//检查svg
		//String svg = oldParam.getString("svg");
	}

	@When("^给视图\"(.*)\"删除CI\"(.*)\"$")
	public void updateDiagramContentRemoveCI(String diagramName, String ciCode) throws DocumentException{
		if (diagramName.isEmpty()){
			fail("画布视图不存在");
		}
		UpdateDiagramContent ud = new UpdateDiagramContent();
		JSONObject result = ud.updateDiagramContentRemoveCI(diagramName, ciCode);
		assertTrue(result.getBoolean("success"));
	}

	@When("^给视图\"(.*)\"删除CI\"(.*)\"成功$")
	public void checkUpdateDiagramContentRemoveCI(String diagramName, String ciCode) throws DocumentException{
		if (diagramName.isEmpty()){
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
		BigDecimal ciId = ciUtil.getCiId(ciCode);
		for (int i=0; i<diagramEles.length(); i++){
			JSONObject tmp = (JSONObject) diagramEles.get(i);
			if (tmp.getBigDecimal("eleId") == ciId){
				hasFlag = true;
				break;
			}
		}
		assertFalse(hasFlag);

		BigDecimal diagramId = oldParam.getJSONObject("diagram").getBigDecimal("id");
		String sql ="select Id from vc_diagram_ele where ele_Id="+ciId+" and diagram_ID="+diagramId+" and DOMAIN_ID = "+ QaUtil.domain_id;
		ArrayList list = (ArrayList) JdbcUtil.executeQuery(sql);
		assertEquals(0, list.size());

		String xml = oldParam.getString("xml");
		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement();            
		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); 
		// 遍历UserObject节点
		boolean hasUserObject = false;
		while (userObjectIters.hasNext()) {
			Element userObjectEle= (Element) userObjectIters.next();
			if (userObjectEle.attribute("code")!=null){
				if (ciCode.compareToIgnoreCase(userObjectEle.attributeValue("code"))==0){
					hasUserObject = true;
					break;
				}
			}
		}
		assertFalse(hasUserObject);

		//检查ci3dPoint
		boolean hasNode = false;
		JSONObject ci3dPoint = new JSONObject(oldParam.getString("ci3dPoint"));
		JSONArray nodes = ci3dPoint.getJSONArray("nodes");
		for (int i=0; i<nodes.length(); i++){
			JSONObject tempObj = nodes.getJSONObject(i);
			if (tempObj.has("code"))
			{
				if (ciCode.compareToIgnoreCase(tempObj.getString("code"))==0){
					hasNode = true;
					break;
				}
			}
		}
		assertFalse(hasNode);
		//检查svg
		//String svg = oldParam.getString("svg");
	}

	/**********************给视图增加CI、删除CI结束**************************/ 
	
	/**********************给视图增加多个CI
	 * @throws DocumentException **************************/ 

	@When("^给视图\"(.*)\"增加如下CI:$")
	public void addCITable(String diagramName,DataTable table) throws DocumentException {
		UpdateDiagramContent ud = new UpdateDiagramContent();
		int rows = table.raw().size();
		for(int i=1;i<rows;i++){
			List<String> row = table.raw().get(i);
			JSONObject result = ud.updateDiagramContentAddCI(diagramName, row.get(0),row.get(1), row.get(2));
			assertTrue(result.getBoolean("success"));
		}
	}
		
	@Then("^成功给视图\"(.*)\"增加如下CI:$")
	public void checkAddCITable(String diagramName,DataTable table) throws DocumentException {
		JSONObject result = getDiagramInfoByName(diagramName);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
		JSONObject oldParam = result.getJSONObject("data");
		JSONArray diagramEles = new JSONArray();
		//检查diagramEles
		if(oldParam.has("diagramEles")){
			diagramEles = (JSONArray) oldParam.get("diagramEles");
		}
		
		int rows = table.raw().size();
		for(int i=1;i<rows;i++){
			List<String> row = table.raw().get(i);
			String ciCode= row.get(0);
			boolean hasFlag = false;
			CiUtil ciUtil = new CiUtil();
			BigDecimal ciId = ciUtil.getCiId(ciCode);
			for (int j=0; j<diagramEles.length(); j++){
				JSONObject tmp = (JSONObject) diagramEles.get(j);
				if (tmp.getBigDecimal("eleId").compareTo(ciId)==0){
					hasFlag = true;
					break;
				}
			}
			assertTrue(hasFlag);

			BigDecimal diagramId = oldParam.getJSONObject("diagram").getBigDecimal("id");
			String sql ="select Id from vc_diagram_ele where ele_Id="+ciId+" and diagram_ID="+diagramId +" and DOMAIN_ID = "+ QaUtil.domain_id;
			ArrayList list = (ArrayList) JdbcUtil.executeQuery(sql);
			assertEquals(1, list.size());

			String xml = oldParam.getString("xml");
			Document doc = DocumentHelper.parseText(xml); 
			Element graphElement = doc.getRootElement();            
			Element rootElement  = graphElement.element("root");
			Iterator userObjectIters = rootElement.elementIterator("UserObject"); 
			// 遍历UserObject节点
			boolean hasUserObject = false;
			while (userObjectIters.hasNext()) {
				Element userObjectEle= (Element) userObjectIters.next();
				if (userObjectEle.attribute("code")!=null){
					if (ciCode.compareToIgnoreCase(userObjectEle.attributeValue("code"))==0){
						hasUserObject = true;
						break;
					}
				}
			}
			assertTrue(hasUserObject);

			//检查ci3dPoint
			JSONObject ci3dPoint = new JSONObject(oldParam.getString("ci3dPoint"));
			JSONArray nodes = ci3dPoint.getJSONArray("nodes");
			assertNotNull(nodes);
			assertTrue(nodes.length()>0);
			boolean hasNode = false;
			for (int k=0; k<nodes.length(); k++){
				JSONObject tempObj = nodes.getJSONObject(k);
				if (tempObj.has("code"))
				{
					if (ciCode.compareToIgnoreCase(tempObj.getString("code"))==0){
						hasNode = true;
						break;
					}
				}

			}
			assertTrue(hasNode);
		}
	}
		    	
		    	
    /**********************给视图增加多个CI结束**************************/ 
	/************************画布-右键-清空画布************************************/
	@When("^视图\"(.*)\"右键清空画布$")
	public void clearCanvas(String diagramName){
		if (diagramName.isEmpty()){
			fail("画布视图不存在");
		}
		UpdateDiagramContent ud = new UpdateDiagramContent();
		JSONObject result = ud.updateDiagramContentClearCanvas(diagramName);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^视图\"(.*)\"清空画布成功$")
	public void checkClearCanvas(String diagramName){
		if (diagramName.isEmpty()){
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
		assertTrue(diagramEles.length()==0);

		BigDecimal diagramId = oldParam.getJSONObject("diagram").getBigDecimal("id");
		String sql ="select Id from vc_diagram_ele where diagram_ID="+diagramId+" and DOMAIN_ID = "+ QaUtil.domain_id;
		ArrayList list = (ArrayList) JdbcUtil.executeQuery(sql);
		assertEquals(0, list.size());
	}

	/************************画布-右键-清空画布结束************************************/

	/************************画布-右键-挂载、编辑、取消动态节点************************************/

	@When("^视图\"(.*)\"中画布挂载动态节点$")
	public void ciMountDynamicNode(String diagramName, DataTable table) throws DocumentException {
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		List<String> dynamicCiCodeList = new ArrayList<String>();
		int rows = table.raw().size();
		for (int i=1;i<rows;i++){
			dynamicCiCodeList.add(table.raw().get(i).get(1));
		}


		UpdateDiagramContent ud = new UpdateDiagramContent();
		JSONObject result = ud.updateDiagramContentMountDynamicNode("canvas", diagramName, "", dynamicCiCodeList);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^视图\"(.*)\"中画布成功挂载动态节点$")
	public void chekcCiMountDynamicNode(String diagramName, DataTable table) throws DocumentException {
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		JSONObject result = getDiagramInfoByName(diagramName);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
		JSONObject oldParam = result.getJSONObject("data");
		CiUtil ciUtil = new CiUtil();
		int rows = table.raw().size();
		for (int r=1;r<rows;r++){
			String dynamicCiCode = table.raw().get(r).get(1);
			BigDecimal dynamicCiId = ciUtil.getCiId(dynamicCiCode);
			//检查xml
			String xml = oldParam.getString("xml");
			Document doc = DocumentHelper.parseText(xml); 
			Element graphElement = doc.getRootElement();            
			Element rootElement  = graphElement.element("root");
			List<Element> eleList = rootElement.elements();
			for (int i=0; i<eleList.size(); i++){
				Element tempEle  =eleList.get(i);
				if ("1".compareToIgnoreCase(tempEle.attributeValue("id")) ==0){
					assertNotNull(tempEle.attribute("uttags-info"));
					assertNotNull(tempEle.attribute("uttags-params-info"));
					assertNotNull(tempEle.attribute("uttags-selection-cis"));
					assertTrue(tempEle.attributeValue("uttags-selection-cis").contains(String.valueOf(dynamicCiId)));
					break;
				}
			}
		}

	}
	@When("^视图\"(.*)\"中画布取消动态节点$")
	public void canvasRemoveMountDynamicNode(String diagramName) throws DocumentException {
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		UpdateDiagramContent ud = new UpdateDiagramContent();
		JSONObject result = ud.updateDiagramContentRemoveMountDynamicNode("canvas", diagramName, "");
		assertTrue(result.getBoolean("success"));
	}

	@Then("^视图\"(.*)\"中画布成功取消动态节点$")
	public void chekcCanvasRemoveMountDynamicNode(String diagramName) throws DocumentException {
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		JSONObject result = getDiagramInfoByName(diagramName);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
		JSONObject oldParam = result.getJSONObject("data");
		CiUtil ciUtil = new CiUtil();


		//检查xml
		String xml = oldParam.getString("xml");
		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement();            
		Element rootElement  = graphElement.element("root");
		List<Element> eleList = rootElement.elements();
		for (int i=0; i<eleList.size(); i++){
			Element tempEle  =eleList.get(i);
			if ("1".compareToIgnoreCase(tempEle.attributeValue("id")) ==0){
				assertNull(tempEle.attribute("uttags-info"));
				assertNull(tempEle.attribute("uttags-params-info"));
				assertNotNull(tempEle.attribute("uttags-selection-cis"));
				assertTrue("[]".compareToIgnoreCase(tempEle.attributeValue("uttags-selection-cis"))==0);
				break;
			}
		}
	}
	/************************画布-右键-挂载编辑取消动态节点************************************/


	/************************画布-右键-添加、编辑、删除影响关系规则************************************/
	@When("^给视图\"(.*)\"添加影响关系规则\"(.*)\"$")
	public void addDiagramRule(String diagramName, String friendDefName) throws DocumentException {
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		SaveOrUpdateDiagramRule saveDR = new SaveOrUpdateDiagramRule();
		JSONObject result = saveDR.saveOrUpdateDiagramRule(diagramName, friendDefName);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
		assertTrue(result.getJSONArray("data").length()>0);
	}

	@Then("^给视图\"(.*)\"添加影响关系规则\"(.*)\"成功$")
	public void checkAddDiagramRule(String diagramName, String friendDefName) throws DocumentException {
		if (diagramName.isEmpty()){
			fail("画布视图不存在");
		}
		DiagramUtil du = new DiagramUtil();
		BigDecimal diagramId = du.getDiagramIdByName(diagramName);
		String sql = "select ID from vc_diagram_ele where ele_type = 5 and diagram_id="+diagramId+" and DOMAIN_ID = "+ QaUtil.domain_id;
		ArrayList list = (ArrayList) JdbcUtil.executeQuery(sql);
		assertEquals(1, list.size());
	}

	@When("^视图\"(.*)\"画布右键编辑影响关系规则\"(.*)\"$")
	public void editDiagramRule(String diagramName, String friendDefName) throws DocumentException {
		addDiagramRule(diagramName, friendDefName);
	}

	@When("^视图\"(.*)\"画布右键编辑影响关系规则\"(.*)\"成功$")
	public void checkEditDiagramRule(String diagramName, String friendDefName) throws DocumentException {
		checkAddDiagramRule(diagramName, friendDefName);
	}

	@When("^视图\"(.*)\"画布右键删除影响关系规则$")
	public void removeDiagramRule(String diagramName) throws DocumentException {
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		RemoveDiagramRuleById removeDR = new RemoveDiagramRuleById();
		JSONObject result = removeDR.removeDiagramRuleById(diagramName);
		assertTrue(result.getBoolean("success"));
	}

	@When("^视图\"(.*)\"画布右键删除影响关系规则成功$")
	public void checkRemoveDiagramRule(String diagramName) throws DocumentException {
		if (diagramName.isEmpty()){
			fail("画布视图不存在");
		}
		DiagramUtil du = new DiagramUtil();
		BigDecimal diagramId = du.getDiagramIdByName(diagramName);
		String sql = "select ID from vc_diagram_ele where ele_type = 5 and diagram_id="+diagramId +" and DOMAIN_ID = "+ QaUtil.domain_id;
		ArrayList list = (ArrayList) JdbcUtil.executeQuery(sql);
		assertEquals(0, list.size());
	}
	/************************画布-右键-添加、编辑、删除影响关系规则结束************************************/

	/************************画布-右键-清除背景************************************/
	@When("^视图\"(.*)\"画布右键清除背景$")
	public void canvasClearBgImg(String diagramName) throws DocumentException {
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		UpdateDiagramContent ud = new UpdateDiagramContent();
		JSONObject result = ud.updateDiagramContentClearBgImg(diagramName);
		assertTrue(result.getBoolean("success"));
		
	}

	@When("^视图\"(.*)\"画布右键清除背景成功$")
	public void checkCanvasClearBgImg(String diagramName) throws DocumentException {
		if (diagramName.isEmpty()){
			fail("画布视图不存在");
		}
		JSONObject result = getDiagramInfoByName(diagramName);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
		JSONObject param = result.getJSONObject("data");
		JSONObject diagram = param.getJSONObject("diagram");
		//oracle数据库接口不返回这个值
		//assertTrue(diagram.has("diagramBgImg"));
		//assertTrue(diagram.getString("diagramBgImg").isEmpty());
	}
	/************************画布-右键-清除背景************************************/

	/**************************DMV-绘图-新建视图-ci:画图创建包含25个关系的视图****************************************/
	@When("^给视图\"(.*)\"增加\"(.*)\"分类的\"(.*)\"个CI$")
	public void addAllClassCI(String diagramName, String classNames, String pageSize) throws DocumentException{
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		if (classNames.isEmpty() ){
			fail("分类数组不存在");
		}
		UpdateDiagramContent ud = new UpdateDiagramContent();
		String[] cls = classNames.split(",");
		for (int i=0; i<cls.length; i++){
			JSONObject result = ud.updateDiagramContentAddAllClsCI(diagramName, cls[i], 50+i*100, Integer.valueOf(pageSize));
			assertTrue(result.getBoolean("success"));
		}
	}
	@Then("^成功给视图\"(.*)\"增加\"(.*)\"分类的\"(.*)\"个CI$")
	public void checkAddAllClassCI(String diagramName, String classNames, String pageSize){
		if (diagramName.isEmpty()){
			fail("画布视图不存在");
		}
		if (classNames.isEmpty() ){
			fail("分类数组不存在");
		}
		JSONObject result = getDiagramInfoByName(diagramName);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
		JSONObject oldParam = result.getJSONObject("data");
		JSONObject oldDiagram = oldParam.getJSONObject("diagram");
		BigDecimal diagramId = oldDiagram.getBigDecimal("id");
		String xml = oldParam.getString("xml");
		//String svg = oldParam.getString("svg");
		String  ci3dPoint= oldParam.getString("ci3dPoint");
		JSONArray 	diagramEles = (JSONArray) oldParam.get("diagramEles");
		assertNotNull(diagramEles);
		
		int count =0;
		String[] cls = classNames.split(",");
		for (int i=0; i<cls.length; i++){
			QueryPageByIndex queryPageByIndex = new QueryPageByIndex();
			JSONObject CIPageByIndex = queryPageByIndex.queryCIPageByIndex(cls[i], 1, Integer.valueOf(pageSize));
			JSONArray data = CIPageByIndex.getJSONObject("data").getJSONArray("data");
			count+=data.length();
		}
		
		assertEquals(diagramEles.length(), count);
		String sql = "select ID from vc_diagram_ele where  diagram_id="+diagramId +" and DOMAIN_ID = "+ QaUtil.domain_id;
		ArrayList list = (ArrayList) JdbcUtil.executeQuery(sql);
		assertEquals(diagramEles.length(), list.size());
	}
	/**************************DMV-绘图-新建视图-ci:画图创建包含25个关系的视图结束****************************************/
	
	/*===================Scenario: Image_根据base的菜单权限，控制显示创建CI和关系的按钮===========*/
	@When("^根据目录Code\"(.*)\"获得权限$")
	public void getAuthMenuTreeByRootModuCode(String rootCode){
		GetAuthMenuTreeByRootModuCode  getAuthMenuTreeByRootModuCode = new GetAuthMenuTreeByRootModuCode();
	    result = getAuthMenuTreeByRootModuCode.getAuthMenuTreeByRootModuCode(rootCode);
	    assertTrue(result.getBoolean("success"));
	}
	
	@Then("^成功获得\"(.*)\",\"(.*)\"的权限$")
	public void checkGetAuthMenuTreeByRootModuCode(String objManager,String rltManager){
		String sql = "SELECT ID FROM sys_menu WHERE MENU_NAME in ('" + objManager +"','" + rltManager + "') AND DATA_STATUS = 1 Order by ID";
		List list = JdbcUtil.executeQuery(sql);
		JSONArray data = result.getJSONArray("data");
		BigDecimal objId = new BigDecimal(0);
		BigDecimal rltId = new BigDecimal(0);
		for(int i = 0; i< data.length(); i ++){
			JSONObject obj = (JSONObject)data.get(i);
			if(obj.getString("text").equals("基本信息管理")){
				JSONArray children  = obj.getJSONArray("children");
				for(int j = 0; j<children.length(); j++){
					JSONObject childObj = (JSONObject)children.get(j);
					if (childObj.getString("text").equals(objManager)){
						objId = childObj.getBigDecimal("id");
					}
					if(childObj.getString("text").equals(rltManager)){
						rltId = childObj.getBigDecimal("id");
					}
				}
			}
			
			
		}
		
		HashMap objHashMap = (HashMap)list.get(0);
		assertEquals(objId,objHashMap.get("ID"));
		HashMap rltHashMap = (HashMap)list.get(1);
		assertEquals(rltId,rltHashMap.get("ID"));
	}
}
