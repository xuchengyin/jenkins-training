package com.uinnova.test.step_definitions.utils.dmv;

import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class SvgUtil {
	/**
	 * @param xml
	 * @return
	 * 在画布上增加内容时修改SVG
	 * @throws DocumentException 
	 */
	public String addCiSvg(String svg, String xml, String clsIcon, String ciCode, String x, String y) throws DocumentException{  
		Document doc = DocumentHelper.parseText(svg); 
		Element svgElement = doc.getRootElement();  
		svgElement.setAttributeValue("width", "100%");
		svgElement.setAttributeValue("height", "100%");
		Element gElement  = svgElement.element("g");
		List<Element> listElement=gElement.elements();
		Element imgEle =    gElement.addElement("image");
		//添加第一个节点位置
		double imageX =2.5;
		double imageY= -0.5;
		double preImageY = -0.5;
		double preImageX = 2.5;
		if (listElement!=null && listElement.size()>0){
			Element tempEle= listElement.get(listElement.size()-1);
			if ("g".compareToIgnoreCase(tempEle.getName())==0){
				tempEle= listElement.get(listElement.size()-2);
			}
			preImageX  = Double.valueOf(tempEle.attributeValue("x"));

			preImageY  = Double.valueOf(tempEle.attributeValue("y"));
			XmlUtil xu = new XmlUtil();
			Map<String, Double> endCoordinate = xu.getEndPointCoordinate(xml);
			imageX = Double.valueOf(x)-endCoordinate.get("x")+preImageX;
			imageY = Double.valueOf(y)-endCoordinate.get("y")+preImageY;
		}


		svgElement.setAttributeValue("width", String.valueOf(imageX+81)+"px");
		svgElement.setAttributeValue("height", String.valueOf(imageY+81.5)+"px");

		imgEle.addAttribute("x", String.valueOf(imageX));
		imgEle.addAttribute("y", String.valueOf(imageY));
		imgEle.addAttribute("width", "60");
		imgEle.addAttribute("height", "60");
		imgEle.addAttribute("xlink:href", clsIcon);
		imgEle.addAttribute("pointer-events", "none");

		Double foreignObjY = 68+Double.valueOf(imageY);
		Double foreignObjX = imageX;

		Element gEle =    gElement.addElement("g");
		gEle.addAttribute("transform", "translate("+foreignObjX+","+foreignObjY+")");
		//gEle.addAttribute("transform", "translate(0.5,67.5)");
		Element switchEle =    gEle.addElement("switch");
		Element foEle =    switchEle.addElement("foreignObject");

		foEle.addAttribute("style", "overflow:visible;");
		foEle.addAttribute("pointer-events", "all");
		foEle.addAttribute("width", "45");
		foEle.addAttribute("height", "12");
		foEle.addAttribute("requiredFeatures", "http://www.w3.org/TR/SVG11/feature#Extensibility");

		Element div1 =    foEle.addElement("div");
		div1.addAttribute("xmlns", "http://www.w3.org/1999/xhtml");
		div1.addAttribute("style", "display: inline-block; font-size: 12px; font-family: 微软雅黑; color: rgb(0, 0, 0); line-height: 1.2; vertical-align: top; white-space: nowrap; text-align: center;");

		Element div2 =    div1.addElement("div");
		div2.addAttribute("xmlns", "http://www.w3.org/1999/xhtml");
		div2.addAttribute("style", "display:inline-block;text-align:inherit;text-decoration:inherit;");
		div2.setText(ciCode);
		Element textEle =    switchEle.addElement("text");

		textEle.addAttribute("x", "34");
		textEle.addAttribute("y", "12");
		textEle.addAttribute("fill", "#000000");
		textEle.addAttribute("text-anchor", "middle");
		textEle.addAttribute("font-size", "12px");
		textEle.addAttribute("font-family", "微软雅黑");
		textEle.setText(ciCode);

		return doc.getRootElement().asXML();
	}  



	/**
	 * @param xml
	 * @return
	 * ci右键关系绘图时修改svg--待修改
	 * @throws DocumentException 
	 */
	public String cIRelationDrawSvg(String svg, String xml, String clsIcon, String ciCode, String x, String y) throws DocumentException{  
		Document doc = DocumentHelper.parseText(svg); 
		Element svgElement = doc.getRootElement();  
		svgElement.setAttributeValue("width", "100%");
		svgElement.setAttributeValue("height", "100%");
		Element gElement  = svgElement.element("g");
		List<Element> listElement=gElement.elements();
		Element imgEle =    gElement.addElement("image");
		//添加第一个节点位置
		double imageX =2.5;
		double imageY= -0.5;
		double preImageY = -0.5;
		double preImageX = 2.5;
		if (listElement!=null && listElement.size()>0){
			Element tempEle= listElement.get(listElement.size()-1);
			if ("g".compareToIgnoreCase(tempEle.getName())==0){
				tempEle= listElement.get(listElement.size()-2);
			}
			preImageX  = Double.valueOf(tempEle.attributeValue("x"));

			preImageY  = Double.valueOf(tempEle.attributeValue("y"));
			XmlUtil xu = new XmlUtil();
			Map<String, Double> endCoordinate = xu.getEndPointCoordinate(xml);
			imageX = Double.valueOf(x)-endCoordinate.get("x")+preImageX;
			imageY = Double.valueOf(y)-endCoordinate.get("y")+preImageY;
		}


		svgElement.setAttributeValue("width", String.valueOf(imageX+81)+"px");
		svgElement.setAttributeValue("height", String.valueOf(imageY+81.5)+"px");

		imgEle.addAttribute("x", String.valueOf(imageX));
		imgEle.addAttribute("y", String.valueOf(imageY));
		imgEle.addAttribute("width", "60");
		imgEle.addAttribute("height", "60");
		imgEle.addAttribute("xlink:href", clsIcon);
		imgEle.addAttribute("pointer-events", "none");

		Double foreignObjY = 68+Double.valueOf(imageY);
		Double foreignObjX = imageX;

		Element gEle =    gElement.addElement("g");
		gEle.addAttribute("transform", "translate("+foreignObjX+","+foreignObjY+")");
		//gEle.addAttribute("transform", "translate(0.5,67.5)");
		Element switchEle =    gEle.addElement("switch");
		Element foEle =    switchEle.addElement("foreignObject");

		foEle.addAttribute("style", "overflow:visible;");
		foEle.addAttribute("pointer-events", "all");
		foEle.addAttribute("width", "45");
		foEle.addAttribute("height", "12");
		foEle.addAttribute("requiredFeatures", "http://www.w3.org/TR/SVG11/feature#Extensibility");

		Element div1 =    foEle.addElement("div");
		div1.addAttribute("xmlns", "http://www.w3.org/1999/xhtml");
		div1.addAttribute("style", "display: inline-block; font-size: 12px; font-family: 微软雅黑; color: rgb(0, 0, 0); line-height: 1.2; vertical-align: top; white-space: nowrap; text-align: center;");

		Element div2 =    div1.addElement("div");
		div2.addAttribute("xmlns", "http://www.w3.org/1999/xhtml");
		div2.addAttribute("style", "display:inline-block;text-align:inherit;text-decoration:inherit;");
		div2.setText(ciCode);
		Element textEle =    switchEle.addElement("text");

		textEle.addAttribute("x", "34");
		textEle.addAttribute("y", "12");
		textEle.addAttribute("fill", "#000000");
		textEle.addAttribute("text-anchor", "middle");
		textEle.addAttribute("font-size", "12px");
		textEle.addAttribute("font-family", "微软雅黑");
		textEle.setText(ciCode);

		return doc.getRootElement().asXML();
	}  
}
