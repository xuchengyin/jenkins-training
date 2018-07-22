package com.uinnova.test.step_definitions.testcase.dmv.image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.dmv.diagram.UpdateDiagramContent;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

import cucumber.api.java.en.When;

/**
 * @author wsl
 * 图标画图
 *
 */
public class Scenario_image_Img extends Scenario_image_Base{
	BigDecimal ciId =new BigDecimal(0);//解除ci是否成功的时候使用

	/**************************DMV-绘图-新建视图-img:给视图增加一个图标****************************************/
	@When("^给视图\"(.*)\"增加常用图标\"(.*)\"坐标为\"(.*)\"\"(.*)\"$")
	public void updateDiagramContentAddImg(String diagramName, String imgName, String x, String y) throws DocumentException{
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		UpdateDiagramContent ud = new UpdateDiagramContent();
		JSONObject result = ud.updateDiagramContentAddImg(diagramName, imgName, x, y);
		assertTrue(result.getBoolean("success"));
	}

	@When("^成功给视图\"(.*)\"增加常用图标\"(.*)\"坐标为\"(.*)\"\"(.*)\"$")
	public void checkUpdateDiagramContentAddImg(String diagramName, String imgName, String x, String y) throws DocumentException{
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
		boolean hasImg = false;
		while (userObjectIters.hasNext()) {
			Element userObjectEle= (Element) userObjectIters.next();
			if (userObjectEle.attribute("imgCode")!=null){
				if (imgName.compareToIgnoreCase(userObjectEle.attributeValue("imgCode"))==0){
					Element tempMxCell = userObjectEle.element("mxCell");
					Element mxGeometry = tempMxCell.element("mxGeometry");
					if (x.compareToIgnoreCase(mxGeometry.attributeValue("x"))==0 && y.compareToIgnoreCase(mxGeometry.attributeValue("y"))==0){
						hasImg = true;
						break;
					}
				}
			}
		}
		assertTrue(hasImg);
	}
	
	/**************************DMV-绘图-新建视图-img:给视图删除一个图标****************************************/
	@When("^给视图\"(.*)\"删除常用图标\"(.*)\"坐标为\"(.*)\"\"(.*)\"$")
	public void updateDiagramContentRemoveImg(String diagramName, String imgName, String x, String y) throws DocumentException{
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		UpdateDiagramContent ud = new UpdateDiagramContent();
		JSONObject result = ud.updateDiagramContentRemoveImg(diagramName, imgName, x, y);
		assertTrue(result.getBoolean("success"));
	}

	@When("^成功给视图\"(.*)\"删除常用图标\"(.*)\"坐标为\"(.*)\"\"(.*)\"$")
	public void checkUpdateDiagramContentRemoveImg(String diagramName, String imgName, String x, String y) throws DocumentException{
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
		boolean hasImg = false;
		while (userObjectIters.hasNext()) {
			Element userObjectEle= (Element) userObjectIters.next();
			if (userObjectEle.attribute("imgCode")!=null){
				if (imgName.compareToIgnoreCase(userObjectEle.attributeValue("imgCode"))==0){
					Element tempMxCell = userObjectEle.element("mxCell");
					Element mxGeometry = tempMxCell.element("mxGeometry");
					if (x.compareToIgnoreCase(mxGeometry.attributeValue("x"))==0 && y.compareToIgnoreCase(mxGeometry.attributeValue("y"))==0){
						hasImg = true;
						break;
					}
				}
			}
		}
		assertFalse(hasImg);
	}
	
	/**************************DMV-绘图-新建视图-img:给图标创建CI****************************************/
	@When("^给视图\"(.*)\"中常用图标\"(.*)\"坐标为\"(.*)\"\"(.*)\"创建CI$")
	public void updateDiagramContentImgCreateCI(String diagramName, String imgName, String x, String y) throws DocumentException{
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		UpdateDiagramContent ud = new UpdateDiagramContent();
		JSONObject result = ud.updateDiagramContentImgCreateCI(diagramName, imgName, x, y);
		assertTrue(result.getBoolean("success"));
	}

	
	@When("^成功给视图\"(.*)\"中常用图标\"(.*)\"坐标为\"(.*)\"\"(.*)\"创建CI$")
	public void checkUpdateDiagramContentImgCreateCI(String diagramName, String imgName, String x, String y) throws DocumentException{
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
		boolean hasImg = false;
		
		while (userObjectIters.hasNext()) {
			Element userObjectEle= (Element) userObjectIters.next();
			if (userObjectEle.attribute("imgCode")!=null){
				if (imgName.compareToIgnoreCase(userObjectEle.attributeValue("imgCode"))==0){
					Element tempMxCell = userObjectEle.element("mxCell");
					Element mxGeometry = tempMxCell.element("mxGeometry");
					if (x.compareToIgnoreCase(mxGeometry.attributeValue("x"))==0 && y.compareToIgnoreCase(mxGeometry.attributeValue("y"))==0){
						hasImg = true;
						String dataid = userObjectEle.attributeValue("data-id");
						ciId = new BigDecimal(dataid.substring(3));
						assertNotNull(userObjectEle.attributeValue("label"));
						assertNotNull(userObjectEle.attributeValue("data-id"));
						assertNotNull(tempMxCell.attributeValue("utcode"));
						assertNotNull(tempMxCell.attributeValue("utdata-id"));
						assertNotNull(tempMxCell.attributeValue("utdata-values"));
						assertNotNull(tempMxCell.attributeValue("utdata-label-list"));
						assertNotNull(tempMxCell.attributeValue("utdata-label-val"));
						assertNotNull(tempMxCell.attributeValue("utdata-label-val-obj"));
						break;
					}
				}
			}
		}
		assertTrue(hasImg);
		
		String sql ="SELECT ID from vc_diagram_ele where ele_ID ="+ciId +" and diagram_ID="+oldParam.getJSONObject("diagram").getBigDecimal("id")+ " and DOMAIN_ID="+QaUtil.domain_id;
		List rltList = JdbcUtil.executeQuery(sql);
		assertEquals(rltList.size(),  1);
	}
	
	/**************************DMV-绘图-新建视图-img:给图标创建CI****************************************/
	@When("^给视图\"(.*)\"中常用图标\"(.*)\"坐标为\"(.*)\"\"(.*)\"解除CI$")
	public void updateDiagramContentImgRemoveCI(String diagramName, String imgName, String x, String y) throws DocumentException{
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		UpdateDiagramContent ud = new UpdateDiagramContent();
		JSONObject result = ud.updateDiagramContentImgRemoveCI(diagramName, imgName, x, y);
		assertTrue(result.getBoolean("success"));
		
	}

	@When("^成功给视图\"(.*)\"中常用图标\"(.*)\"坐标为\"(.*)\"\"(.*)\"解除CI$")
	public void checkUpdateDiagramContentImgRemoveCI(String diagramName, String imgName, String x, String y) throws DocumentException{
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
		boolean hasImg = false;
		while (userObjectIters.hasNext()) {
			Element userObjectEle= (Element) userObjectIters.next();
			if (userObjectEle.attribute("imgCode")!=null){
				if (imgName.compareToIgnoreCase(userObjectEle.attributeValue("imgCode"))==0){
					Element tempMxCell = userObjectEle.element("mxCell");
					Element mxGeometry = tempMxCell.element("mxGeometry");
					if (x.compareToIgnoreCase(mxGeometry.attributeValue("x"))==0 && y.compareToIgnoreCase(mxGeometry.attributeValue("y"))==0){
						hasImg = true;
						assertNotNull(userObjectEle.attributeValue("label"));
						assertTrue(userObjectEle.attributeValue("data-id").isEmpty());
						assertNull(tempMxCell.attribute("utcode"));
						assertNull(tempMxCell.attribute("utdata-id"));
						assertNotNull(tempMxCell.attributeValue("utdata-values"));
						assertNotNull(tempMxCell.attributeValue("utdata-label-list"));
						assertNotNull(tempMxCell.attributeValue("utdata-label-val"));
						assertNotNull(tempMxCell.attributeValue("utdata-label-val-obj"));
						break;
					}
				}
			}
		}
		assertTrue(hasImg);
		
		String sql ="SELECT ID from vc_diagram_ele where  ele_ID ="+ciId +" and diagram_ID="+oldParam.getJSONObject("diagram").getBigDecimal("id")+ " and DOMAIN_ID="+QaUtil.domain_id;
		List rltList = JdbcUtil.executeQuery(sql);
		assertEquals(rltList.size(),  0);
	}

}
