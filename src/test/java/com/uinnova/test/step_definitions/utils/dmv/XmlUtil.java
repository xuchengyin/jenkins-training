package com.uinnova.test.step_definitions.utils.dmv;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.dmv.ci.QueryPageByIndex;
import com.uinnova.test.step_definitions.api.dmv.image.QueryCommonImageInfoPage;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.base.CiUtil;


/**
 * @author wsl
 * xml工具类
 *
 */
public class XmlUtil {

	/**
	 * @param xml
	 * @throws DocumentException
	 * 测试用
	 */
	public void readStringXml(String xml) throws DocumentException {
		Document doc = DocumentHelper.parseText(xml); // 将字符串转为XML
		Element graphElement = doc.getRootElement(); // 获取根节点                
		//遍历某节点的所有属性     
		for(Iterator it=graphElement.attributeIterator();it.hasNext();){          
			Attribute attribute = (Attribute) it.next();           
		}  

		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); // 获取根节点下的子节点head
		// 遍历root节点
		int id=0;
		while (userObjectIters.hasNext()) {
			Element userObjectEle= (Element) userObjectIters.next();
			for(Iterator it=userObjectEle.attributeIterator();it.hasNext();){          
				Attribute attribute = (Attribute) it.next();           
			}  
		}
	}

	/**
	 * @param xml
	 * @return
	 * 在画布上增加图标
	 * @throws DocumentException 
	 */
	public String addImgUserObject(String xml, String imgName, String x, String y) throws DocumentException{  
		Document doc = DocumentHelper.parseText(xml); // 将字符串转为XML
		Element graphElement = doc.getRootElement(); // 获取根节点                

		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); // 获取根节点下的子节点head
		// 遍历head节点
		int id=0;
		while (userObjectIters.hasNext()) {
			Element userObjectEle= (Element) userObjectIters.next();
			int tempId = Integer.valueOf(userObjectEle.attributeValue("id"));
			if (tempId > id)
				id= tempId;
		}

		//增加UserObject
		Element addEle = rootElement.addElement("UserObject");  
		addEle.addAttribute("label", "");
		addEle.addAttribute("imgCode", imgName);
		addEle.addAttribute("image_node", "true");
		addEle.addAttribute("size-ms", "40,40");
		addEle.addAttribute("size-mx", "60,60");
		addEle.addAttribute("size-ml", "80,80");
		addEle.addAttribute("id", String.valueOf(id+1));
		
		String imgPath = "";
		String imgFullName = "";
		QueryCommonImageInfoPage queryCommonImageInfoPage = new QueryCommonImageInfoPage();
		JSONObject imgResult = queryCommonImageInfoPage.queryCommonImageInfoPage("",1,1,1000);
		JSONArray imgArr = imgResult.getJSONObject("data").getJSONArray("data");
		for (int i=0; i<imgArr.length(); i++){
			JSONObject tempObj = imgArr.getJSONObject(i).getJSONObject("image");
			if (imgName.compareToIgnoreCase(tempObj.getString("imgName"))==0){
				imgFullName = tempObj.getString("imgFullName");
				imgPath = tempObj.getString("imgPath");
				break;
			}
		}
		
		
		Element mxEle = addEle.addElement("mxCell");  
		mxEle.addAttribute("style", "html=1;image;image="+imgPath);
		mxEle.addAttribute("vertex", "1");
		mxEle.addAttribute("parent", "1");
		mxEle.addAttribute("utsize-ms", "40,40");
		mxEle.addAttribute("utsize-mx", "60,60");
		mxEle.addAttribute("utsize-ml", "80,80");

		mxEle.addAttribute("utimgCode", imgName);
		mxEle.addAttribute("utimg_node", "true");
		mxEle.addAttribute("utimgFullName", imgFullName);

		Element mxGrometryEle = mxEle.addElement("mxGeometry"); 
		mxGrometryEle.addAttribute("x", x);
		mxGrometryEle.addAttribute("y", y);
		mxGrometryEle.addAttribute("width", "60");
		mxGrometryEle.addAttribute("height", "60");
		mxGrometryEle.addAttribute("as", "geometry");

		return doc.getRootElement().asXML();
	}  
	
	/**
	 * @param xml
	 * @param x
	 * @param y
	 * @return
	 * @throws DocumentException
	 * 增加容器
	 */
	public String addContainerUserObject(String xml, String x, String y) throws DocumentException{  
		Document doc = DocumentHelper.parseText(xml); // 将字符串转为XML
		Element graphElement = doc.getRootElement(); // 获取根节点                

		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); // 获取根节点下的子节点head
		// 遍历head节点
		int id=0;
		while (userObjectIters.hasNext()) {
			Element userObjectEle= (Element) userObjectIters.next();
			int tempId = Integer.valueOf(userObjectEle.attributeValue("id"));
			if (tempId > id)
				id= tempId;
		}

		//增加UserObject
		Element addEle = rootElement.addElement("UserObject");  
		addEle.addAttribute("label", "Container");
		addEle.addAttribute("size-ms", "133,133");
		addEle.addAttribute("size-mx", "200,200");
		addEle.addAttribute("size-ml", "266,266");
		addEle.addAttribute("open-size-ms", "133,133");
		addEle.addAttribute("open-size-mx", "200,200");
		addEle.addAttribute("open-size-ml", "266,266");
		addEle.addAttribute("close-size-ms", "40,22");
		addEle.addAttribute("close-size-mx", "60,34");
		addEle.addAttribute("clsoe-size-ml", "80,45");
		addEle.addAttribute("minstyle", "shape=image;image=assets/images/class_big.png;verticalLabelPosition=bottom;verticalAlign=top;");
		
		addEle.addAttribute("id", String.valueOf(id+1));
		
				
		
		Element mxEle = addEle.addElement("mxCell");  
		mxEle.addAttribute("style", "shape=swimlaneRounded;swimlaneFillColor=white;whiteSpace=wrap;html=1;rounded=1;startSize=30;arcSize=0;");
		mxEle.addAttribute("vertex", "1");
		mxEle.addAttribute("parent", "1");
		mxEle.addAttribute("utimg_node", "true");
		mxEle.addAttribute("utsize-ms", "133,133");
		mxEle.addAttribute("utsize-mx", "200,200");
		mxEle.addAttribute("utsize-ml", "266,266");
		mxEle.addAttribute("first", "1");
		mxEle.addAttribute("utopen-size-ms", "133,133");
		mxEle.addAttribute("utopen-size-mx", "200,200");
		mxEle.addAttribute("utopen-size-ml", "266,266");
		
		mxEle.addAttribute("utclose-size-ms", "40,22");
		mxEle.addAttribute("utclsoe-size-mx", "60,34");
		mxEle.addAttribute("utclsoe-size-ml", "80,45");
		mxEle.addAttribute("utminstyle", "shape=image;image=assets/images/class_big.png;verticalLabelPosition=bottom;verticalAlign=top;");

		Element mxGrometryEle = mxEle.addElement("mxGeometry"); 
		mxGrometryEle.addAttribute("x", x);
		mxGrometryEle.addAttribute("y", y);
		mxGrometryEle.addAttribute("width", "200");
		mxGrometryEle.addAttribute("height", "200");
		mxGrometryEle.addAttribute("as", "geometry");
		Element mxRectangle = mxGrometryEle.addElement("mxRectangle"); 
		mxRectangle.addAttribute("x", x);
		mxRectangle.addAttribute("y", y);
		mxRectangle.addAttribute("width", "60");
		mxRectangle.addAttribute("height", "34");
		mxRectangle.addAttribute("as", "alternateBounds");

		return doc.getRootElement().asXML();
	}  
	
	/**
	 * @param xml
	 * @param x
	 * @param y
	 * @return
	 * @throws DocumentException
	 * 容器增加筛选规则
	 */
	public String containerAddRuleCondition(String xml, String x, String y, JSONObject utruleCondition) throws DocumentException{  
		Document doc = DocumentHelper.parseText(xml); // 将字符串转为XML
		Element graphElement = doc.getRootElement(); // 获取根节点              

		
		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); // 获取根节点下的子节点head
		// 遍历head节点
		int id=0;
		while (userObjectIters.hasNext()) {
			Element userObjectEle= (Element) userObjectIters.next();
			if (userObjectEle.attribute("label")!=null){
				if ("Container".compareToIgnoreCase(userObjectEle.attributeValue("label"))==0){
					Element tempMxCell = userObjectEle.element("mxCell");
					Element mxGeometry = tempMxCell.element("mxGeometry");
					if (x.compareToIgnoreCase(mxGeometry.attributeValue("x"))==0 && y.compareToIgnoreCase(mxGeometry.attributeValue("y"))==0){
						tempMxCell.setAttributeValue("utruleCondition",utruleCondition.toString());
						break;
					}
				}
			}
		}

		return doc.getRootElement().asXML();
	}  
	
	/**
	 * @param xml
	 * @param imgName
	 * @param x
	 * @param y
	 * @return
	 * @throws DocumentException
	 * 画布删除图标
	 */
	public String removeImgUserObject(String xml, String imgName, String x, String y) throws DocumentException{  
		Document doc = DocumentHelper.parseText(xml); // 将字符串转为XML
		Element graphElement = doc.getRootElement(); // 获取根节点              

		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); // 获取根节点下的子节点head
		while (userObjectIters.hasNext()) {
			Element userObjectEle= (Element) userObjectIters.next();
			if (userObjectEle.attribute("imgCode")!=null){
				if (imgName.compareToIgnoreCase(userObjectEle.attributeValue("imgCode"))==0){
					Element tempMxCell = userObjectEle.element("mxCell");
					Element mxGeometry = tempMxCell.element("mxGeometry");
					if (x.compareToIgnoreCase(mxGeometry.attributeValue("x"))==0 && y.compareToIgnoreCase(mxGeometry.attributeValue("y"))==0){
						rootElement.remove(userObjectEle);
						break;
					}
				}
			}
		}
		return doc.getRootElement().asXML();
	}  
	
	/**
	 * @param xml
	 * @param imgName
	 * @param x
	 * @param y
	 * @return
	 * @throws DocumentException
	 * 图标上新建CI
	 */
	public String imgCreateCI(String xml, String imgName, String ciCode, String ciId, String label, List utdataLabelValObj, String x, String y) throws DocumentException{  
		Document doc = DocumentHelper.parseText(xml); // 将字符串转为XML
		Element graphElement = doc.getRootElement(); // 获取根节点              

		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); // 获取根节点下的子节点head
		while (userObjectIters.hasNext()) {
			Element userObjectEle= (Element) userObjectIters.next();
			if (userObjectEle.attribute("imgCode")!=null){
				if (imgName.compareToIgnoreCase(userObjectEle.attributeValue("imgCode"))==0){
					Element tempMxCell = userObjectEle.element("mxCell");
					Element mxGeometry = tempMxCell.element("mxGeometry");
					if (x.compareToIgnoreCase(mxGeometry.attributeValue("x"))==0 && y.compareToIgnoreCase(mxGeometry.attributeValue("y"))==0){
						userObjectEle.setAttributeValue("label", ciCode);
						userObjectEle.setAttributeValue("data-id", "ci_"+ ciId);
						
						tempMxCell.addAttribute("utdata-values", "[\""+ciCode+"\"]");//"["CI名称"]"
						tempMxCell.addAttribute("utdata-id", userObjectEle.attributeValue("data-id"));

						tempMxCell.addAttribute("utdata-label-list", "[\""+label+"\"]");  
						tempMxCell.addAttribute("utdata-label-val", "{\""+label+"\":\""+ciCode+"\"}"); 

						tempMxCell.addAttribute("utdata-label-val-obj", utdataLabelValObj.toString());  

						tempMxCell.addAttribute("utcode", ciCode);  
						
						
						break;
					}
				}
			}
		}
		return doc.getRootElement().asXML();
	}  
	
	/**
	 * @param xml
	 * @param imgName
	 * @param ciCode
	 * @param ciId
	 * @param label
	 * @param utdataLabelValObj
	 * @param x
	 * @param y
	 * @return
	 * @throws DocumentException
	 * 
	 */
	public String imgRemoveCI(String xml, String imgName, String x, String y) throws DocumentException{  
		Document doc = DocumentHelper.parseText(xml); // 将字符串转为XML
		Element graphElement = doc.getRootElement(); // 获取根节点              
		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); // 获取根节点下的子节点head
		while (userObjectIters.hasNext()) {
			Element userObjectEle= (Element) userObjectIters.next();
			if (userObjectEle.attribute("imgCode")!=null){
				if (imgName.compareToIgnoreCase(userObjectEle.attributeValue("imgCode"))==0){
					Element tempMxCell = userObjectEle.element("mxCell");
					Element mxGeometry = tempMxCell.element("mxGeometry");
					if (x.compareToIgnoreCase(mxGeometry.attributeValue("x"))==0 && y.compareToIgnoreCase(mxGeometry.attributeValue("y"))==0){
					
						userObjectEle.setAttributeValue("data-id", "");
						tempMxCell.remove(tempMxCell.attribute("utdata-id"));  
						tempMxCell.remove(tempMxCell.attribute("utcode"));  
						break;
					}
				}
			}
		}

		return doc.getRootElement().asXML();
	}  

	/**
	 * @param xml
	 * @return
	 * 在画布上增加CI
	 * @throws DocumentException (xml,ciCode,ciId,icon,label, utdataLabelValObj, x, y);//增加C
	 */
	public String addCI(String xml, String ciCode, String ciId, String icon, String label, List utdataLabelValObj, String x, String y) throws DocumentException{  
		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement();      
		Element rootElement  = graphElement.element("root");
		//Iterator userObjectIters = rootElement.elementIterator("UserObject"); 
		List<Element> eleLise = rootElement.elements();
		// 遍历root节点
		int id=0;
		for (int i=0; i<eleLise.size(); i++){
			Element userObjectEle= eleLise.get(i);
			int tempId = Integer.valueOf(userObjectEle.attributeValue("id"));
			if (tempId > id)
				id= tempId;
		}
		/*while (userObjectIters.hasNext()) {
			Element userObjectEle= (Element) userObjectIters.next();
			int tempId = Integer.valueOf(userObjectEle.attributeValue("id"));
			if (tempId > id)
				id= tempId;
		}*/

		//增加UserObject
		Element addEle = rootElement.addElement("UserObject");  
		addEle.addAttribute("label", ciCode);
		addEle.addAttribute("size-ms", "40,40");
		addEle.addAttribute("size-mx", "60,60");
		addEle.addAttribute("size-ml", "80,80");

		addEle.addAttribute("data-id", "ci_"+ciId);
		addEle.addAttribute("code", ciCode);
		//addEle.addAttribute("image_node", "true");
		addEle.addAttribute("id", String.valueOf(id+1));
		Element mxEle = addEle.addElement("mxCell");  

		mxEle.addAttribute("style", "html=1;image;image="+icon+";");
		mxEle.addAttribute("vertex", "1");
		mxEle.addAttribute("parent", "1");
		mxEle.addAttribute("utsize-ms", "40,40");
		mxEle.addAttribute("utsize-mx", "60,60");
		mxEle.addAttribute("utsize-ml", "80,80");
		mxEle.addAttribute("utdata-values", "[\""+ciCode+"\"]");//"["CI名称"]"
		mxEle.addAttribute("utdata-id", addEle.attributeValue("data-id"));

		mxEle.addAttribute("utdata-label-list", "[\""+label+"\"]");  
		mxEle.addAttribute("utdata-label-val", "{\""+label+"\":\""+ciCode+"\"}"); 

		mxEle.addAttribute("utdata-label-val-obj", utdataLabelValObj.toString());  

		mxEle.addAttribute("utcode", ciCode);  
		Element mxGrometryEle = mxEle.addElement("mxGeometry"); 
		mxGrometryEle.addAttribute("x", x);
		mxGrometryEle.addAttribute("y", y);
		mxGrometryEle.addAttribute("width", "60");
		mxGrometryEle.addAttribute("height", "60");
		mxGrometryEle.addAttribute("as", "geometry");

		return doc.getRootElement().asXML();
	}  

	/**
	 * @param xml
	 * @return
	 * 在画布上增加CI
	 * @throws DocumentException (xml,ciCode,ciId,icon,label, utdataLabelValObj, x, y);//增加C
	 */
	public String addRlt(String xml, String clsName, String ciRltId, String sourceCiCode, String targetCiCode) throws DocumentException{  
		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement();      
		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); 
		// 遍历root节点
		int id=0;
		String sourceId = "";
		String targetId = "";
		while (userObjectIters.hasNext()) {
			Element userObjectEle= (Element) userObjectIters.next();
			int tempId = Integer.valueOf(userObjectEle.attributeValue("id"));
			if (tempId > id)
				id= tempId;
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

		//增加UserObject
		Element addEle = rootElement.addElement("UserObject");  
		addEle.addAttribute("label", clsName);
		addEle.addAttribute("data-id", "relation_"+ciRltId);
		addEle.addAttribute("id", String.valueOf(id+1));

		Element mxEle = addEle.addElement("mxCell");  
		mxEle.addAttribute("style", "noEdgeStyle=0;orthogonalLoop=0;curved=0;rounded=1;strokeColor=#000;fillColor=#000;endArrow=classic;strokeWidth=1;");
		mxEle.addAttribute("edge", "1");
		mxEle.addAttribute("parent", "1");
		mxEle.addAttribute("source", sourceId);
		mxEle.addAttribute("target", targetId);
		mxEle.addAttribute("utdata-id", "relation_"+ciRltId);
		mxEle.addAttribute("labelValue", clsName);

		Element mxGrometryEle = mxEle.addElement("mxGeometry"); 
		mxGrometryEle.addAttribute("relative", "1");
		mxGrometryEle.addAttribute("as", "geometry");

		return doc.getRootElement().asXML();
	}  

	/**
	 * @param xml
	 * @param sourceCiCode
	 * @param targetCiCode
	 * @return
	 * @throws DocumentException
	 * 增加连线
	 */
	public String addLine(String xml, String sourceCiCode, String targetCiCode) throws DocumentException{  
		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement();      
		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); 
		// 遍历root节点
		int id=0;
		String sourceId = "";
		String targetId = "";
		while (userObjectIters.hasNext()) {
			Element userObjectEle= (Element) userObjectIters.next();
			int tempId = Integer.valueOf(userObjectEle.attributeValue("id"));
			if (tempId > id)
				id= tempId;
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

		//增加line

		Element mxEle = rootElement.addElement("mxCell");  
		mxEle.addAttribute("id", String.valueOf(id+1));
		mxEle.addAttribute("style", "noEdgeStyle=0;orthogonalLoop=0;curved=0;rounded=1;exitX=1;exitY=0.75;");
		mxEle.addAttribute("edge", "1");
		mxEle.addAttribute("parent", "1");
		mxEle.addAttribute("source", sourceId);
		mxEle.addAttribute("target", targetId);

		Element mxGrometryEle = mxEle.addElement("mxGeometry"); 
		mxGrometryEle.addAttribute("relative", "1");
		mxGrometryEle.addAttribute("as", "geometry");

		return doc.getRootElement().asXML();
	}  

	/**
	 * @param xml
	 * @param sourceCiCode
	 * @param targetCiCode
	 * @return
	 * @throws DocumentException
	 * 箭头反向
	 */
	public String updateLineReverse(String xml, String sourceCiCode, String targetCiCode) throws DocumentException{  
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

		List<Element> eleList = rootElement.elements();
		for (int i=0; i<eleList.size(); i++){
			Element tempEle = eleList.get(i);
			if (tempEle.attribute("source")!=null && tempEle.attribute("target")!=null && sourceId.compareToIgnoreCase(tempEle.attributeValue("source"))==0 && targetId.compareToIgnoreCase(tempEle.attributeValue("target"))==0 ){
				tempEle.setAttributeValue("source", targetId);
				tempEle.setAttributeValue("target", sourceId);
				break;
			}
		}


		return doc.getRootElement().asXML();
	}  
	
	/**
	 * @param xml
	 * @param sourceCiCode
	 * @param targetCiCode
	 * @return
	 * @throws DocumentException
	 * 删除连线
	 */
	public String delLine(String xml, String sourceCiCode, String targetCiCode) throws DocumentException{  
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
				Element  mxTempEle = userObjectEle.element("mxCell");
				if (mxTempEle.attribute("source")!=null && mxTempEle.attribute("target")!=null && sourceId.compareToIgnoreCase(mxTempEle.attributeValue("source"))==0 && targetId.compareToIgnoreCase(mxTempEle.attributeValue("target"))==0 ){
					rootElement.remove(userObjectEle);
					break;
				}
			}
		}

		List<Element> eleList = rootElement.elements();
		for (int i=0; i<eleList.size(); i++){
			Element tempEle = eleList.get(i);
			if (tempEle.attribute("source")!=null && tempEle.attribute("target")!=null && sourceId.compareToIgnoreCase(tempEle.attributeValue("source"))==0 && targetId.compareToIgnoreCase(tempEle.attributeValue("target"))==0 ){
				rootElement.remove(tempEle);
				break;
			}
		}

		return doc.getRootElement().asXML();
	}  

	/**
	 * @param xml
	 * @return
	 * 删除关系
	 * 
	 */
	public String delRlt(String xml, String clsName, String ciId, String sourceCiCode, String targetCiCode) throws DocumentException{  
		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement();      
		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); 
		// 遍历root节点
		int id=0;
		String sourceId = "";
		String targetId = "";
		while (userObjectIters.hasNext()) {
			Element userObjectEle= (Element) userObjectIters.next();
			int tempId = Integer.valueOf(userObjectEle.attributeValue("id"));
			if (tempId > id)
				id= tempId;
			String tempCiCode = userObjectEle.attributeValue("code");
			if (tempCiCode!=null && !tempCiCode.isEmpty()){
				if (sourceCiCode.compareToIgnoreCase(tempCiCode)==0){
					sourceId =userObjectEle.attributeValue("id");
				}
				if (targetCiCode.compareToIgnoreCase(tempCiCode)==0){
					targetId =userObjectEle.attributeValue("id");
				}
				if (clsName.compareToIgnoreCase(userObjectEle.attributeValue("label"))==0){
					Element mxEle = userObjectEle.element("mxCell");  
					if (sourceId.compareToIgnoreCase(mxEle.attributeValue("source"))==0 && targetId.compareToIgnoreCase(mxEle.attributeValue("target"))==0){
						rootElement.remove(userObjectEle);
						break;
					}
				}
			}
		}

		return doc.getRootElement().asXML();
	}  

	/**
	 * @param xml
	 * @return
	 * 在画布上CI添加至容器
	 * @throws DocumentException 
	 */
	public String ciAddContainer(String xml, String ciCode) throws DocumentException{  
		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement();      
		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); 
		// 遍历root节点
		int id=0;
		String x = "";
		String y = "";
		while (userObjectIters.hasNext()) {
			Element userObjectEle= (Element) userObjectIters.next();
			int tempId = Integer.valueOf(userObjectEle.attributeValue("id"));
			if (tempId > id)
				id= tempId;
			String tempCiCode = userObjectEle.attributeValue("code");
			if (tempCiCode!=null && !tempCiCode.isEmpty())
				if (ciCode.compareToIgnoreCase(tempCiCode)==0)
				{
					Element tempMxCell = userObjectEle.element("mxCell");
					Attribute a = tempMxCell.attribute("parent");
					tempMxCell.remove(a);
					tempMxCell.addAttribute("parent", String.valueOf(id+1));

					Element tempMxGrometryEle = tempMxCell.element("mxGeometry");
					x = tempMxGrometryEle.attributeValue("x");
					y = tempMxGrometryEle.attributeValue("y");
					tempMxGrometryEle.setAttributeValue("x", "40");
					tempMxGrometryEle.setAttributeValue("y", "70");
					break;
				}
		}

		Element containerEle = rootElement.addElement("UserObject");  
		containerEle.addAttribute("label", "Container");
		containerEle.addAttribute("size-ms", "40,40");
		containerEle.addAttribute("size-mx", "60,60");
		containerEle.addAttribute("size-ml", "80,80");
		containerEle.addAttribute("minstyle", "shape=image;image=assets/images/class_big.png;verticalLabelPosition=bottom;verticalAlign=top;");
		containerEle.addAttribute("id", String.valueOf(id+1));
		Element mxEle = containerEle.addElement("mxCell");  
		mxEle.addAttribute("style", "swimlane;whiteSpace=wrap;html=1;startSize=30;");
		mxEle.addAttribute("vertex", "1");
		mxEle.addAttribute("parent", "1");
		mxEle.addAttribute("utsize-ms", "40,40");
		mxEle.addAttribute("utsize-mx", "60,60");
		mxEle.addAttribute("utsize-ml", "80,80");
		mxEle.addAttribute("utchild-num", "1");
		Element mxGrometryEle = mxEle.addElement("mxGeometry"); 
		mxGrometryEle.addAttribute("x", x);
		mxGrometryEle.addAttribute("y", y);
		mxGrometryEle.addAttribute("width", "140");
		mxGrometryEle.addAttribute("height", "170");
		mxGrometryEle.addAttribute("as", "geometry");
		Element mxRectangleEle = mxGrometryEle.addElement("mxRectangle"); 

		mxRectangleEle.addAttribute("x", x);
		mxRectangleEle.addAttribute("y", y);
		mxRectangleEle.addAttribute("width", "60");
		mxRectangleEle.addAttribute("height", "34");
		mxRectangleEle.addAttribute("as", "alternateBounds");


		return doc.getRootElement().asXML();
	}  

	/**
	 * @param xml
	 * @return
	 * 给CI添加钻取视图
	 * @throws DocumentException 
	 */
	public String ciAddDrillDown(String xml, String ciCode, String drillDiagramName) throws DocumentException{  
		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement();      
		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); 
		// 遍历root节点
		while (userObjectIters.hasNext()) {
			Element userObjectEle= (Element) userObjectIters.next();
			String tempCiCode = userObjectEle.attributeValue("code");
			if (tempCiCode!=null && !tempCiCode.isEmpty())
				if (ciCode.compareToIgnoreCase(tempCiCode)==0)
				{
					DiagramUtil diagramUtil = new DiagramUtil();
					BigDecimal diagramId = diagramUtil.getDiagramIdByName(drillDiagramName);
					userObjectEle.addAttribute("direction", "down");
					String view = userObjectEle.attributeValue("view");
					if (view==null || view.isEmpty()){
						view = String.valueOf(diagramId);
					}else{
						view = view+","+ String.valueOf(diagramId);
					}
					userObjectEle.setAttributeValue("view", view);

					Element tempMxCell = userObjectEle.element("mxCell");
					tempMxCell.setAttributeValue("utview", view);
					tempMxCell.setAttributeValue("utdirection", "down");
					break;
				}
		}

		return doc.getRootElement().asXML();
	}  

	/**
	 * @param xml
	 * @param ciCode
	 * @param drillDiagramName
	 * @return
	 * @throws DocumentException
	 * Line增加钻取视图
	 */
	public String lineAddDrillDown(String xml, String sourceCiCode, String targetCiCode, String drillDiagramName) throws DocumentException{  
		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement();      
		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); 
		DiagramUtil diagramUtil = new DiagramUtil();
		BigDecimal diagramId = diagramUtil.getDiagramIdByName(drillDiagramName);

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
			}
			
			Element mxTempEle = userObjectEle.element("mxCell");
			if (mxTempEle.attribute("source")!=null && mxTempEle.attribute("target")!=null && sourceId.compareToIgnoreCase(mxTempEle.attributeValue("source"))==0 && targetId.compareToIgnoreCase(mxTempEle.attributeValue("target"))==0 ){
				String view = userObjectEle.attributeValue("view");
				if (view==null || view.isEmpty()){
					view = String.valueOf(diagramId);
				}else{
					view = view+","+ String.valueOf(diagramId);
				}
				userObjectEle.setAttributeValue("direction", "down");
				userObjectEle.setAttributeValue("view", view);

				
				mxTempEle.setAttributeValue("utview", view);
				mxTempEle.setAttributeValue("utdirection", "down");
				hasLine = true;
				break;
			}
		}
		
		//如果上述没找到，可能只是一个连线， 下面接着找
		if (!hasLine){
			List<Element> eleList = rootElement.elements();
			for (int i=0; i<eleList.size(); i++){
				Element tempEle = eleList.get(i);
				if (tempEle.attribute("source")!=null && tempEle.attribute("target")!=null && sourceId.compareToIgnoreCase(tempEle.attributeValue("source"))==0 && targetId.compareToIgnoreCase(tempEle.attributeValue("target"))==0 ){
					String view = tempEle.attributeValue("view");
					if (view==null || view.isEmpty()){
						view = String.valueOf(diagramId);
						if ("mxCell".compareToIgnoreCase(tempEle.getName())==0){
							Element addEle = rootElement.addElement("UserObject"); 
							addEle.setAttributeValue("label", "undefined");
							addEle.setAttributeValue("direction", "down");
							addEle.setAttributeValue("view", view);
							addEle.setAttributeValue("id", tempEle.attributeValue("id"));

							tempEle.setAttributeValue("utview", view);
							tempEle.setAttributeValue("utdirection", "down");
							tempEle.remove(tempEle.attribute("id"));
							Element mxCell =  addEle.addElement("mxCell");
							mxCell.addAttribute("style", tempEle.attributeValue("style"));
							mxCell.addAttribute("edge",  tempEle.attributeValue("edge"));
							mxCell.addAttribute("parent",  tempEle.attributeValue("parent"));
							mxCell.addAttribute("source",  tempEle.attributeValue("source"));
							mxCell.addAttribute("target",  tempEle.attributeValue("target"));
							mxCell.addAttribute("utview", view);
							mxCell.addAttribute("utdirection", "down");

							Element mxGrometryEle = mxCell.addElement("mxGeometry"); 
							mxGrometryEle.addAttribute("relative", "1");
							mxGrometryEle.addAttribute("as", "geometry");
							
							rootElement.remove(tempEle);		
							break;
						}
					}
				}
			}
			
		}
		

		return doc.getRootElement().asXML();
	}  
	
	/**
	 * @param xml
	 * @param sourceCiCode
	 * @param targetCiCode
	 * @param drillDiagramName
	 * @return
	 * @throws DocumentException
	 * 关系增加钻取视图
	 */
	public String rltAddDrillDown(String xml,String sourceCiCode, String targetCiCode, String rltClassName, String drillDiagramName) throws DocumentException{  
		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement();      
		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); 
		DiagramUtil diagramUtil = new DiagramUtil();
		BigDecimal diagramId = diagramUtil.getDiagramIdByName(drillDiagramName);

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
				if (rltClassName.compareToIgnoreCase(userObjectEle.attributeValue("label"))==0){
					String view = userObjectEle.attributeValue("view");
					if (view==null || view.isEmpty()){
						view = String.valueOf(diagramId);
					}else{
						view = view+","+ String.valueOf(diagramId);
					}
					userObjectEle.setAttributeValue("direction", "down");
					userObjectEle.setAttributeValue("view", view);

					
					mxTempEle.setAttributeValue("utview", view);
					mxTempEle.setAttributeValue("utdirection", "down");
					hasRlt = true;
					break;
				}
			}
		}
		
		return doc.getRootElement().asXML();
	}  
	
	/**
	 * @param xml
	 * @param sourceCiCode
	 * @param targetCiCode
	 * @param drillDiagramName
	 * @return
	 * @throws DocumentException
	 * 连线创建关系
	 */
	public String lineCreateRlt(String xml, String sourceCiCode, String targetCiCode, String rltCls, String rltId) throws DocumentException{  
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
			}
			
			Element mxTempEle = userObjectEle.element("mxCell");
			if (mxTempEle.attribute("source")!=null && mxTempEle.attribute("target")!=null && sourceId.compareToIgnoreCase(mxTempEle.attributeValue("source"))==0 && targetId.compareToIgnoreCase(mxTempEle.attributeValue("target"))==0 ){
				
				userObjectEle.setAttributeValue("label", rltCls);
				userObjectEle.setAttributeValue("data-id", "relation_"+rltId);
				mxTempEle.setAttributeValue("utdata-id",  "relation_"+rltId);
				hasLine = true;
				break;
			}
		}
		
		//如果上述没找到，可能只是一个连线， 下面接着找
		if (!hasLine){
			List<Element> eleList = rootElement.elements();
			for (int i=0; i<eleList.size(); i++){
				Element tempEle = eleList.get(i);
				if (tempEle.attribute("source")!=null && tempEle.attribute("target")!=null && sourceId.compareToIgnoreCase(tempEle.attributeValue("source"))==0 && targetId.compareToIgnoreCase(tempEle.attributeValue("target"))==0 ){
						if ("mxCell".compareToIgnoreCase(tempEle.getName())==0){
							Element addEle = rootElement.addElement("UserObject"); 
							addEle.setAttributeValue("label", rltCls);
							addEle.setAttributeValue("data-id", "relation_"+rltId);
							addEle.setAttributeValue("id", tempEle.attributeValue("id"));

							tempEle.remove(tempEle.attribute("id"));
							Element mxCell =  addEle.addElement("mxCell");
							mxCell.addAttribute("style", tempEle.attributeValue("style"));
							mxCell.addAttribute("edge",  tempEle.attributeValue("edge"));
							mxCell.addAttribute("parent",  tempEle.attributeValue("parent"));
							mxCell.addAttribute("source",  tempEle.attributeValue("source"));
							mxCell.addAttribute("target",  tempEle.attributeValue("target"));
							mxCell.setAttributeValue("utdata-id",  "relation_"+rltId);

							Element mxGrometryEle = mxCell.addElement("mxGeometry"); 
							mxGrometryEle.addAttribute("relative", "1");
							mxGrometryEle.addAttribute("as", "geometry");
							
							rootElement.remove(tempEle);		
							break;
						}
				}
			}
			
		}

		return doc.getRootElement().asXML();
	}  
	
	/**
	 * @param xml
	 * @param sourceCiCode
	 * @param targetCiCode
	 * @param oldRltCls
	 * @param rltCls
	 * @param rltId
	 * @return
	 * @throws DocumentException
	 * 删除关系
	 */
	public String removeRlt(String xml, String sourceCiCode, String targetCiCode, String rltCls) throws DocumentException{  
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
				if (rltCls.compareToIgnoreCase(userObjectEle.attributeValue("label")) ==0 ){
					rootElement.remove(userObjectEle);
					hasRlt = true;
					break;
				}
				
			}
		}
	
		return doc.getRootElement().asXML();
	}  
	
	
	/**
	 * @param xml
	 * @param sourceCiCode
	 * @param targetCiCode
	 * @param rltCls
	 * @param displayFlag
	 * @return
	 * @throws DocumentException
	 * 显示或者隐藏关系
	 * displayFlag "1": 显示 "0":隐藏
	 */
	public String hideORDisplayRlt(String xml, String sourceCiCode, String targetCiCode, String rltCls, String displayFlag) throws DocumentException{  
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
				if (rltCls.compareToIgnoreCase(userObjectEle.attributeValue("label")) ==0 ){
					if("1".compareToIgnoreCase(displayFlag)==0){
						mxTempEle.setAttributeValue("showStatus", "1");
					}
					else{
						mxTempEle.setAttributeValue("showStatus", "0");
					}
					hasRlt = true;
					break;
				}
				
			}
		}
	
		return doc.getRootElement().asXML();
	}  
	/**
	 * @param xml
	 * @return
	 * 给CI取消钻取视图
	 * @throws DocumentException 
	 */
	public String ciRemoveDrillDown(String xml, String ciCode, String drillDiagramName) throws DocumentException{  
		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement();      
		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); 
		// 遍历root节点
		while (userObjectIters.hasNext()) {
			Element userObjectEle= (Element) userObjectIters.next();
			String tempCiCode = userObjectEle.attributeValue("code");
			if (tempCiCode!=null && !tempCiCode.isEmpty())
				if (ciCode.compareToIgnoreCase(tempCiCode)==0)
				{
					DiagramUtil diagramUtil = new DiagramUtil();
					BigDecimal diagramId = diagramUtil.getDiagramIdByName(drillDiagramName);
					userObjectEle.addAttribute("direction", "down");
					String view = userObjectEle.attributeValue("view");
					String[] vs = view.split(",");
					String newView ="";
					for (int i=0; i<vs.length; i++){
						String temp = vs[i];
						if (diagramId.compareTo(new BigDecimal(temp))!=0)
						{
							if (newView.isEmpty()){
								newView = temp;
							}
							else{
								newView+=","+temp;
							}
						}
					}

					userObjectEle.setAttributeValue("view", newView);
					Element tempMxCell = userObjectEle.element("mxCell");
					tempMxCell.setAttributeValue("utview", newView);
					tempMxCell.setAttributeValue("utdirection", "down");
					break;
				}
		}

		return doc.getRootElement().asXML();
	}  


	/**
	 * @param xml
	 * @param ciCode
	 * @param drillDiagramName
	 * @return
	 * @throws DocumentException
	 * 给连线去掉钻取视图
	 */
	public String lineRemoveDrillDown(String xml, String sourceCiCode, String targetCiCode, String drillDiagramName) throws DocumentException{  
		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement();      
		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); 
		DiagramUtil diagramUtil = new DiagramUtil();
		BigDecimal diagramId = diagramUtil.getDiagramIdByName(drillDiagramName);
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
			Element  mxTempEle = userObjectEle.element("mxCell");
			if (mxTempEle.attribute("source")!=null && mxTempEle.attribute("target")!=null && sourceId.compareToIgnoreCase(mxTempEle.attributeValue("source"))==0 && targetId.compareToIgnoreCase(mxTempEle.attributeValue("target"))==0 ){
				userObjectEle.addAttribute("direction", "down");
				String view = userObjectEle.attributeValue("view");
				String[] vs = view.split(",");
				String newView ="";
				for (int i=0; i<vs.length; i++){
					String temp = vs[i];
					if (diagramId.compareTo(new BigDecimal(temp))!=0)
					{
						if (newView.isEmpty()){
							newView = temp;
						}
						else{
							newView+=","+temp;
						}
					}
				}

				userObjectEle.setAttributeValue("view", newView);
				Element tempMxCell = userObjectEle.element("mxCell");
				tempMxCell.setAttributeValue("utview", newView);
				tempMxCell.setAttributeValue("utdirection", "down");
				break;
			}
			
		}
		
		return doc.getRootElement().asXML();
	}  

	/**
	 * @param xml
	 * @param sourceCiCode
	 * @param targetCiCode
	 * @param rltClassName
	 * @param drillDiagramName
	 * @return
	 * @throws DocumentException
	 * 给关系删除钻取视图
	 */
	public String rltRemoveDrillDown(String xml, String sourceCiCode, String targetCiCode, String rltClassName, String drillDiagramName) throws DocumentException{  
		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement();      
		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); 
		DiagramUtil diagramUtil = new DiagramUtil();
		BigDecimal diagramId = diagramUtil.getDiagramIdByName(drillDiagramName);
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
			Element  mxTempEle = userObjectEle.element("mxCell");
			if (mxTempEle.attribute("source")!=null && mxTempEle.attribute("target")!=null && sourceId.compareToIgnoreCase(mxTempEle.attributeValue("source"))==0 && targetId.compareToIgnoreCase(mxTempEle.attributeValue("target"))==0 ){
				if (rltClassName.compareToIgnoreCase(userObjectEle.attributeValue("label"))==0){
					userObjectEle.addAttribute("direction", "down");
					String view = userObjectEle.attributeValue("view");
					String[] vs = view.split(",");
					String newView ="";
					for (int i=0; i<vs.length; i++){
						String temp = vs[i];
						if (new BigDecimal(temp).compareTo(diagramId)!=0)
						{
							if (newView.isEmpty()){
								newView = temp;
							}
							else{
								newView+=","+temp;
							}
						}
					}

					userObjectEle.setAttributeValue("view", newView);
					Element tempMxCell = userObjectEle.element("mxCell");
					tempMxCell.setAttributeValue("utview", newView);
					tempMxCell.setAttributeValue("utdirection", "down");
					break;
				}
			}
		}
		
		return doc.getRootElement().asXML();
	}  

	/**
	 * @param xml
	 * @param ciCode
	 * @param labelPosition
	 * @return
	 * @throws DocumentException
	 * 设置cilabel位置
	 */
	public String ciLabelSetup(String xml, String ciCode, String labelPosition) throws DocumentException{  
		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement();      
		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); 
		// 遍历root节点
		while (userObjectIters.hasNext()) {
			Element userObjectEle= (Element) userObjectIters.next();
			String tempCiCode = userObjectEle.attributeValue("code");
			if (tempCiCode!=null && !tempCiCode.isEmpty())
				if (ciCode.compareToIgnoreCase(tempCiCode)==0)
				{
					String newStyle ="";
					Element tempMxCell = userObjectEle.element("mxCell");
					String icon = getClsIconByCiCode(ciCode);
					newStyle ="html=1;image;image="+icon+";";
					if ("上".compareToIgnoreCase(labelPosition)==0){
						newStyle+="labelPosition=center;verticalLabelPosition=top;align=center;verticalAlign=bottom;";
					}
					if ("下".compareToIgnoreCase(labelPosition)==0){
						newStyle+="labelPosition=center;verticalLabelPosition=bottom;align=center;verticalAlign=top;";
					}
					if ("左".compareToIgnoreCase(labelPosition)==0){
						newStyle+="labelPosition=left;verticalLabelPosition=middle;align=right;verticalAlign=middle;";
					}
					if ("右".compareToIgnoreCase(labelPosition)==0){
						newStyle+="labelPosition=right;verticalLabelPosition=middle;align=left;verticalAlign=middle;";
					}
					tempMxCell.setAttributeValue("style", newStyle);
					break;
				}
		}

		return doc.getRootElement().asXML();
	}  


	/**
	 * @param xml
	 * @param ciCode
	 * @return
	 * @throws DocumentException
	 * 获取ci的坐标
	 */
	public Map<String, String> getCoordinate(String xml, String ciCode) throws DocumentException{
		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement();      
		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); 
		// 遍历root节点
		//取ci的坐标
		Map<String, String>  coordinateMap= new HashMap<String, String>();
		String x = "0";
		String y = "0";
		while (userObjectIters.hasNext()) {
			Element userObjectEle= (Element) userObjectIters.next();
			if (userObjectEle.attribute("code")!=null){
				if (ciCode.compareToIgnoreCase(userObjectEle.attributeValue("code"))==0){
					Element mxEle = userObjectEle.element("mxCell"); 
					Element mxGrometryEle = mxEle.element("mxGeometry"); 
					x = mxGrometryEle.attributeValue("x");
					y = mxGrometryEle.attributeValue("y");
					break;
				}
			}
		}
		coordinateMap.put("x", x);
		coordinateMap.put("y", y);
		return coordinateMap;
	}

	/**
	 * @param xml
	 * @param ciCode
	 * @return
	 * @throws DocumentException
	 * 取得最后一个元素的坐标
	 */
	public Map<String, Double> getEndPointCoordinate(String xml) throws DocumentException{
		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement();      
		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); 
		// 遍历root节点
		//取ci的坐标
		Map<String, Double>  coordinateMap= new HashMap<String, Double>();
		Double x = (double) 0;
		Double y = (double) 0;
		Element preObjectEle = null ;
		while (userObjectIters.hasNext()) {
			Element userObjectEle= (Element) userObjectIters.next();

			Element mxEle = userObjectEle.element("mxCell"); 
			Element mxGrometryEle = mxEle.element("mxGeometry"); 
			if (userObjectIters.hasNext()){
				if (mxGrometryEle!=null && mxGrometryEle.attribute("x")!=null){
					preObjectEle = userObjectEle;
				}
				continue;
			}

			if (mxGrometryEle!=null && mxGrometryEle.attribute("x")!=null){
				//最后节点没关系
				x = Double.valueOf(mxGrometryEle.attributeValue("x"));
				y = Double.valueOf(mxGrometryEle.attributeValue("y"));
			}
			else if(preObjectEle!=null) {
				mxEle = preObjectEle.element("mxCell"); 
				mxGrometryEle = mxEle.element("mxGeometry");
				x = Double.valueOf(mxGrometryEle.attributeValue("x"));
				y = Double.valueOf(mxGrometryEle.attributeValue("y"));
			}
		}
		coordinateMap.put("x", x);
		coordinateMap.put("y", y);
		return coordinateMap;
	}



	/**
	 * @param xml
	 * @return
	 * 在画布上删除CI
	 * @throws DocumentException 
	 */
	public String removeUserObject(String xml, String ciCode) throws DocumentException{  
		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement();            
		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); 
		// 遍历UserObject节点
		while (userObjectIters.hasNext()) {
			Element userObjectEle= (Element) userObjectIters.next();
			String dataId = userObjectEle.attributeValue("data-id");
			CiUtil ciUtil = new CiUtil();
			String ciId = ciUtil.getCiId(ciCode).toString();
			if (userObjectEle.attribute("data-id")!=null){
				dataId = dataId.substring(3);

				if (dataId.compareToIgnoreCase(ciId)==0){
					rootElement.remove(userObjectEle);
					break;
				}

			}
		}

		return doc.getRootElement().asXML();
	}  




	/**
	 * @param xml
	 * @param ciCode
	 * @return
	 * @throws DocumentException
	 * CI画图-右键-解除CI
	 */
	public String ciGetRidOfCi(String xml, String ciCode) throws DocumentException{  
		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement();            
		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); 
		// 遍历UserObject节点
		while (userObjectIters.hasNext()) {
			Element userObjectEle= (Element) userObjectIters.next();
			String dataId = userObjectEle.attributeValue("data-id");
			CiUtil ciUtil = new CiUtil();
			String ciId = ciUtil.getCiId(ciCode).toString();
			if (userObjectEle.attribute("data-id")!=null){
				dataId = dataId.substring(3);
				if (dataId.compareToIgnoreCase(ciId)==0){
					userObjectEle.setAttributeValue("data-id", "");
					Element tempMxCell = userObjectEle.element("mxCell");
					tempMxCell.remove(tempMxCell.attribute("utcode"));
					tempMxCell.remove(tempMxCell.attribute("utdata-id"));
					break;
				}

			}
		}
		return doc.getRootElement().asXML();
	}  

	public String ciCreateCi(String xml, String oldCiCode, String ciCode, String ciId, String icon, String label, List utdataLabelValObj, String x, String y) throws DocumentException{  
		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement();      
		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); 
		// 遍历root节点
		int id=0;
		while (userObjectIters.hasNext()) {
			Element userObjectEle= (Element) userObjectIters.next();
			int tempId = Integer.valueOf(userObjectEle.attributeValue("id"));
			if (tempId > id)
				id= tempId;
			if (userObjectEle.attribute("code")!=null){
				if (oldCiCode.compareToIgnoreCase(userObjectEle.attributeValue("code"))==0){
					userObjectEle.setAttributeValue("data-id", "ci_"+ciId);
					Element mxEle = userObjectEle.element("mxCell"); 
					mxEle.setAttributeValue("utdata-values", "[\""+ciCode+"\"]");//"["CI名称"]"
					mxEle.setAttributeValue("utdata-id", userObjectEle.attributeValue("data-id"));
					mxEle.setAttributeValue("utdata-label-list", "[\""+label+"\"]");  
					mxEle.setAttributeValue("utdata-label-val", "{\""+label+"\":\""+ciCode+"\"}"); 

					mxEle.setAttributeValue("utdata-label-val-obj", utdataLabelValObj.toString());  

					mxEle.setAttributeValue("utcode", ciCode); 
					break;
				}
			}
		}
		return doc.getRootElement().asXML();
	}  


	/**
	 * @param xml
	 * @param ciCode
	 * @param dynamicCiId
	 * @return
	 * @throws DocumentException
	 * ci 上挂载动态节点
	 */
	public String ciMountDynamicNode(String xml, String ciCode, String dynamicCiId) throws DocumentException{  
		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement();      
		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); 
		// 遍历root节点
		int id=0;
		while (userObjectIters.hasNext()) {
			Element userObjectEle= (Element) userObjectIters.next();
			if (userObjectEle.attribute("code")!=null){
				if (ciCode.compareToIgnoreCase(userObjectEle.attributeValue("code"))==0){
					Element mxEle = userObjectEle.element("mxCell"); 
					if (mxEle.attribute("uttags-info")==null){
						mxEle.addAttribute("uttags-info", "{\"tagIds\":[],\"classIds\":[],\"words\":[]}");
					}
					if (mxEle.attribute("uttags-params-info")==null){
						mxEle.addAttribute("uttags-params-info", "[]");
					}
					boolean hasChild = false;
					if (mxEle.attribute("uttags-selection-cis")==null){
						List cisList = new ArrayList<>();
						cisList.add("\""+dynamicCiId+"\"");
						mxEle.addAttribute("uttags-selection-cis",cisList.toString());
					}else{
						String temp = mxEle.attributeValue("uttags-selection-cis");
						temp =temp.substring(1,temp.length()-1);
						List cisList = new ArrayList<>();
						String[] tempL= temp.split(",");
						for (int k=0; k<tempL.length; k++){
							String s = tempL[k];
							cisList.add(s);
							if (s.compareToIgnoreCase("\""+ dynamicCiId+"\"")==0){
								hasChild =true;
								break;
							}
						}
						if (!hasChild){
							cisList.add("\""+dynamicCiId+"\"");
							mxEle.setAttributeValue("uttags-selection-cis", cisList.toString());
						}
					}

					if (mxEle.attribute("utchild-num")==null){
						mxEle.addAttribute("utchild-num", "1");
					}else{
						String childNum = mxEle.attributeValue("utchild-num");
						if (childNum.isEmpty())
							childNum="1";
						else{
							if (!hasChild){
								childNum=String.valueOf(Integer.valueOf(childNum)+1);
								mxEle.setAttributeValue("utchild-num", childNum);
							}
						}
					}
					break;
				}
			}
		}
		//xml = doc.getRootElement().asXML();
		return doc.getRootElement().asXML();
	}  

	/**
	 * @param xml
	 * @param ciCode
	 * @param dynamicCiId
	 * @return
	 * @throws DocumentException
	 * 画布上挂载动态节点
	 */
	public String canvasMountDynamicNode(String xml, String ciCode, String dynamicCiId) throws DocumentException{  
		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement(); 

		Element rootElement  = graphElement.element("root");
		List<Element> eleList = rootElement.elements();
		for (int i=0; i<eleList.size(); i++){
			Element tempEle  =eleList.get(i);
			if ("1".compareToIgnoreCase(tempEle.attributeValue("id")) ==0){
				if (tempEle.attribute("uttags-info")==null){
					tempEle.addAttribute("uttags-info", "{\"tagIds\":[],\"classIds\":[],\"words\":[]}");
				}
				if (tempEle.attribute("uttags-params-info")==null){
					tempEle.addAttribute("uttags-params-info", "[]");
				}
				boolean hasChild = false;
				if (tempEle.attribute("uttags-selection-cis")==null){
					List cisList = new ArrayList<>();
					cisList.add("\""+dynamicCiId+"\"");
					tempEle.addAttribute("uttags-selection-cis",cisList.toString());
				}else{
					String temp = tempEle.attributeValue("uttags-selection-cis");
					temp =temp.substring(1,temp.length()-1);
					List cisList = new ArrayList<>();
					String[] tempL= temp.split(",");
					for (int k=0; k<tempL.length; k++){
						String s = tempL[k];
						cisList.add(s);
						if (s.compareToIgnoreCase("\""+ dynamicCiId+"\"")==0){
							hasChild =true;
							break;
						}
					}
					if (!hasChild){
						cisList.add("\""+dynamicCiId+"\"");
						tempEle.setAttributeValue("uttags-selection-cis", cisList.toString());
					}
				}
				break;
			}
		}

		//xml = doc.getRootElement().asXML();
		return doc.getRootElement().asXML();
	}  

	/**
	 * 新建视图后生成的xml
	 * @return
	 */
	public String buildDefaultXML(){  
		//DocumentHelper提供了创建Document对象的方法  
		Document document = DocumentHelper.createDocument();  
		//添加节点信息  
		Element graphElement = document.addElement("mxGraphModel");  
		graphElement.addAttribute("grid", "1");
		graphElement.addAttribute("gridSize", "10");
		graphElement.addAttribute("guides", "1");
		graphElement.addAttribute("tooltips", "0");
		graphElement.addAttribute("connect", "1");
		graphElement.addAttribute("arrows", "1");
		graphElement.addAttribute("fold", "1");
		graphElement.addAttribute("page", "0");
		graphElement.addAttribute("pageScale", "1");
		graphElement.addAttribute("pageWidth", "850");
		graphElement.addAttribute("pageHeight", "1100");
		graphElement.addAttribute("update-history-view", "1");
		graphElement.addAttribute("uv-attr-show", "{}");//{"10615":["CI Code"]}
		graphElement.addAttribute("uv-auto-layout", "0");
		graphElement.addAttribute("upanel-width", "0");
		graphElement.addAttribute("upanel-height", "0");
		graphElement.addAttribute("upanel-enabled", "0");

		Element rootElement = graphElement.addElement("root"); 
		//默认1
		Element uoElement0 = rootElement.addElement("UserObject");  
		uoElement0.addAttribute("label", "undefined");
		uoElement0.addAttribute("image_node", "true");
		uoElement0.addAttribute("id", "0");
		Element mxElement0 = uoElement0.addElement("mxCell");  
		mxElement0.addAttribute("utimage_node", "true");  
		//默认2
		Element uoElement = rootElement.addElement("UserObject");  
		uoElement.addAttribute("label", "undefined");
		uoElement.addAttribute("image_node", "true");
		uoElement.addAttribute("id", "1");
		Element mxElement = uoElement.addElement("mxCell");  
		mxElement.addAttribute("parent", "0");  
		mxElement.addAttribute("utimage_node", "true");  
		return document.getRootElement().asXML();
	}



	private String getClsIconByCiCode(String ciCode){
		CiClassUtil ciClsUtil = new CiClassUtil();
		String clsName = ciClsUtil.getCiClassNameByCiCode(ciCode);
		QueryPageByIndex queryPageByIndex = new QueryPageByIndex();
		JSONObject ciClsResult = queryPageByIndex.queryClsPageByIndex(1,1000);
		JSONArray arr = ciClsResult.getJSONObject("data").getJSONArray("classInfos");
		String icon ="";
		for (int k=0; k<arr.length(); k++){
			JSONObject temp =(JSONObject) arr.get(k);
			JSONObject ciO= temp.getJSONObject("ciClass");
			if (clsName.compareToIgnoreCase(ciO.getString("className"))==0){
				icon = ciO.getString("icon");
				break;
			}
		}
		return icon;
	}

	/**
	 * @param xml
	 * @param ciCode
	 * @return
	 * @throws DocumentException
	 * 是否存在某个CI
	 */
	public boolean hasCiCode(String xml, String ciCode) throws DocumentException{
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
				if (ciCode.compareToIgnoreCase(userObjectEle.attributeValue("code"))==0){//解除cI的时候UserObject 的这个属性不会删除，所以用下面一层比较
					Element mxEle = userObjectEle.element("mxCell");  
					if (ciCode.compareToIgnoreCase(mxEle.attributeValue("utcode"))==0){
						hasCICode = true;
						break;
					}
				}
			}
		}
		return hasCICode;
	}

	/**
	 * @param xml
	 * @param ciCode
	 * @return
	 * @throws DocumentException
	 * 是否存在某个关系
	 */
	public boolean hasRlt(String xml, String rltId) throws DocumentException{
		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement();            
		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); 
		// 遍历UserObject节点
		//取容器
		boolean hasRlt = false;
		List<String> containerList = new ArrayList<String>();
		while (userObjectIters.hasNext()) {
			Element userObjectEle= (Element) userObjectIters.next();
			if (userObjectEle.attribute("data-id")!=null){
				if (("relation_"+rltId).compareToIgnoreCase(userObjectEle.attributeValue("data-id"))==0){//解除cI的时候UserObject 的这个属性不会删除，所以用下面一层比较
					hasRlt = true;
					break;
				}
			}
		}
		return hasRlt;
	}

	/**
	 * @param xml
	 * @return
	 * 在画布上增加图片
	 * @throws DocumentException (xml,icon);//添加图片
	 */
	public String addImage(String xml, String icon) throws DocumentException{  
		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement();      
		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); 
		// 遍历root节点
		int id=0;
		while (userObjectIters.hasNext()) {
			Element userObjectEle= (Element) userObjectIters.next();
			int tempId = Integer.valueOf(userObjectEle.attributeValue("id"));
			if (tempId > id)
				id= tempId;
		}

		//增加UserObject
	   Element addEle = rootElement.addElement("UserObject");  
		Element mxEle = addEle.addElement("mxCell");  

		mxEle.addAttribute("style", "html=1;image;image="+icon+";");
		mxEle.addAttribute("vertex", "1");
		mxEle.addAttribute("parent", "1");
		mxEle.addAttribute("utsize-ms", "40,40");
		mxEle.addAttribute("utsize-mx", "60,60");
		mxEle.addAttribute("utsize-ml", "80,80"); 
		Element mxGrometryEle = mxEle.addElement("mxGeometry"); 
		mxGrometryEle.addAttribute("width", "60");
		mxGrometryEle.addAttribute("height", "60");
		mxGrometryEle.addAttribute("as", "geometry");
		return doc.getRootElement().asXML();
	}  
	/**
	 * @param xml
	 * @return
	 * 在画布上选择颜色
	 * @throws DocumentException (xml,background);//添加背景颜色
	 */
	public String addBackGround(String xml, String background) throws DocumentException{  
		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement();
		graphElement.setAttributeValue("background", background);
		return doc.getRootElement().asXML();
	}  
	/**
	 * @param xml
	 * @return
	 * 在画布上选择背景
	 * @throws DocumentException (xml,imagePath);//插入背景
	 */
	public String insertImage(String xml,String imagePath) throws DocumentException{
		Document doc = DocumentHelper.parseText(xml);
		Element graphElemen = doc.getRootElement();
		graphElemen.setAttributeValue("background-url", imagePath);
		return doc.getRootElement().asXML();
	}
	
	public String setOpacity(String xml,String opacity) throws DocumentException{
		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement();      
		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); 
		// 遍历root节点
		int id=0;
		while (userObjectIters.hasNext()) {
			Element userObjectEle= (Element) userObjectIters.next();
			int tempId = Integer.valueOf(userObjectEle.attributeValue("id"));
			if (tempId > id)
				id= tempId;
		}

		//增加UserObject
	   Element addEle = rootElement.addElement("UserObject");  
		Element mxEle = addEle.addElement("mxCell");  

		mxEle.addAttribute("style", "html=1;image;image=;opacity="+opacity+";");
		mxEle.addAttribute("vertex", "1");
		mxEle.addAttribute("parent", "1");
		mxEle.addAttribute("utsize-ms", "40,40");
		mxEle.addAttribute("utsize-mx", "60,60");
		mxEle.addAttribute("utsize-ml", "80,80"); 
		Element mxGrometryEle = mxEle.addElement("mxGeometry"); 
		mxGrometryEle.addAttribute("width", "60");
		mxGrometryEle.addAttribute("height", "60");
		mxGrometryEle.addAttribute("as", "geometry");
		return doc.getRootElement().asXML();
	}
	/**
	 * @param xml
	 * @param attribute
	 * @return 
	 * @throws DocumentException 
	 */
	public String setUtfilterValue(String xml, String ciCode,String attribute) throws DocumentException{
		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement();      
		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); 
		while(userObjectIters.hasNext()){
			Element userObjectEle = (Element)userObjectIters.next();
			if (userObjectEle.attribute("code")!=null){
				if (ciCode.compareToIgnoreCase(userObjectEle.attributeValue("code"))==0){
			     Element mxEle = userObjectEle.element("mxCell");
			     mxEle.setAttributeValue("utfilter-value", attribute);	
			     }
			}
		}
		return doc.getRootElement().asXML();
	}
	/**
	 * @param xml
	 * @param attribute
	 * @return 
	 * @throws DocumentException 
	 */
	public String setUtsizems(String xml, String ciCode,String attribute) throws DocumentException{
		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement();      
		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); 
		while(userObjectIters.hasNext()){
			Element userObjectEle = (Element)userObjectIters.next();
			if (userObjectEle.attribute("code")!=null){
				if (ciCode.compareToIgnoreCase(userObjectEle.attributeValue("code"))==0){
			     Element mxEle = userObjectEle.element("mxCell");
			     mxEle.setAttributeValue("utsize-ms", attribute);	
			     }
			}
		}
		return doc.getRootElement().asXML();
	}
	/**
	 * @param xml
	 * @param attribute
	 * @return 
	 * @throws DocumentException 
	 */
	public String setUtsizemx(String xml, String ciCode,String attribute) throws DocumentException{
		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement();      
		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); 
		while(userObjectIters.hasNext()){
			Element userObjectEle = (Element)userObjectIters.next();
			if (userObjectEle.attribute("code")!=null){
				if (ciCode.compareToIgnoreCase(userObjectEle.attributeValue("code"))==0){
			     Element mxEle = userObjectEle.element("mxCell");
			     mxEle.setAttributeValue("utsize-mx", attribute);	
			     }
			}
		}
		return doc.getRootElement().asXML();
	}
	/**
	 * @param xml
	 * @param attribute
	 * @return 
	 * @throws DocumentException 
	 */
	public String setUtsizeml(String xml, String ciCode,String attribute) throws DocumentException{
		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement();      
		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); 
		while(userObjectIters.hasNext()){
			Element userObjectEle = (Element)userObjectIters.next();
			if (userObjectEle.attribute("code")!=null){
				if (ciCode.compareToIgnoreCase(userObjectEle.attributeValue("code"))==0){
			     Element mxEle = userObjectEle.element("mxCell");
			     mxEle.setAttributeValue("utsize-ml", attribute);	
			     }
			}
		}
		return doc.getRootElement().asXML();
	}
	
	/**
	 * @param xml
	 * @param attribute
	 * @return 
	 * @throws DocumentException 
	 */
	public String setHorizontal(String xml, String ciCode,String attribute) throws DocumentException{
		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement();      
		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); 
		while(userObjectIters.hasNext()){
			Element userObjectEle = (Element)userObjectIters.next();
			if (userObjectEle.attribute("code")!=null){
				if (ciCode.compareToIgnoreCase(userObjectEle.attributeValue("code"))==0){
			     Element mxEle = userObjectEle.element("mxCell");
			     mxEle.setAttributeValue("horizontal", attribute);	
			     }
			}
		}
		return doc.getRootElement().asXML();
	}
	/**
	 * @param xml
	 * @param attribute
	 * @return
	 * 在画布上设置外部链接
	 * @throws DocumentException (xml,attribute);//设置外部链接
	 */
	public String setUtdatalinkurl(String xml,String ciCode,String attribute) throws DocumentException{	
		Document doc = DocumentHelper.parseText(xml); 
	    Element graphElement = doc.getRootElement();      
		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); 
		while(userObjectIters.hasNext()){
			Element userObjectEle = (Element)userObjectIters.next();
			if (userObjectEle.attribute("code")!=null){
				if (ciCode.compareToIgnoreCase(userObjectEle.attributeValue("code"))==0){
			     Element mxEle = userObjectEle.element("mxCell");
			     mxEle.setAttributeValue("utdata-link-url", attribute);	
			     }
			}
		}
	return doc.getRootElement().asXML();
	}
	public static void main(String[] args) throws DocumentException {
		XmlUtil test = new XmlUtil(); 
		test.buildDefaultXML();

		/*String xmlString ="<mxGraphModel dx=\"-1\" dy=\"0\" grid=\"1\" gridSize=\"10\" guides=\"1\" tooltips=\"0\" connect=\"1\" arrows=\"1\" fold=\"1\" page=\"0\" pageScale=\"1\" pageWidth=\"850\" pageHeight=\"1100\" update-history-view=\"1\" uv-attr-show=\"{&quot;10615&quot;:[&quot;CI Code&quot;]}\" uv-auto-layout=\"0\" upanel-width=\"0\" upanel-height=\"0\" upanel-enabled=\"0\"><root><UserObject label=\"undefined\" image_node=\"true\" id=\"0\"><mxCell utimage_node=\"true\"/></UserObject><UserObject label=\"undefined\" image_node=\"true\" id=\"1\"><mxCell parent=\"0\" utimage_node=\"true\"/></UserObject><UserObject label=\"信用卡工单1\" size-ms=\"40,40\" size-mx=\"60,60\" size-ml=\"80,80\" data-id=\"ci_22716\" code=\"信用卡工单1\" id=\"4\"><mxCell style=\"html=1;image;image=http://192.168.1.82:1512/vmdb-sso/rsm/cli/read/122/52430.png\" vertex=\"1\" parent=\"1\" utsize-ms=\"40,40\" utsize-mx=\"60,60\" utsize-ml=\"80,80\" utdata-values=\"[&quot;信用卡工单1&quot;]\" utdata-id=\"ci_22716\" utdata-label-list=\"[&quot;CI Code&quot;]\" utdata-label-val=\"{&quot;CI Code&quot;:&quot;信用卡工单1&quot;}\" utdata-label-val-obj=\"[{&quot;value&quot;:&quot;信用卡工单1&quot;,&quot;key&quot;:&quot;CI Code&quot;,&quot;$$hashKey&quot;:&quot;object:3542&quot;},{&quot;value&quot;:&quot;信用卡工单&quot;,&quot;key&quot;:&quot;Name&quot;,&quot;$$hashKey&quot;:&quot;object:3543&quot;},{&quot;value&quot;:&quot;Chandler&quot;,&quot;key&quot;:&quot;Supporter&quot;,&quot;$$hashKey&quot;:&quot;object:3544&quot;},{&quot;value&quot;:&quot;7*24&quot;,&quot;key&quot;:&quot;Service Time&quot;,&quot;$$hashKey&quot;:&quot;object:3545&quot;},{&quot;value&quot;:&quot;重要&quot;,&quot;key&quot;:&quot;Important Level&quot;,&quot;$$hashKey&quot;:&quot;object:3546&quot;}]\" utcode=\"信用卡工单1\"><mxGeometry x=\"440\" y=\"130\" width=\"60\" height=\"60\" as=\"geometry\"/></mxCell></UserObject></root></mxGraphModel>";
		test.addUserObject(xmlString,"", "");*/

	}
}
