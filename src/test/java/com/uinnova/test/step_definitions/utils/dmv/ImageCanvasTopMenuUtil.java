package com.uinnova.test.step_definitions.utils.dmv;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.HashMap;

import org.dom4j.DocumentException;
import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.contant.Contants;
import com.uinnova.test.step_definitions.api.dmv.diagram.QueryDiagramInfoById;
import com.uinnova.test.step_definitions.api.dmv.diagram.UpdateDiagramContent;
import com.uinnova.test.step_definitions.api.dmv.image.AddImage;
import com.uinnova.test.step_definitions.api.dmv.image.QueryImagePage;
import com.uinnova.test.step_definitions.testcase.dmv.group.Scenario_group;

/**
 * 编写时间:2018-01-02 编写人:sunsl 功能介绍:画布顶部菜单工具类
 */
public class ImageCanvasTopMenuUtil {
	/**
	 * @param diagramName
	 * @param imageName
	 * @return map
	 */
	public HashMap addImage(String diagramName, String imageName,int flag) throws DocumentException {
		HashMap map = new HashMap();
		JSONObject result;
		String filePath = Scenario_group.class.getResource("/").getPath() + "testData/dmv/group/" + imageName;
		AddImage addImage = new AddImage();
		result = addImage.addImage(filePath);
		QueryImagePage qi = new QueryImagePage();
		result = qi.queryImagePage();
		JSONObject obj = result.getJSONObject("data");
		JSONArray data = obj.getJSONArray("data");
		JSONObject imageObj = (JSONObject) data.get(0);
		String imagePath = imageObj.getString("imgPath");
		result =getDiagramInfo(diagramName);
		assertTrue(result.getBoolean("success"));
		JSONObject oldParam = result.getJSONObject("data");
		JSONObject oldDiagram = oldParam.getJSONObject("diagram");
		String ci3dPoint = oldParam.getString("ci3dPoint");
		String xml = oldParam.getString("xml");
		XmlUtil  xmlUtil = new XmlUtil();
		if(flag == 0){
		   xml = xmlUtil.addImage(xml,imagePath);
		}else{
		   xml = xmlUtil.insertImage(xml,imagePath);
		}
		JSONArray diagramEles =new JSONArray();
		DiagramUtil diagramUtil = new DiagramUtil();
		BigDecimal id = diagramUtil.getDiagramIdByName(diagramName);
		
		JSONObject param = new JSONObject();
		param.put("ci3dPoint", ci3dPoint);
		if(flag == 1){
			oldDiagram.put("diagramBgImg", imagePath);
		}
		param.put("diagram", oldDiagram);
		param.put("diagramEles", diagramEles);
		param.put("id", id);
		param.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
        param.put("xml", xml);
        UpdateDiagramContent updateDiagramContent = new UpdateDiagramContent();
        result = updateDiagramContent.updateDiagramContent(param);
        map.put("result", result);
        map.put("imagePath", imagePath);
		return map;
	}
	/**
	 *@param diagramName
	 *@param width
	 *@param height
	 */
	public JSONObject updateDiagramContent(String diagramName,String width,String height){
		JSONObject result;
		result =getDiagramInfo(diagramName);
		assertTrue(result.getBoolean("success"));
		JSONObject oldParam = result.getJSONObject("data");
		JSONObject oldDiagram = oldParam.getJSONObject("diagram");
		String ci3dPoint = oldParam.getString("ci3dPoint");
		String xml = oldParam.getString("xml");
		JSONArray diagramEles =new JSONArray();
		DiagramUtil diagramUtil = new DiagramUtil();
		BigDecimal id = diagramUtil.getDiagramIdByName(diagramName);
		JSONObject ci3dPointObj = new JSONObject(ci3dPoint);
		ci3dPointObj.put("graphWidth", width);
		ci3dPointObj.put("graphHeight", height);
		JSONObject param = new JSONObject();
		param.put("ci3dPoint", ci3dPointObj.toString());
		param.put("diagram", oldDiagram);
		param.put("diagramEles", diagramEles);
		param.put("id", id);
		param.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
        param.put("xml", xml);
        UpdateDiagramContent updateDiagramContent = new UpdateDiagramContent();
        result = updateDiagramContent.updateDiagramContent(param);
        return result;
	}
	/**
	 * @param  diagramName
	 * @param background
	 * @return result
	 */
	public JSONObject setXmlArribute(String diagramName,String ciCode,String arribute,int flag) throws DocumentException{
		JSONObject result;
		result =getDiagramInfo(diagramName);
		assertTrue(result.getBoolean("success"));
		JSONObject oldParam = result.getJSONObject("data");
		JSONObject oldDiagram = oldParam.getJSONObject("diagram");
		String ci3dPoint = oldParam.getString("ci3dPoint");
		String xml = oldParam.getString("xml");
		XmlUtil  xmlUtil = new XmlUtil();
		switch(flag){
		  case 0:
			  xml = xmlUtil.setUtfilterValue(xml,ciCode,arribute);
		      break;
		  case 1:
			  xml = xmlUtil.setUtsizems(xml,ciCode,arribute);
		      break;
		  case 2:
			  xml = xmlUtil.setUtsizemx(xml,ciCode,arribute);
		      break;
		  case 3:
			  xml = xmlUtil.setUtsizeml(xml,ciCode,arribute);
		      break;
		  case 4:
			  xml = xmlUtil.setHorizontal(xml,ciCode,arribute);
		      break;
		  case 5:
			  xml = xmlUtil.setUtdatalinkurl(xml,ciCode,arribute);
		      break;
		}
		JSONArray diagramEles =new JSONArray();
		DiagramUtil diagramUtil = new DiagramUtil();
		BigDecimal id = diagramUtil.getDiagramIdByName(diagramName);
		
		JSONObject param = new JSONObject();
		param.put("ci3dPoint", ci3dPoint);
		param.put("diagram", oldDiagram);
		param.put("diagramEles", diagramEles);
		param.put("id", id);
		param.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
        param.put("xml", xml);
        UpdateDiagramContent updateDiagramContent = new UpdateDiagramContent();
        result = updateDiagramContent.updateDiagramContent(param);
		return result;
}
	/**
	 * @param  diagramName
	 * @param background
	 * @return result
	 */
	public JSONObject setXmlArribute(String diagramName,String arribute, int flag) throws DocumentException{
		JSONObject result;
		result =getDiagramInfo(diagramName);
		assertTrue(result.getBoolean("success"));
		JSONObject oldParam = result.getJSONObject("data");
		JSONObject oldDiagram = oldParam.getJSONObject("diagram");
		String ci3dPoint = oldParam.getString("ci3dPoint");
		String xml = oldParam.getString("xml");
		XmlUtil  xmlUtil = new XmlUtil();
		if(flag==0){
			oldDiagram.put("diagramBgCss", arribute);
		}
		switch(flag){
		  case 0:
		     xml = xmlUtil.addBackGround(xml,arribute);
		      break;
		  case 1:
			 xml = xmlUtil.setOpacity(xml,arribute);
			 break;
		  //case 2:
			// xml = xmlUtil.setUtdatalinkurl(xml,arribute);
			// break;
		}
		JSONArray diagramEles =new JSONArray();
		DiagramUtil diagramUtil = new DiagramUtil();
		BigDecimal id = diagramUtil.getDiagramIdByName(diagramName);
		
		JSONObject param = new JSONObject();
		param.put("ci3dPoint", ci3dPoint);
		param.put("diagram", oldDiagram);
		param.put("diagramEles", diagramEles);
		param.put("id", id);
		param.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
        param.put("xml", xml);
        UpdateDiagramContent updateDiagramContent = new UpdateDiagramContent();
        result = updateDiagramContent.updateDiagramContent(param);
		return result;
	}

	public JSONObject getDiagramInfo(String diagramName){
		QueryDiagramInfoById queryDiagramInfoById  = new QueryDiagramInfoById();
		JSONObject result = queryDiagramInfoById.queryDiagramInfoById(diagramName,true);
		return result;
	}
}
