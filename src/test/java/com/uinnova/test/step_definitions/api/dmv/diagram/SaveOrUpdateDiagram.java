package com.uinnova.test.step_definitions.api.dmv.diagram;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.contant.Contants;
import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.api.base.ciExcelBatchImport.ImportExcel;
import com.uinnova.test.step_definitions.api.dmv.ci.QueryList;
import com.uinnova.test.step_definitions.api.dmv.png2mxgraph.GetAiProjectRoot;
import com.uinnova.test.step_definitions.api.dmv.png2mxgraph.Parser;
import com.uinnova.test.step_definitions.api.dmv.visio.Parser2Json;
import com.uinnova.test.step_definitions.utils.base.CiUtil;
import com.uinnova.test.step_definitions.utils.common.AssertResult;
import com.uinnova.test.step_definitions.utils.common.QaUtil;
import com.uinnova.test.step_definitions.utils.dmv.Ci3dPointUtil;
import com.uinnova.test.step_definitions.utils.dmv.DiagramUtil;
import com.uinnova.test.step_definitions.utils.dmv.SvgUtil;
import com.uinnova.test.step_definitions.utils.dmv.XmlUtil;

/**
 * 编写时间:2017-11-15
 * 编写人:sunsl
 * 功能介绍:DMV广场新建视图类
 */
public class SaveOrUpdateDiagram extends RestApi{
	public JSONObject saveOrUpdateDiagram(String TelmplateDiagramName,String name,String diagramDesc){
		String url = ":1511/tarsier-vmdb/dmv/diagram/saveOrUpdateDiagram";
		JSONObject param = new JSONObject();
		QueryDiagramTemplate qdt = new QueryDiagramTemplate();
		JSONObject qdtResult = qdt.queryDiagramTemplate();
		JSONArray data = qdtResult.getJSONArray("data");
		String ci3dPoint = "";
		String xml = "";
		for(int i = 0; i < data.length(); i ++){
			JSONObject square = data.getJSONObject(0);
			JSONObject diagram = square.getJSONObject("diagram");
			if(diagram.getString("name").equals(TelmplateDiagramName)){
				ci3dPoint = square.getString("ci3dPoint");
				xml = square.getString("xml");
				break;
			}
		}
		// 新建视图		
		JSONObject diagram = new JSONObject();
		diagram.put("diagramDesc", diagramDesc);
		diagram.put("diagramType", 1);
		diagram.put("dirId", 0);
		diagram.put("isOpen", 0);
		diagram.put("name", name);
		diagram.put("status", 1);
		param.put("autoName", true);
		param.put("diagram", diagram);
		param.put("ci3dPoint", ci3dPoint);
		param.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
		param.put("xml", xml);
		return doRest(url, param.toString(), "POST");
	}
	/**
	 * @param result
	 * @param diagramNameList
	 * @return resultObj
	 */
	public JSONObject saveOrUpdateDiagram(JSONObject result,List diagramNameList){
		String url = ":1511/tarsier-vmdb/dmv/diagram/saveOrUpdateDiagram";
		JSONObject param = new JSONObject();
		JSONObject data = result.getJSONObject("data");
		String ci3dPoint = data.getString("ci3dPoint");
		String xml = data.getString("xml");
		JSONObject diagram = data.getJSONObject("diagram");
		JSONArray diagramEles = data.getJSONArray("diagramEles");
		String rltDiagramIds = new String();
		JSONObject diagramEle = new JSONObject();
		diagramEle.put("eleType", 7);
		DiagramUtil diagramUtil = new DiagramUtil();
		for(int i = 0; i < diagramNameList.size(); i++){
			String diagramName = (String)diagramNameList.get(i);
			BigDecimal diagramId = diagramUtil.getDiagramIdByName(diagramName);
			if(i ==0){
			  rltDiagramIds = diagramId.toString();
			}else{
			  rltDiagramIds = rltDiagramIds + "," + diagramId.toString();
			}
		}
		diagramEle.put("rltDiagramIds", rltDiagramIds);
		diagramEles.put(diagramEle);
		param.put("ci3dPoint", ci3dPoint);
		param.put("diagram", diagram);
		param.put("diagramEles", diagramEles);
		param.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
		param.put("xml", xml);
		return doRest(url,param.toString(),"POST");
	}
	/**
	 * @param diagramName
	 * @param diagramDesc
	 * @param dirName
	 * @return resultObj
	 */
	public JSONObject createNewDiagramAgain(String diagramName, String diagramDesc, String dirName, String kw){
		JSONObject param = new JSONObject();
		if (diagramName.isEmpty())
			diagramName = "";
		if (diagramDesc.isEmpty())
			diagramDesc = "";
		if (dirName.isEmpty())
			dirName = "0";
		DiagramUtil dUtil = new DiagramUtil();
		int dirID = dUtil.getDirIdByName(dirName);

		JSONObject diagram = new JSONObject();
		diagram.put("diagramDesc", diagramDesc);
		diagram.put("diagramType", 1);
		diagram.put("dirId", dirID);
		diagram.put("isOpen", 0);
		diagram.put("name", diagramName);
		diagram.put("status", 1);

		diagram.put("diagramBgCss", "");
		diagram.put("diagramBgImg", "");

		String ci3dPoint = Contants.DMV_INIT_CI3DPOINT;
		String xml = Contants.DMV_INIT_XML;

		param.put("autoName", true);
		param.put("diagram", diagram);
		param.put("ci3dPoint", ci3dPoint);
		param.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
		param.put("xml", xml);
		String url = ":1511/tarsier-vmdb/dmv/diagram/saveOrUpdateDiagram";
		JSONObject resultObj = doFailRest(url, param.toString(), "POST", kw);
		return resultObj;
	}
	/**
	 * @param diagramName
	 * @param diagramDesc
	 * @param dirName
	 * @return
	 * 新建视图， 新建完成后更新画布
	 */
	public JSONObject createNewDiagram(String diagramName, String diagramDesc, String dirName){
		JSONObject param = new JSONObject();
		if (diagramName.isEmpty())
			diagramName = "";
		if (diagramDesc.isEmpty())
			diagramDesc = "";
		if (dirName.isEmpty())
			dirName = "0";
		DiagramUtil dUtil = new DiagramUtil();
		int dirID = dUtil.getDirIdByName(dirName);

		JSONObject diagram = new JSONObject();
		diagram.put("diagramDesc", diagramDesc);
		diagram.put("diagramType", 1);
		diagram.put("dirId", dirID);
		diagram.put("isOpen", 0);
		diagram.put("name", diagramName);
		diagram.put("status", 1);

		diagram.put("diagramBgCss", "");
		diagram.put("diagramBgImg", "");

		String ci3dPoint = Contants.DMV_INIT_CI3DPOINT;
		String xml = Contants.DMV_INIT_XML;

		param.put("autoName", true);
		param.put("diagram", diagram);
		param.put("ci3dPoint", ci3dPoint);
		String thumbnail=Contants.DMV_INIT_THUMBNAIL;
		param.put("thumbnail", thumbnail);
		param.put("xml", xml);
		String url = ":1511/tarsier-vmdb/dmv/diagram/saveOrUpdateDiagram";
		JSONObject resultObj = doRest(url, param.toString(), "POST");
		assertTrue(resultObj.getBigDecimal("data").compareTo(new BigDecimal(1))>0);
		BigDecimal diagramId = resultObj.getBigDecimal("data");
		//更新默认画布
		XmlUtil xmlUtil = new XmlUtil();
		param.put("xml", xmlUtil.buildDefaultXML());
		param.put("ci3dPoint", ci3dPoint);
		param.put("id",diagramId);
		param.put("diagramEles", new ArrayList());
		UpdateDiagramContent ud = new UpdateDiagramContent();
		ud.updateDiagramContent(param);
		return resultObj;
	}



	/**
	 * @param diagramName
	 * @param newDiagranName
	 * @param diagramDesc
	 * @param dirName
	 * @return
	 * 另存为
	 */
	public JSONObject saveAsDiagram(String diagramName, String newDiagranName,String diagramDesc, String dirName){
		if (diagramName.isEmpty()){
			return null;
		}
		QueryDiagramInfoById queryDiagramInfoById  = new QueryDiagramInfoById();
		JSONObject result = queryDiagramInfoById.queryDiagramInfoById(diagramName, true);
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
		UpdateDiagramContent ud = new UpdateDiagramContent();
		JSONObject resultObj = ud.updateDiagramContent(param);
		AssertResult as = new AssertResult();
		return as.assertRes(result.toString());
	}
	public JSONObject saveOrUpdateDiagram(JSONObject param){
		String url = ":1511/tarsier-vmdb/dmv/diagram/saveOrUpdateDiagram";
		return doRest(url, param.toString(), "POST");
	}

	/**
	 * @param fileName
	 * @return
	 * @throws DocumentException
	 * 导入Visio视图
	 */
	public JSONObject saveOrUpdateDiagramImportVisio(String fileName) throws DocumentException{
		Parser2Json p2j = new Parser2Json();
		JSONObject result = p2j.parser2Json(fileName);
		if (!result.has("success") || !result.getBoolean("success")){
			return null;
		}
		assertTrue(result.getBoolean("success"));

		JSONObject data = new JSONObject(result.getString("data"));
		JSONObject mapping = data.getJSONObject("mapping");
		JSONArray load = data.getJSONArray("load");
		JSONArray fileNameArray = new JSONArray();

		for (int i=0; i<load.length(); i++){
			String xml = Contants.DMV_INIT_XML;
			String ci3dPoint = Contants.DMV_INIT_CI3DPOINT;
			Document doc = DocumentHelper.parseText(xml); 
			Element graphElement = doc.getRootElement();      
			Element rootElement  = graphElement.element("root");

			JSONObject dataObj = load.getJSONObject(i);
			JSONArray framesArr =dataObj.getJSONArray("frames");
			JSONArray linesArr =dataObj.getJSONArray("lines");
			//处理点
			for (int f=0; f<framesArr.length(); f++){
				JSONObject frameObj = framesArr.getJSONObject(f);
				JSONArray textArr = frameObj.getJSONArray("text");
				String text = "";
				String fontSize="1";
				if (textArr!=null && textArr.length()>0 &&  textArr.getJSONObject(0)!=null){
					text = textArr.getJSONObject(0).getString("text");
					fontSize =  String.valueOf(textArr.getJSONObject(0).getInt("fontSize"));
				}

				String lineColor = "#000000";
				if (frameObj.has("lineColor"))
					lineColor = frameObj.getString("lineColor");
				int lineWeight = 1;
				if (frameObj.has("lineWeight"))
					lineWeight = frameObj.getInt("lineWeight");
				String style = "shape="+mapping.get(frameObj.getString("category"))+";whiteSpace=wrap;html=1;strokeColor="
						+lineColor+";strokeWidth="+lineWeight+";rotation="+String.valueOf(frameObj.getBigDecimal("rotation"))
						+";fillColor="+frameObj.getString("fillForegnd")+";fontSize="+fontSize+";align="+
						frameObj.getString("horizonAlign")+";verticalAlign="+frameObj.getString("verticalAlign")+";startArrow=none;endArrow=none";

				//增加mxCell
				Element mxEle = rootElement.addElement("mxCell");  
				mxEle.addAttribute("id", "prefix"+String.valueOf(frameObj.getInt("id")));
				mxEle.addAttribute("value", text);
				mxEle.addAttribute("style", style);
				mxEle.addAttribute("parent", "1");
				mxEle.addAttribute("vertex", "1");

				mxEle.addAttribute("utimgFullName", frameObj.getString("utimgFullName"));
				mxEle.addAttribute("utimgCode", frameObj.getString("utimgCode"));
				mxEle.addAttribute("originStyle", style);
				mxEle.addAttribute("severity", "-1");
				mxEle.addAttribute("needMarker", "0");
				mxEle.addAttribute("name", text);
				mxEle.addAttribute("originWidth", String.valueOf(frameObj.getInt("width")));  
				mxEle.addAttribute("originHeight", String.valueOf(frameObj.getInt("height")));

				Element mxGrometryEle = mxEle.addElement("mxGeometry"); 
				mxGrometryEle.addAttribute("x", String.valueOf(frameObj.getInt("x")));
				mxGrometryEle.addAttribute("y", String.valueOf(frameObj.getInt("y")));
				mxGrometryEle.addAttribute("width", String.valueOf(frameObj.getInt("width")));  
				mxGrometryEle.addAttribute("height", String.valueOf(frameObj.getInt("height")));
				mxGrometryEle.addAttribute("as", "geometry");

				Element objectEle = mxEle.addElement("Object"); 
				objectEle.addAttribute("x", String.valueOf(frameObj.getInt("x")));
				objectEle.addAttribute("y", String.valueOf(frameObj.getInt("y")));
				objectEle.addAttribute("width", String.valueOf(frameObj.getInt("width")));  
				objectEle.addAttribute("height", String.valueOf(frameObj.getInt("height")));
				objectEle.addAttribute("as", "originSize");
			}

			//处理线
			for (int l=0; l<linesArr.length(); l++){
				JSONObject lineObj = linesArr.getJSONObject(l);
				JSONArray textArr = lineObj.getJSONArray("text");
				String text = "";
				String fontSize="1";
				if (textArr!=null && textArr.length()>0 &&  textArr.getJSONObject(0)!=null){
					text = textArr.getJSONObject(0).getString("text");
					fontSize =  String.valueOf(textArr.getJSONObject(0).getInt("fontSize"));
				}

				String style = "whiteSpace=wrap;html=1;strokeColor="
						+lineObj.getString("lineColor")+";strokeWidth="+String.valueOf(lineObj.getInt("lineWeight"))
						+";startArrow=none;endArrow=none";

				//增加mxCell
				Element mxEle = rootElement.addElement("mxCell");  
				mxEle.addAttribute("id", "prefix"+String.valueOf(lineObj.getInt("id")));
				mxEle.addAttribute("value", text);
				mxEle.addAttribute("style", style);
				mxEle.addAttribute("parent", "1");

				if (lineObj.has("beginConnect"))
					mxEle.addAttribute("source", "prefix"+String.valueOf(lineObj.getInt("beginConnect")));
				if (lineObj.has("endConnect"))
					mxEle.addAttribute("target", "prefix"+String.valueOf(lineObj.getInt("endConnect")));
				mxEle.addAttribute("edge", "1");
				mxEle.addAttribute("originStyle", style);
				mxEle.addAttribute("severity", "-1");
				mxEle.addAttribute("needMarker", "0");


				Element mxGrometryEle = mxEle.addElement("mxGeometry"); 
				mxGrometryEle.addAttribute("relative", "1");
				mxGrometryEle.addAttribute("as", "geometry");
				Element arrayEle = mxEle.addElement("Array"); 
				arrayEle.addAttribute("as", "points");

				if (lineObj.has("points")){
					JSONArray pointArr = lineObj.getJSONArray("points");
					for (int p =0; p<pointArr.length(); p++){
						JSONArray xy = pointArr.getJSONObject(p).getJSONArray("xy");
						Element mxPoint = arrayEle.addElement("mxPoint");
						mxPoint.addAttribute("x", String.valueOf(xy.getInt(0)));
						mxPoint.addAttribute("y", String.valueOf(xy.getInt(1)));
					}
				}
			}	

			if (load.length()>1){
				fileName = fileName+String.valueOf(i+1);
			}
			xml = doc.getRootElement().asXML();
			JSONObject diagram = new JSONObject();
			diagram.put("dirId", 0);
			diagram.put("isOpen", 0);
			diagram.put("diagramDesc", "");
			diagram.put("status", 1);
			diagram.put("diagramType", 1);

			diagram.put("name", fileName);
			JSONObject param = new JSONObject();
			param.put("autoName", true);
			param.put("diagram", diagram);
			param.put("xml", xml);
			param.put("ci3dpoint", ci3dPoint);
			param.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
			this.saveOrUpdateDiagram(param);
			fileNameArray.put(fileName);
		}

		JSONObject resultAll = new JSONObject();
		resultAll.put("success", true);
		resultAll.put("code", -1);
		resultAll.put("data", fileNameArray);
		return resultAll;
	}

	/**
	 * @param fileName
	 * @return
	 * 导入xml视图
	 * @throws DocumentException 
	 */
	public JSONObject saveOrUpdateDiagramImportXml(String fileName) throws DocumentException{
		SAXReader reader = new SAXReader(); 
		String filePath = ImportExcel.class.getResource("/").getPath()+"testData/dmv/image/import/xml/"+fileName;
		Document document = reader.read(filePath);  
		Element graphElement = document.getRootElement(); // 获取根节点              
		Element rootElement  = graphElement.element("root");
		List<Element> eleList = rootElement.elements();
		JSONArray ciCodes = new JSONArray();
		JSONArray diagramEles = new JSONArray();
		CiUtil ciu = new CiUtil();
		for (int i =0; i<eleList.size(); i++){
			Element tempEle = eleList.get(i);
			if (tempEle.attribute("data-id")!=null && !tempEle.attributeValue("data-id").isEmpty()){
				String ciCode = tempEle.attributeValue("label");
				ciCodes.put(ciCode);
				JSONObject ele = new JSONObject();
				ele.put("eleType", 1);
				ele.put("eleId", ciu.getCiId(ciCode));
				//diagramEles.put(ele);
			}
		}

		JSONArray ciQ = new JSONArray();
		ciQ.put("ATTR");
		ciQ.put("CLASS");
		QueryList queryList = new QueryList();
		JSONObject ciResult = queryList.queryList(ciQ,ciCodes);

		String xml = document.getRootElement().asXML();
		JSONObject diagram = new JSONObject();
		diagram.put("dirId", 0);
		diagram.put("isOpen", 0);
		diagram.put("diagramDesc", "");
		diagram.put("status", 1);
		diagram.put("diagramType", 1);
		diagram.put("name", fileName);
		JSONObject param = new JSONObject();
		param.put("autoName", true);
		param.put("diagram", diagram);
		param.put("xml", xml);
		String ci3dpoint = Contants.DMV_INIT_CI3DPOINT;
		param.put("ci3dpoint", ci3dpoint);
		param.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
		param.put("diagramEles", diagramEles);
		return this.saveOrUpdateDiagram(param);
	}

	/**
	 * @param fileName
	 * @return
	 * 导入配置信息视图
	 * @throws DocumentException 
	 */
	public JSONObject saveOrUpdateDiagramImportConfig(String fileName) throws DocumentException{
		ImportDiagramConfigInfo importconfig = new ImportDiagramConfigInfo();
		JSONObject result = importconfig.importDiagramConfigInfo(fileName);
		if (!result.has("success") || !result.getBoolean("success")){
			return null;
		}
		assertTrue(result.getBoolean("success"));
		String xml = Contants.DMV_INIT_XML;
		String ci3dPoint = Contants.DMV_INIT_CI3DPOINT;
		JSONArray diagramEles = new JSONArray();

		XmlUtil xmlUtil = new XmlUtil();
		SvgUtil svgUtil = new SvgUtil();
		Ci3dPointUtil ci3dPointUtil = new Ci3dPointUtil();

		JSONArray data = result.getJSONArray("data");
		int x =100;
		for (int i=0; i<data.length(); i++){
			JSONObject dataObj = data.getJSONObject(i);
			JSONArray ciInfos = new JSONArray();
			if (dataObj.has("ciInfos"))
				ciInfos = dataObj.getJSONArray("ciInfos");
			JSONArray ciRlts = new JSONArray();
			if (dataObj.has("ciRlts"))
				ciRlts = dataObj.getJSONArray("ciRlts");
			//处理CI
			for (int ci=0; ci<ciInfos.length(); ci++){
				JSONObject ciInfoObj = ciInfos.getJSONObject(ci);
				JSONObject ciObj = ciInfoObj.getJSONObject("ci");
				JSONObject ele = new JSONObject();
				ele.put("eleType", 1);
				ele.put("eleId", ciObj.getBigDecimal("id"));
				diagramEles.put(ele);
				String ciId = String.valueOf(ciObj.getBigDecimal("id"));
				String ciCode =ciObj.getString("ciCode");

				String label = "";
				JSONObject fixMapping = ciInfoObj.getJSONObject("fixMapping");
				label = fixMapping.getString("nmCiCode");

				JSONObject ciClass = ciInfoObj.getJSONObject("ciClass");
				String icon = ciClass.getString("icon");


				List utdataLabelValObj =  new ArrayList();
				List ci3dPointLabelValObj =  new ArrayList();
				JSONArray attrsArr = ciInfoObj.getJSONArray("attrs");
				for (int j=0; j<attrsArr.length(); j++){
					JSONObject temp = (JSONObject) attrsArr.get(j);
					JSONObject m= new JSONObject();
					JSONObject ci3dm= new JSONObject();
					if (temp.has("value")){
						m.put("\"value\"", "\""+temp.get("value")+"\"");
						ci3dm.put("value",temp.get("value"));
					}
					m.put("\"key\"", "\""+temp.get("key")+"\"");
					m.put("\"$$hashkey\"", "\"object:3000\"");

					ci3dm.put("key", temp.get("key"));
					ci3dm.put("$$hashkey", "object:3000");
					utdataLabelValObj.add(m);
					ci3dPointLabelValObj.add(ci3dm);
				}

				//先组装svg 在组装xml ， 因为svg用到了该节点上一个节点的坐标
				//svg = svgUtil.addCiSvg(svg, xml, icon, ciCode,String.valueOf(x+ci*10), String.valueOf(x+ci*80));//增加CI
				xml = xmlUtil.addCI(xml,ciCode,ciId,icon,label, utdataLabelValObj, String.valueOf(x+ci*10), String.valueOf(x+ci*80));//增加CI
				ci3dPoint = ci3dPointUtil.ci3dPointAddNode(ci3dPoint,"ci",ciCode,ciId,icon,label, ci3dPointLabelValObj, String.valueOf(x+ci*10), String.valueOf(x+ci*80));
			}

			// 处理关系
			for (int rlt = 0; rlt < ciRlts.length(); rlt++) {
				JSONObject rltObj = ciRlts.getJSONObject(rlt);
				JSONArray ciRltClassInfos = new JSONArray();
				if (rlt == 0) {
					if (dataObj.has("ciRltClassInfos")) {
						ciRltClassInfos = dataObj.getJSONArray("ciRltClassInfos");
						String rltClsName = "";
						for (int rltCls = 0; rltCls < ciRltClassInfos.length(); rltCls++) {
							JSONObject ciClassTempObj = ciRltClassInfos.getJSONObject(rltCls);
							JSONObject ciClassObj = ciClassTempObj.getJSONObject("ciClass");

							if (rltObj.getBigDecimal("classId").compareTo(ciClassObj.getBigDecimal("id")) == 0) {
								rltClsName = ciClassObj.getString("className");
								break;
							}

						}
						xml = xmlUtil.addRlt(xml, rltClsName, String.valueOf(rltObj.getBigDecimal("id")),
								rltObj.getString("sourceCiCode"), rltObj.getString("targetCiCode"));
					}
				}
			}

		}

		JSONObject diagram = new JSONObject();
		diagram.put("dirId", 0);
		diagram.put("isOpen", 0);
		diagram.put("diagramDesc", "");
		diagram.put("status", 1);
		diagram.put("diagramType", 1);
		diagram.put("name", fileName);
		JSONObject param = new JSONObject();
		param.put("autoName", true);
		param.put("diagram", diagram);
		param.put("xml", xml);
		param.put("ci3dpoint", ci3dPoint);
		param.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
		param.put("diagramEles", diagramEles);
		return this.saveOrUpdateDiagram(param);
	}

	/**
	 * @param fileName
	 * @return
	 * @throws DocumentException
	 * 导入图片视图
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws InterruptedException 
	 */
	public JSONObject saveOrUpdateDiagramImportPicture(String fileName) throws DocumentException, ClientProtocolException, IOException, InterruptedException{
		GetAiProjectRoot getAiProjectRoot = new GetAiProjectRoot();
		JSONObject aiResult = getAiProjectRoot.getAiProjectRoot();
		String paraserUrl = aiResult.getString("data");
		String curImageDir = QaUtil.loadRest(paraserUrl+"/getCurImageDir", "", "POST");
		JSONObject curImageDirObj = new JSONObject(curImageDir);
		String dir = curImageDirObj.getString("data");

		//上传图片
		String filePath = this.getClass().getResource("/").getPath()+"testData/dmv/image/import/picture/"+fileName;
		JSONArray fileNames = new JSONArray();
		fileNames.put(fileName);
		Map<String,String>  uploadFileMap = new HashMap<String,String>();
		uploadFileMap.put(filePath, fileName);
		Map<String, String> textMap = new HashMap<String,String>();
		textMap.put("dir", curImageDirObj.getString("data"));
		textMap.put("fileNames", fileNames.toString());

		String uoloadImgresult = postImage(paraserUrl+"/uploadImages", fileName, new File(filePath), textMap);  
		String id = new JSONObject(uoloadImgresult).getJSONArray("data").getString(0);  
		JSONObject getFrameByImg = new JSONObject();
		getFrameByImg.put("id", id);
		String frameByImageResut = QaUtil.loadRest(paraserUrl+"/getFrameByImage", getFrameByImg.toString(), "POST");
		int width = 0;
		int height =0;
		JSONObject dataObj = (new JSONObject(frameByImageResut)).getJSONObject("data");
		width = dataObj.getInt("width");
		height = dataObj.getInt("height");

		String category = QaUtil.loadRest(paraserUrl+"/getCategoryByLabel", "", "POST");
		//模糊识别
		JSONObject detectObj = new JSONObject();
		detectObj.put("id", id);
		detectObj.put("frames", new JSONArray());
		String detectResulet= QaUtil.loadRest(paraserUrl+"/detect", detectObj.toString(), "POST");
		String serial = (new JSONObject(detectResulet)).getString("data");
		JSONObject taskObj = new JSONObject();
		taskObj.put("serial", serial);
		boolean detectFlag = true;
		JSONObject dataResult = new JSONObject();
		while(detectFlag){
			String detectTaskResulet= QaUtil.loadRest(paraserUrl+"/getDetectTask", taskObj.toString(), "POST");
			dataResult = (new JSONObject(detectTaskResulet)).getJSONObject("data");
			if (dataResult.getBoolean("fin")){
				detectFlag = false;
			}
			else{
				Thread.sleep(1000);
			}
		}
		JSONObject loadObj = dataResult.getJSONObject("load");
		JSONObject  saveImgFrameObj = new JSONObject();
		saveImgFrameObj.put("id", id);
		saveImgFrameObj.put("frames", loadObj.getJSONArray("frames"));
		saveImgFrameObj.put("lines", loadObj.getJSONArray("lines"));
		String saveImageFrameResulet= QaUtil.loadRest(paraserUrl+"/saveImageFrame", saveImgFrameObj.toString(), "POST");

		JSONObject paraseObj = new JSONObject();
		paraseObj.put("frames", loadObj.getJSONArray("frames"));
		paraseObj.put("lines", loadObj.getJSONArray("lines"));
		paraseObj.put("width", width);
		paraseObj.put("height", height);
		Parser parase = new Parser();
		JSONObject result = parase.parser(paraseObj);
		if (!result.has("success") || !result.getBoolean("success")){
			return null;
		}
		assertTrue(result.getBoolean("success"));

		String xml = result.getString("data");;
		String ci3dPoint = Contants.DMV_INIT_CI3DPOINT;

		JSONObject diagram = new JSONObject();
		diagram.put("dirId", 0);
		diagram.put("isOpen", 0);
		diagram.put("diagramDesc", "");
		diagram.put("status", 1);
		diagram.put("diagramType", 1);
		diagram.put("name", fileName);
		JSONObject param = new JSONObject();
		param.put("autoName", true);
		param.put("diagram", diagram);
		param.put("xml", xml);
		param.put("ci3dpoint", ci3dPoint);
		param.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
		return this.saveOrUpdateDiagram(param);
	}

	/**
	 * @param serverUrl
	 * @param fileParamName
	 * @param file
	 * @param params
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * httpClient4.2.2 上传图片 调用ai识别
	 */
	public  String postImage(String serverUrl, String fileParamName, File file, Map<String, String> params)  
			throws ClientProtocolException, IOException {  
		HttpPost httpPost = new HttpPost(serverUrl);  
		MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);  
		// 上传的文件  
		ContentBody cbFile= new FileBody(file, "image/jepg");
		reqEntity.addPart(fileParamName, cbFile);
		// 设置其他参数  
		for (Entry<String, String> entry : params.entrySet()) {  
			reqEntity.addPart(entry.getKey(), new StringBody(entry.getValue()));  
		}  

		httpPost.setEntity(reqEntity);
		HttpClient httpClient = new DefaultHttpClient();  
		HttpResponse response = httpClient.execute(httpPost);  
		BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-8"));
		String sResponse;
		StringBuffer s = new StringBuffer();
		while ((sResponse = reader.readLine())!=null){
			s = s.append(sResponse);
		}
		return s.toString();  
	}  
}
