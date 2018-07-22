package com.uinnova.test.step_definitions.testcase.dmv.image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.dmv.diagram.UpdateDiagramContent;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;

import cucumber.api.DataTable;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author wsl
 * 视图中容器操作
 *
 */
public class Scenario_image_Container extends Scenario_image_Base{

	/********************************给视图增加容器***************************************/

	@When("^给视图\"(.*)\"增加容器坐标为\"(.*)\"\"(.*)\"$")
	public void updateDiagramContentAddContainer(String diagramName, String x, String y) throws DocumentException, InterruptedException{
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		UpdateDiagramContent ud = new UpdateDiagramContent();
		JSONObject result = ud.updateDiagramContentAddContainer(diagramName,  x, y);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^成功给视图\"(.*)\"增加容器坐标为\"(.*)\"\"(.*)\"$")
	public void checkUpdateDiagramContentAddCI(String diagramName, String x, String y) throws DocumentException{
		if (diagramName.isEmpty()){
			fail("画布视图不存在");
		}
		JSONObject result = getDiagramInfoByName(diagramName);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
		JSONObject oldParam = result.getJSONObject("data");

		String xml = oldParam.getString("xml");
		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement();            
		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); 
		// 遍历UserObject节点
		boolean hasContainer = false;
		while (userObjectIters.hasNext()) {
			Element userObjectEle= (Element) userObjectIters.next();
			if (userObjectEle.attribute("label")!=null){
				if ("Container".compareToIgnoreCase(userObjectEle.attributeValue("label"))==0){
					Element tempMxCell = userObjectEle.element("mxCell");
					Element mxGeometry = tempMxCell.element("mxGeometry");
					if (x.compareToIgnoreCase(mxGeometry.attributeValue("x"))==0 && y.compareToIgnoreCase(mxGeometry.attributeValue("y"))==0){
						hasContainer = true;
						break;
					}
				}
			}
		}
		assertTrue(hasContainer);
	}

	/********************************给视图中容器编辑规则筛选条件***************************************/	
	@When("^给视图\"(.*)\"中坐标为\"(.*)\"\"(.*)\"的容器编辑规则筛选条件类名\"(.*)\"条件如下:$")
	public void containerAddDefAndRule(String diagramName, String x, String y, String className, DataTable table) throws DocumentException, InterruptedException{
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		UpdateDiagramContent ud = new UpdateDiagramContent();
		JSONObject result = ud.updateDiagramContentContainerAddDefAndRule(diagramName,  x, y, className, table);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^成功给视图\"(.*)\"中坐标为\"(.*)\"\"(.*)\"的容器编辑规则筛选条件类名\"(.*)\"条件如下:$")
	public void checkContainerAddDefAndRule(String diagramName, String x, String y, String className, DataTable table) throws DocumentException, InterruptedException{
		if (diagramName.isEmpty()){
			fail("画布视图不存在");
		}
		
		JSONObject result = getDiagramInfoByName(diagramName);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
		JSONObject oldParam = result.getJSONObject("data");

		String xml = oldParam.getString("xml");
		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement();            
		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); 
		// 遍历UserObject节点
		boolean hasContainer = false;
		while (userObjectIters.hasNext()) {
			Element userObjectEle= (Element) userObjectIters.next();
			if (userObjectEle.attribute("label")!=null){
				if ("Container".compareToIgnoreCase(userObjectEle.attributeValue("label"))==0){
					Element tempMxCell = userObjectEle.element("mxCell");
					Element mxGeometry = tempMxCell.element("mxGeometry");
					if (x.compareToIgnoreCase(mxGeometry.attributeValue("x"))==0 && y.compareToIgnoreCase(mxGeometry.attributeValue("y"))==0){
						JSONObject utruleCondition = new JSONObject(tempMxCell.attributeValue("utruleCondition"));
						CiClassUtil ciclsU = new CiClassUtil();
						assertEquals(utruleCondition.getBigDecimal("classId"), ciclsU.getCiClassId(className));
						JSONArray attrDefsArr = utruleCondition.getJSONArray("attrDefs");
						int rows = table.raw().size();
						assertEquals(rows-1, attrDefsArr.length());
						for(int i=1;i<rows;i++){
							boolean hasAttr = false;
							List<String> row = table.raw().get(i);
							for (int arr=0; arr<attrDefsArr.length(); arr++){
								JSONObject attrTempObj = attrDefsArr.getJSONObject(arr);
								String keyName = attrTempObj.getString("keyName");
								if (row.get(1).compareToIgnoreCase(keyName)==0 && row.get(2).compareToIgnoreCase(attrTempObj.getString("symbol"))==0 && row.get(3).compareToIgnoreCase(attrTempObj.getString("keyValue"))==0){
									hasAttr = true;
									break;
								}
							}
							assertTrue(hasAttr);
						}
						hasContainer = true;
						break;
					}
				}
			}
		}
		assertTrue(hasContainer);
	}
}
