package com.uinnova.test.step_definitions.testcase.dmv.image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.dmv.diagram.QueryDiagramInfoById;
import com.uinnova.test.step_definitions.api.dmv.diagram.QueryDiagramTree;
import com.uinnova.test.step_definitions.api.dmv.feedback.ExportFeedback;
import com.uinnova.test.step_definitions.api.dmv.feedback.SaveFeedback;
import com.uinnova.test.step_definitions.api.dmv.image.QueryCustomImagePage;
import com.uinnova.test.step_definitions.api.dmv.image.SaveCustomImage;
import com.uinnova.test.step_definitions.api.dmv.linked.QueryListByCdt;
import com.uinnova.test.step_definitions.api.dmv.linked.RemoveByCdt;
import com.uinnova.test.step_definitions.api.dmv.linked.SaveOrUpdateBatch;
import com.uinnova.test.step_definitions.utils.common.ExcelUtil;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;
import com.uinnova.test.step_definitions.utils.common.TxtUtil;
import com.uinnova.test.step_definitions.utils.dmv.DiagramUtil;
import com.uinnova.test.step_definitions.utils.dmv.ImageCanvasTopMenuUtil;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * 编写时间:2017-12-28 编写人:sunsl 功能介绍:绘图画布顶部菜单测试用例类
 */
public class Scenario_image_canvas_top_menu extends Scenario_image_Base{
	JSONObject result = new JSONObject();
	HashMap map = new HashMap();
	private String filePath;
	/* ===============Scenario: Canvas_Top_Menu_文件打开================== */
	@When("^文件打开视图$")
	public void queryDiagramTree() {
		QueryDiagramTree queryDiagramTree = new QueryDiagramTree();
		result = queryDiagramTree.queryDiagramTree();
		assertTrue(result.getBoolean("success"));
	}

	@Then("^文件成功打开视图\"(.*)\"$")
	public void checkQueryDiagramTree(String diagramName) {
		JSONObject data = result.getJSONObject("data");
		JSONArray diagrams = data.getJSONArray("diagrams");
		String diagramXml = "";
		String diagramSvg = "";
		String ci3dPoint = "";
		for (int i = 0; i < diagrams.length(); i++) {
			JSONObject obj = (JSONObject) diagrams.get(i);
			if (obj.getString("name").equals(diagramName)) {
				diagramXml = obj.getString("diagramXml");
				//diagramSvg = obj.getString("diagramSvg");
				ci3dPoint = obj.getString("ci3dPoint");
				break;
			}
		}
		QueryDiagramInfoById qd = new QueryDiagramInfoById();
		result = qd.queryDiagramInfoById(diagramName, true);
		if (result.getJSONObject("data").getJSONObject("diagram").getString("diagramXml").equals(diagramXml)
				&& result.getJSONObject("data").getJSONObject("diagram").getString("ci3dPoint").equals(ci3dPoint)) {
			assertTrue(true);
		} else {
			fail();
		}
	}

	/* ===================Scenario: Canvas_Top_Menu_插入图片===================== */
	@When("^为视图\"(.*)\"添加图片\"(.*)\"$")
	public void addImage(String diagramName, String imageName) throws DocumentException {
		ImageCanvasTopMenuUtil imageCanvasTopMenuUtil = new ImageCanvasTopMenuUtil();
		map = imageCanvasTopMenuUtil.addImage(diagramName, imageName, 0);
		assertTrue(true);
	}

	@Then("^为视图\"(.*)\"成功添加图片\"(.*)\"$")
	public void checkAddImage(String diagramName, String imageName) throws DocumentException {
		result = (JSONObject) map.get("result");
		String imagePath = (String) map.get("imagePath");
		JSONObject result =getDiagramInfoByName(diagramName);
		assertTrue(result.getBoolean("success"));
		JSONObject oldParam = result.getJSONObject("data");
		String xml = oldParam.getString("xml");
		Document doc = DocumentHelper.parseText(xml);
		Element graphElement = doc.getRootElement();
		Element rootElement = graphElement.element("root");
		List<Element> list = rootElement.elements();
		boolean flag = false;
		for (int i = 0; i < list.size(); i++) {
			Element temp = list.get(i);
			if (temp.attribute("style") != null) {
				String style = temp.attributeValue("style");
				String[] str = style.split(";");
				for (int j = 0; j < str.length; j++) {
					String tempstr = str[j];
					if (tempstr.contains("image")) {
						String[] strss = tempstr.split("=");
						assertEquals(imagePath, strss[1]);
						flag = true;
						break;
					}
				}
			}
			if (flag) {
				break;
			}
		}
	}

	/*
	 * ======================Scenario Outline:Canvas_Top_Menu_绘制面板===================
	 */
	@When("^给视图\"(.*)\"绘制面板宽度为\"(.*)\"高度为\"(.*)\"$")
	public void updateDiagramContent(String diagramName, String width, String height) {
		ImageCanvasTopMenuUtil imageCanvasTopMenuUtil = new ImageCanvasTopMenuUtil();
		result = imageCanvasTopMenuUtil.updateDiagramContent(diagramName, width, height);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^视图\"(.*)\"成功绘制面板宽度为\"(.*)\"高度为\"(.*)\"$")
	public void checkUpdateDiagramContent(String diagramName, String width, String height) {
		result =getDiagramInfoByName(diagramName);
		assertTrue(result.getBoolean("success"));
		JSONObject param = result.getJSONObject("data");
		String ci3dPoint = param.getString("ci3dPoint");
		JSONObject ci3dPointObj = new JSONObject(ci3dPoint);
		Integer graphWidth = ci3dPointObj.getInt("graphWidth");
		Integer graphHeight = ci3dPointObj.getInt("graphHeight");
		assertEquals(width, graphWidth.toString());
		assertEquals(height, graphHeight.toString());
	}

	@When("^视图\"(.*)\"清除面板宽度为\"(.*)\"高度为\"(.*)\"$")
	public void clearPanel(String diagramName, String graphWidth, String graphHeight) {
		ImageCanvasTopMenuUtil imageCanvasTopMenuUtil = new ImageCanvasTopMenuUtil();
		result = imageCanvasTopMenuUtil.updateDiagramContent(diagramName, graphWidth, graphHeight);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^视图\"(.*)\"成功清除面板宽度为\"(.*)\"高度为\"(.*)\"$")
	public void checkClearPanel(String diagramName, String graphWidth, String graphHeight) {
		checkUpdateDiagramContent(diagramName, graphWidth, graphHeight);
	}

	/* ======================Scenario: Canvas_Top_Menu_页面_背景颜色=============== */
	@When("^给视图\"(.*)\"选择背景颜色\"(.*)\"$")
	public void addBackground(String diagramName, String background) throws DocumentException {
		ImageCanvasTopMenuUtil imageCanvasTopMenuUtil = new ImageCanvasTopMenuUtil();
		result = imageCanvasTopMenuUtil.setXmlArribute(diagramName, background,0);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^视图\"(.*)\"成功选择背景颜色\"(.*)\"$")
	public void checkAddBackground(String diagramName, String background) throws DocumentException {
		result =getDiagramInfoByName(diagramName);
		assertTrue(result.getBoolean("success"));
		JSONObject param = result.getJSONObject("data");
        JSONObject diagram = param.getJSONObject("diagram");
		assertEquals(background, diagram.get("diagramBgCss"));
	}

	/*
	 * ==================Scenario:Canvas_Top_Menu_页面_插入背景===============*/
	@When("^给视图\"(.*)\"选择图片\"(.*)\"为背景$")
	public void insertImage(String diagramName, String imageName) throws DocumentException {
		ImageCanvasTopMenuUtil imageCanvasTopMenuUtil = new ImageCanvasTopMenuUtil();
		map = imageCanvasTopMenuUtil.addImage(diagramName, imageName, 1);
		result = (JSONObject) map.get("result");
		assertTrue(result.getBoolean("success"));
	}

	@Then("^视图\"(.*)\"成功选择图片\"(.*)\"为背景$")
	public void checkInsertImage(String diagramName, String imageName) throws DocumentException {
		result =getDiagramInfoByName(diagramName);
		assertTrue(result.getBoolean("success"));
		JSONObject param = result.getJSONObject("data");
		JSONObject diagram = param.getJSONObject("diagram");
		assertEquals((String) map.get("imagePath"), diagram.get("diagramBgImg"));
	}

	/*
	 * =============Scenario Outline: Canvas_Top_Menu_设置_不透明度==================*/
	@When("^给视图\"(.*)\"设置不透明度\"(.*)\"$")
	public void setOpacity(String diagramName, String opacity) throws DocumentException {
		ImageCanvasTopMenuUtil imageCanvasTopMenuUtil = new ImageCanvasTopMenuUtil();
		result = imageCanvasTopMenuUtil.setXmlArribute(diagramName, opacity, 1);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^给视图\"(.*)\"成功设置不透明度\"(.*)\"$")
	public void checkSetOpacity(String diagramName, String opacity) throws DocumentException {
		result =getDiagramInfoByName(diagramName);
		assertTrue(result.getBoolean("success"));
		JSONObject oldParam = result.getJSONObject("data");
		String xml = oldParam.getString("xml");
		Document doc = DocumentHelper.parseText(xml);
		Element graphElement = doc.getRootElement();
		Element rootElement = graphElement.element("root");
		List<Element> list = rootElement.elements();
		boolean flag = false;
		for (int i = 0; i < list.size(); i++) {
			Element temp = list.get(i);
			if (temp.attribute("style") != null) {
				String style = temp.attributeValue("style");
				String[] str = style.split(";");
				for (int j = 0; j < str.length; j++) {
					String tempstr = str[j];
					if (tempstr.contains("opacity")) {
						String[] strss = tempstr.split("=");
						assertEquals(opacity, strss[1]);
						flag = true;
						break;
					}
				}
			}
			if (flag) {
				break;
			}
		}
	}

	/* ===============Scenario Outline:Canvas_Top_Menu_设置_图标颜色=====*/
	@When("^给视图\"(.*)\"CICode为\"(.*)\"设置图标颜色\"(.*)\"$")
	public void setUtfilterValue(String diagramName,String ciCode, String utfilterValue) throws DocumentException {
		ImageCanvasTopMenuUtil imageCanvasTopMenuUtil = new ImageCanvasTopMenuUtil();
		result = imageCanvasTopMenuUtil.setXmlArribute(diagramName, ciCode,utfilterValue, 0);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^给视图\"(.*)\"CICode为\"(.*)\"成功设置图标颜色\"(.*)\"$")
	public void checkSetUtfilterValue(String diagramName,String ciCode, String utfilterValue) throws DocumentException {
		result =getDiagramInfoByName(diagramName);
		assertTrue(result.getBoolean("success"));
		JSONObject param = result.getJSONObject("data");
		String xml = param.getString("xml");
		Document doc = DocumentHelper.parseText(xml);
		Element graphElement = doc.getRootElement();
		Element rootElement = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject");
		// 遍历UserObject节点
		while (userObjectIters.hasNext()) {
			Element userObjectEle = (Element) userObjectIters.next();
			if (userObjectEle.attribute("code")!=null){
				if (ciCode.compareToIgnoreCase(userObjectEle.attributeValue("code"))==0){
			      Element mxEle = userObjectEle.element("mxCell");
			      assertEquals(mxEle.attributeValue("utfilter-value"), utfilterValue);
			      break;
				}
			}
		}
	}
	
	/* ===============Scenario Outline:Canvas_Top_Menu_设置_图标大小_小=====*/
	@When("^给视图\"(.*)\"CICode为\"(.*)\"设置图标大小小\"(.*)\"$")
	public void setUtsizems(String diagramName,String ciCode,String utsizems) throws DocumentException {
		ImageCanvasTopMenuUtil imageCanvasTopMenuUtil = new ImageCanvasTopMenuUtil();
		result = imageCanvasTopMenuUtil.setXmlArribute(diagramName, ciCode,utsizems, 1);
		assertTrue(result.getBoolean("success"));
	}
	
	@Then("^给视图\"(.*)\"CICode为\"(.*)\"成功设置图标大小小\"(.*)\"$")
	public void checkSetUtsizems(String diagramName,String ciCode,String utsizems) throws DocumentException{
		result =getDiagramInfoByName(diagramName);
		assertTrue(result.getBoolean("success"));
		JSONObject param = result.getJSONObject("data");
		String xml = param.getString("xml");
		Document doc = DocumentHelper.parseText(xml);
		Element graphElement = doc.getRootElement();
		Element rootElement = graphElement.element("root");
		Iterator  userObjectIters = rootElement.elementIterator("UserObject");
		while(userObjectIters.hasNext()){
			Element userObjectEle = (Element)userObjectIters.next();
			if(userObjectEle.attribute("code") != null){
				if(ciCode.compareToIgnoreCase(userObjectEle.attributeValue("code"))==0){
					Element mxEle = userObjectEle.element("mxCell");
					assertEquals(mxEle.attributeValue("utsize-ms"),utsizems);
					break;
				}
			}
		}
	}
	
	/* ===============Scenario Outline:Canvas_Top_Menu_设置_图标大小_中=====*/
	@When("^给视图\"(.*)\"CICode为\"(.*)\"设置图标大小中\"(.*)\"$")
	public void setUtsizemx(String diagramName,String ciCode,String utsizemx) throws DocumentException {
		ImageCanvasTopMenuUtil imageCanvasTopMenuUtil = new ImageCanvasTopMenuUtil();
		result = imageCanvasTopMenuUtil.setXmlArribute(diagramName, ciCode,utsizemx, 2);
		assertTrue(result.getBoolean("success"));
	}
	
	@Then("^给视图\"(.*)\"CICode为\"(.*)\"成功设置图标大小中\"(.*)\"$")
	public void checkSetUtsizemx(String diagramName,String ciCode,String utsizemx) throws DocumentException{
		result =getDiagramInfoByName(diagramName);
		assertTrue(result.getBoolean("success"));
		JSONObject param = result.getJSONObject("data");
		String xml = param.getString("xml");
		Document doc = DocumentHelper.parseText(xml);
		Element graphElement = doc.getRootElement();
		Element rootElement = graphElement.element("root");
		Iterator  userObjectIters = rootElement.elementIterator("UserObject");
		while(userObjectIters.hasNext()){
			Element userObjectEle = (Element)userObjectIters.next();
			if(userObjectEle.attribute("code") != null){
				if(ciCode.compareToIgnoreCase(userObjectEle.attributeValue("code"))==0){
					Element mxEle = userObjectEle.element("mxCell");
					assertEquals(mxEle.attributeValue("utsize-mx"),utsizemx);
					break;
				}
			}
		}
	}
	
	/* ===============Scenario Outline:Canvas_Top_Menu_设置_图标大小_大=====*/
	@When("^给视图\"(.*)\"CICode为\"(.*)\"设置图标大小大\"(.*)\"$")
	public void setUtsizeml(String diagramName,String ciCode,String utsizeml) throws DocumentException {
		ImageCanvasTopMenuUtil imageCanvasTopMenuUtil = new ImageCanvasTopMenuUtil();
		result = imageCanvasTopMenuUtil.setXmlArribute(diagramName, ciCode,utsizeml, 3);
		assertTrue(result.getBoolean("success"));
	}
	
	@Then("^给视图\"(.*)\"CICode为\"(.*)\"成功设置图标大小大\"(.*)\"$")
	public void checkSetUtsizeml(String diagramName,String ciCode,String utsizeml) throws DocumentException{
		result =getDiagramInfoByName(diagramName);
		assertTrue(result.getBoolean("success"));
		JSONObject param = result.getJSONObject("data");
		String xml = param.getString("xml");
		Document doc = DocumentHelper.parseText(xml);
		Element graphElement = doc.getRootElement();
		Element rootElement = graphElement.element("root");
		Iterator  userObjectIters = rootElement.elementIterator("UserObject");
		while(userObjectIters.hasNext()){
			Element userObjectEle = (Element)userObjectIters.next();
			if(userObjectEle.attribute("code") != null){
				if(ciCode.compareToIgnoreCase(userObjectEle.attributeValue("code"))==0){
					Element mxEle = userObjectEle.element("mxCell");
					assertEquals(mxEle.attributeValue("utsize-ml"),utsizeml);
					break;
				}
			}
		}
	}
	
	/* ===============Scenario Outline:Canvas_Top_Menu_设置_字体样式=====*/
	@When("^给视图\"(.*)\"CICode为\"(.*)\"设置字体样式\"(.*)\"$")
	public void setHorizontal(String diagramName,String ciCode,String horizontal) throws DocumentException{
		ImageCanvasTopMenuUtil imageCanvasTopMenuUtil = new ImageCanvasTopMenuUtil();
		result = imageCanvasTopMenuUtil.setXmlArribute(diagramName,ciCode,horizontal, 4);
		assertTrue(result.getBoolean("success"));
	}
	
	@Then("^给视图\"(.*)\"CICode为\"(.*)\"成功设置字体样式\"(.*)\"$")
	public void checkSetHorizontal(String diagramName,String ciCode,String horizontal) throws DocumentException{
		result =getDiagramInfoByName(diagramName);
		assertTrue(result.getBoolean("success"));
		JSONObject param = result.getJSONObject("data");
		String xml = param.getString("xml");
		Document doc = DocumentHelper.parseText(xml);
		Element graphElement = doc.getRootElement();
		Element rootElement = graphElement.element("root");
		Iterator  userObjectIters = rootElement.elementIterator("UserObject");
		while(userObjectIters.hasNext()){
			Element userObjectEle = (Element)userObjectIters.next();
			if(userObjectEle.attribute("code") != null){
				if(ciCode.compareToIgnoreCase(userObjectEle.attributeValue("code"))==0){
					Element mxEle = userObjectEle.element("mxCell");
					assertEquals(mxEle.attributeValue("horizontal"),horizontal);
					break;
				}
			}
		}
	}
	/*============ Scenario: Canvas_Top_Menu_设置_自定义形状及资源包======*/
	@When("^给图标设置\"(.*)\",\"(.*)\"$")
	public void saveCustomImage(String imageName,String imageXml){
		SaveCustomImage saveCustomImage = new SaveCustomImage();
		result = saveCustomImage.saveCustomImage(imageName, imageXml);
		assertTrue(result.getBoolean("success"));
	}
	@Then("^给图标成功设置\"(.*)\",\"(.*)\"$")
	public void checkSaveCustomImage(String imageName,String imageXml){
		QueryCustomImagePage queryCustomImagePage = new QueryCustomImagePage();
		result = queryCustomImagePage.queryCustomImagePage(1, 10000);
		JSONObject data = result.getJSONObject("data");
		Integer totalRows = data.getInt("totalRows");
		assertEquals(totalRows.intValue(),1);
		JSONArray dataArray = data.getJSONArray("data");
		JSONObject obj = (JSONObject)dataArray.get(0);
		JSONObject image = obj.getJSONObject("image");
		assertEquals(imageName,image.getString("imgName"));
		assertEquals(imageXml,obj.getString("imageXml"));
	}
	
	/*===============Scenario: Canvas_Top_Menu_设置_外部链接==========*/
	@When("^给视图\"(.*)\"CICode为\"(.*)\"设置外部链接名称为\"(.*)\"链接地址为\"(.*)\"$")
	public void setUtdatalinkurl(String diagramName,String ciCode,String linkName,String linkAddress) throws DocumentException{
		String utfatalinkUrl = "[{&quot;name&quot;:&quot;"+ linkName +"&quot;,&quot;url&quot;:&quot;"+linkAddress+"&quot;}]";
		ImageCanvasTopMenuUtil imageCanvasTopMenuUtil = new ImageCanvasTopMenuUtil();
		result = imageCanvasTopMenuUtil.setXmlArribute(diagramName, ciCode,utfatalinkUrl, 5);
		assertTrue(result.getBoolean("success"));
	}
	@Then("^视图\"(.*)\"CICode为\"(.*)\"成功设置外部链接名称为\"(.*)\"链接地址为\"(.*)\"$")
	public void checkSetUtdatalinkurl(String diagramName,String ciCode,String linkName,String linkAddress) throws DocumentException{
		String utfatalinkUrl = "[{&quot;name&quot;:&quot;"+ linkName +"&quot;,&quot;url&quot;:&quot;"+linkAddress+"&quot;}]";
		result =getDiagramInfoByName(diagramName);
		JSONObject param = result.getJSONObject("data");
		String xml = param.getString("xml");
		Document doc = DocumentHelper.parseText(xml);
		Element graphElement = doc.getRootElement();
		Element rootElement = graphElement.element("root");
		Iterator  userObjectIters = rootElement.elementIterator("UserObject");
		while(userObjectIters.hasNext()){
			Element userObjectEle = (Element)userObjectIters.next();
			if(userObjectEle.attribute("code") != null){
				if(ciCode.compareToIgnoreCase(userObjectEle.attributeValue("code"))==0){
					Element mxEle = userObjectEle.element("mxCell");
					assertEquals(mxEle.attributeValue("utdata-link-url"),utfatalinkUrl);
					break;
				}
			}
		}
	}
	
	
	/*========================Scenario: Canvas_Top_Menu_设置_生成链路,删除链路===================*/
	@When("^给视图\"(.*)\"添加链路名称为\"(.*)\"链路颜色为\"(.*)\"的ciCodes为\"(.*)\"$")
	public void saveOrUpdateBatch(String diagramName,String linkedName,String linkedColor,String ciCodes){
		SaveOrUpdateBatch  saveOrUpdateBatch = new SaveOrUpdateBatch();
		result = saveOrUpdateBatch.saveOrUpdateBatch(diagramName, linkedName, linkedColor,ciCodes);
		assertTrue(result.getBoolean("success"));
	}
	
	@Then("^给视图\"(.*)\"成功添加链路名称为\"(.*)\"链路颜色为\"(.*)\"的ciCodes为\"(.*)\"$")
	public void checkSaveOrUpdateBatch(String diagramName,String linkedName,String linkedColor,String ciCodes){
		QueryListByCdt queryListByCdt = new QueryListByCdt();
		result = queryListByCdt.queryListByCdt(diagramName);
		JSONArray data = result.getJSONArray("data");
		if (data.length() > 0){
		    JSONObject obj = (JSONObject)data.get(0);
		    assertEquals(linkedName,obj.getString("linkedName"));
		    assertEquals(ciCodes,obj.getString("ciCodes"));
		    assertEquals(linkedColor,obj.getString("linkedColor"));
		}
		
	}
	
	@When("^删除视图\"(.*)\"的链路$")
	public void removeByCdt(String diagramName){
		RemoveByCdt removeByCdt = new RemoveByCdt();
		result = removeByCdt.removeByCdt(diagramName);
		assertTrue(result.getBoolean("success"));
	}
	
	@Then("^成功删除视图\"(.*)\"的链路$")
	public void checkRemoveByCdt(String diagramName){
		DiagramUtil diagramUtil = new DiagramUtil();
		BigDecimal sourceId = diagramUtil.getDiagramIdByName(diagramName);
		String sql = "SELECT * FROM vc_ci_linked WHERE SOURCE_ID =" + sourceId +" AND DATA_STATUS = 1 AND DOMAIN_ID =" + QaUtil.domain_id;
		List list = JdbcUtil.executeQuery(sql);
		assertEquals(list.size(),0);
	}
	
	  /*==================Scenario Outline: Canvas_Top_Menu_吐槽==========*/
	   @When("^用户给系统提供问题为\"(.*)\"的反馈$")
	   public void saveFeedback(String content){
		   if(content.indexOf(".") > 0){
			   String filePath = Scenario_image_canvas.class.getResource("/").getPath() + "testData/dmv/feedback/" + content;
			   content = (new TxtUtil()).readTxt(filePath);
		   }
		   SaveFeedback saveFeedback = new SaveFeedback();
		   result = saveFeedback.saveFeedback(content);
		   assertTrue(result.getBoolean("success"));
	   }
	   
	   @Then("^用户成功给系统提供问题为\"(.*)\"的反馈$")
	   public void checkSaveFeedback(String content){
		   if(content.indexOf(".") > 0){
			   String filePath = Scenario_image_canvas.class.getResource("/").getPath() + "testData/dmv/feedback/" + content;
			   content = (new TxtUtil()).readTxt(filePath);
		   }
		   String sql = "SELECT ID FROM vc_feedback WHERE CONTENT = '" + content + "' AND DATA_STATUS = 1";
		   List list = JdbcUtil.executeQuery(sql);
		   assertEquals(list.size(),1);
	   }
	   
	   @And("^删除用户给系统提供问题为\"(.*)\"的反馈$")
	   public void removeFeedback(String content){
		   if(content.indexOf(".") > 0){
			   String filePath = Scenario_image_canvas.class.getResource("/").getPath() + "testData/dmv/feedback/" + content;
			   content = (new TxtUtil()).readTxt(filePath);
		   }
		   String sql = "UPDATE vc_feedback SET DATA_STATUS = 0 WHERE CONTENT = '" + content + "' AND DATA_STATUS = 1";
		   JdbcUtil.executeUpdate(sql);
	   }
	   /*=============Canvas_Top_Menu_吐槽_导出================*/
	   @And("^用户给系统提供以下问题的反馈:$")
	   public void saveFeedbackDupli(DataTable dt){
		  List<List<String>>  list = dt.raw();
		  SaveFeedback saveFeedback = new SaveFeedback();
		  for(int i = 1; i < list.size(); i ++){
			   result = saveFeedback.saveFeedback(list.get(i).get(0));
			   assertTrue(result.getBoolean("success"));
		  }
	  }
	   
	   @When("^用户导出反馈$")
	   public void exportFeedback(){
		   ExportFeedback exportFeedback = new ExportFeedback();
		   filePath = exportFeedback.exportFeedback();
		   File file = new File(filePath);
		   if (file.exists()){
			   assertTrue(true);
		   }
	   }
	   
	   @Then("^用户成功导出以下反馈:$")
	   public void checkExportFeedback(DataTable dt){
		   List<List<String>> list = dt.raw();
		   JSONArray array =  (new ExcelUtil()).readFromExcel(filePath, "DMV用户反馈");
		   for(int i = 1; i < array.length(); i ++){
			   JSONObject obj = (JSONObject)array.get(i);
			   assertEquals(list.get(i).get(0),obj.get("2"));
		   }
		   
//		   String sql = "SELECT TARGET_CODE,TARGET_DESC,PV_COUNT FROM cc_pv_count  ORDER BY ID";
//		   List listCC = JdbcUtil.executeQuery(sql);
//		   JSONArray array1 = (new ExcelUtil()).readFromExcel(filePath, "管图-操作统计");
//		   assertEquals(listCC.size(),array1.length()-1);
//		   for(int i = 0;i < listCC.size(); i ++){
//			   HashMap map = (HashMap)listCC.get(i);
//			   JSONObject obj1 = (JSONObject)array1.get(i+1);
//			   assertEquals(map.get("TARGET_CODE"),obj1.get("0"));
//			   if(map.get("TARGET_DESC") != null){
//			      assertEquals(map.get("TARGET_DESC"),obj1.get("1"));
//			   }
//			   if(map.get("PV_COUNT").toString() !=null){
//			      assertEquals(map.get("PV_COUNT").toString(),obj1.get("2"));
//			   }
//		   }
	   }
	   
	   @And("^删除以下用户反馈:$")
	   public void removeFeedbackDupli(DataTable dt){
		   List<List<String>>  list = dt.raw();
		   for(int i = 1; i < list.size(); i ++){
			   String sql = "UPDATE vc_feedback SET DATA_STATUS = 0 WHERE CONTENT = '" + list.get(i).get(0) + "' AND DATA_STATUS = 1";
			   JdbcUtil.executeUpdate(sql);
		   }
	   }
}
