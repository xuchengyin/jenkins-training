package com.uinnova.test.step_definitions.api.dmv.diagram;

import static org.junit.Assert.assertEquals;
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
import org.json.JSONException;
import org.json.JSONObject;

import com.uinnova.test.contant.Contants;
import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.api.base.ci.RemoveById;
import com.uinnova.test.step_definitions.api.dmv.ci.QueryById;
import com.uinnova.test.step_definitions.api.dmv.ci.QueryPageByIndex;
import com.uinnova.test.step_definitions.api.dmv.ciClass.QueryList;
import com.uinnova.test.step_definitions.api.dmv.ciRlt.QueryCiBetweenRlt;
import com.uinnova.test.step_definitions.api.dmv.ciRlt.QueryUpAndDownRlt;
import com.uinnova.test.step_definitions.api.dmv.ciRlt.QueryUpAndDownRltAndClass;
import com.uinnova.test.step_definitions.api.dmv.ciRlt.SaveOrUpdate;
import com.uinnova.test.step_definitions.testcase.base.rlt.ciRlt.Scenario_ciRlt;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.base.CiUtil;
import com.uinnova.test.step_definitions.utils.base.SyncToNoahUtil;
import com.uinnova.test.step_definitions.utils.dmv.Ci3dPointUtil;
import com.uinnova.test.step_definitions.utils.dmv.DiagramUtil;
import com.uinnova.test.step_definitions.utils.dmv.SvgUtil;
import com.uinnova.test.step_definitions.utils.dmv.XmlUtil;

import cucumber.api.DataTable;

/**
 * 编写时间:2017-11-17
 * 编写人:sunsl
 * 功能介绍:更新视图类
 */
public class UpdateDiagramContent extends RestApi{
	public JSONObject updateDiagramContent(String diagramName,JSONObject searchResult,JSONArray diagramEles){
		String url= ":1511/tarsier-vmdb/dmv/diagram/updateDiagramContent";
		JSONObject param = new JSONObject();
		DiagramUtil diagramUtil = new DiagramUtil();
		String xml = searchResult.getJSONObject("data").getString("xml");
		String ci3dPoint = searchResult.getJSONObject("data").getString("ci3dPoint");
		JSONObject diagram = searchResult.getJSONObject("data").getJSONObject("diagram");
		param.put("ci3dPoint", ci3dPoint);
		param.put("diagram", diagram);
		param.put("id", String.valueOf(diagramUtil.getDiagramIdByName(diagramName)));
		param.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
		param.put("xml", xml);
		param.put("diagramEles", diagramEles);
		return doRest(url, param.toString(), "POST");
	}

	/**
	 * @param param
	 * @return
	 * wsl  
	 */
	public JSONObject updateDiagramContent(JSONObject param){
		String url= ":1511/tarsier-vmdb/dmv/diagram/updateDiagramContent";
		return doRest(url, param.toString(), "POST");
	}


	/**
	 * @param diagramName
	 * @param clsName
	 * @param ciCode
	 * @return
	 * 给画布增加CI
	 * @throws DocumentException 
	 */
	public JSONObject updateDiagramContentAddCI(String diagramName, String ciCode, String x, String y) throws DocumentException {
		JSONObject oldParam = getDiagramInfo(diagramName);
		String xml = oldParam.getString("xml");
		String  ci3dPoint= oldParam.getString("ci3dPoint");
		XmlUtil xmlUtil = new XmlUtil();
		SvgUtil svgUtil = new SvgUtil();
		//组装xml
		String clsName = "";
		String icon = "";
		CiClassUtil ciClsUtil = new CiClassUtil();
		clsName = ciClsUtil.getCiClassNameByCiCode(ciCode);
		QueryPageByIndex queryPageByIndex = new QueryPageByIndex();
		JSONObject CIPageByIndex = queryPageByIndex.queryCIPageByIndex(clsName, 1, 10000);
		JSONArray data = CIPageByIndex.getJSONObject("data").getJSONArray("data");
		//assertTrue(data.length()>0);
		if (data!=null){
			for (int i=0; i<data.length(); i++){
				JSONObject ciAllObj = data.getJSONObject(i);
				JSONObject ciObj =  ciAllObj.getJSONObject("ci");
				if (ciObj.getString("ciCode").compareToIgnoreCase(ciCode)==0){
					String ciId = String.valueOf(ciObj.getBigDecimal("id"));
					icon = getClsIconByCiCode(ciCode);
					String label = "";
					JSONArray attrDefs = ciAllObj.getJSONArray("attrDefs");
					for (int j=0; j<attrDefs.length(); j++){
						JSONObject temp = (JSONObject) attrDefs.get(j);
						if (temp.getInt("isMajor")==1){
							label = temp.getString("proName");
						}
					}

					List utdataLabelValObj =  new ArrayList();
					List ci3dPointLabelValObj =  new ArrayList();
					JSONArray attrsArr = ciAllObj.getJSONArray("attrs");
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
					//svg = svgUtil.addCiSvg(svg, xml, icon, ciCode, x, y);//增加CI
					xml = xmlUtil.addCI(xml,ciCode,ciId,icon,label, utdataLabelValObj, x, y);//增加CI
					Ci3dPointUtil ci3dPointUtil = new Ci3dPointUtil();
					ci3dPoint = ci3dPointUtil.ci3dPointAddNode(ci3dPoint,"ci",ciCode,ciId,icon,label, ci3dPointLabelValObj, x, y);
					break;
				}
			}
		}

		JSONArray diagramEles = new JSONArray();
		if(oldParam.has("diagramEles")){
			diagramEles = (JSONArray) oldParam.get("diagramEles");
		}
		CiUtil ciUtil = new CiUtil();
		BigDecimal ciId = ciUtil.getCiId(ciCode);
		JSONObject diagramEle = new JSONObject();
		diagramEle.put("eleType", 1);
		diagramEle.put("eleId", ciId);
		diagramEles.put(diagramEle);
		oldParam.put("diagramEles", diagramEles);

		//查看关系
		QueryCiBetweenRlt queryCiBetweenRlt = new QueryCiBetweenRlt();
		String ciCodeIds = "";
		for (int i=0; i<diagramEles.length(); i++){
			JSONObject tempEle = diagramEles.getJSONObject(i);
			if (ciCodeIds.isEmpty())
				ciCodeIds=String.valueOf(tempEle.getBigDecimal("eleId"));
			else
				ciCodeIds+=","+ String.valueOf(tempEle.getBigDecimal("eleId"));
		}
		JSONObject rltObjs = queryCiBetweenRlt.queryMultiCiBetweenRlt(ciCodeIds.split(","));
		JSONArray rltDataArr = rltObjs.getJSONArray("data");

		for (int i=0; i<rltDataArr.length(); i++){
			JSONObject ciRlts = (JSONObject) rltDataArr.get(i);
			JSONObject tempObj = ciRlts.getJSONObject("ciRlt");
			//增加关系
			String rltClsName = ciClsUtil.getClsNameByClsId(tempObj.getBigDecimal("classId"));
			xml = xmlUtil.addRlt(xml, rltClsName, String.valueOf(tempObj.getBigDecimal("id")), tempObj.getString("sourceCiCode"), tempObj.getString("targetCiCode"));
		}

		oldParam.put("xml", xml);
		oldParam.put("ci3dPoint", ci3dPoint);
		oldParam.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
		return updateDiagramContent(oldParam);
	}
	/**
	 * @param diagramName
	 * @param ciCode
	 * @param x
	 * @param y
	 * @return
	 * @throws DocumentException
	 * 增加图标
	 */
	public JSONObject updateDiagramContentAddImg(String diagramName, String imgName, String x, String y) throws DocumentException {
		JSONObject oldParam = getDiagramInfo(diagramName);
		String xml = oldParam.getString("xml");
		XmlUtil xmlUtil = new XmlUtil();
		//组装xml
		xml = xmlUtil.addImgUserObject(xml,imgName, x, y);//增加一个图标
		oldParam.put("xml", xml);
		oldParam.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
		return updateDiagramContent(oldParam);
	}

	/**
	 * @param diagramName
	 * @param ciCode
	 * @param x
	 * @param y
	 * @return
	 * @throws DocumentException
	 * 增加容器
	 */
	public JSONObject updateDiagramContentAddContainer(String diagramName, String x, String y) throws DocumentException {
		JSONObject oldParam = getDiagramInfo(diagramName);
		String xml = oldParam.getString("xml");
		XmlUtil xmlUtil = new XmlUtil();
		//组装xml
		xml = xmlUtil.addContainerUserObject(xml, x, y);//增加一个图标
		oldParam.put("xml", xml);
		oldParam.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
		return updateDiagramContent(oldParam);
	}

	/**
	 * @param diagramName
	 * @param x
	 * @param y
	 * @return
	 * @throws DocumentException
	 */
	public JSONObject updateDiagramContentContainerAddDefAndRule(String diagramName, String x, String y, String className,  DataTable table) throws DocumentException {
		//String keyName, String symbol, String keyValue
		JSONObject oldParam = getDiagramInfo(diagramName);
		String xml = oldParam.getString("xml");

		JSONObject utruleCondition = new JSONObject();
		CiClassUtil ciclsU = new CiClassUtil();
		utruleCondition.put("classId", ciclsU.getCiClassId(className));
		JSONArray attrDefs = new JSONArray();

		QueryPageByIndex queryPageByIndex = new QueryPageByIndex();
		JSONObject CIPageByIndex = queryPageByIndex.queryCIPageByIndex(className, 1, 10000);
		JSONArray data = CIPageByIndex.getJSONObject("data").getJSONArray("data");
		if (data!=null){
			int rows = table.raw().size();
			for(int i=1;i<rows;i++){
				List<String> row = table.raw().get(i);
				JSONObject ciAllObj = data.getJSONObject(0);
				JSONArray attrDefsArr =  ciAllObj.getJSONArray("attrDefs");
				for (int arr=0; arr<attrDefsArr.length(); arr++){
					JSONObject attrTempObj = attrDefsArr.getJSONObject(arr);
					String keyName = attrTempObj.getString("proName");
					if (row.get(1).compareToIgnoreCase(keyName)==0){
						JSONObject attrObj = new JSONObject();
						attrObj.put("id", attrTempObj.getBigDecimal("id"));
						attrObj.put("keyName", keyName);
						attrObj.put("proType", attrTempObj.getInt("proType"));
						attrObj.put("keyValue", row.get(3));
						attrObj.put("symbol", row.get(2));
						attrDefs.put(attrObj);
						break;
					}
				}

			}
		}

		utruleCondition.put("attrDefs",attrDefs);
		XmlUtil xmlUtil = new XmlUtil();
		//组装xml
		xml = xmlUtil.containerAddRuleCondition(xml, x, y, utruleCondition);//增加一个图标
		oldParam.put("xml", xml);
		oldParam.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
		return updateDiagramContent(oldParam);
	}


	/**
	 * @param diagramName
	 * @param imgName
	 * @param x
	 * @param y
	 * @return
	 * @throws DocumentException
	 * 给图标创建CI
	 */
	public JSONObject updateDiagramContentImgCreateCI(String diagramName, String imgName, String x, String y) throws DocumentException {
		JSONObject oldParam = getDiagramInfo(diagramName);
		QueryList queryList = new QueryList();
		JSONObject clsObjs = queryList.queryList();
		JSONArray clsObjArr = clsObjs.getJSONArray("data");
		JSONObject clsObj = clsObjArr.getJSONObject(0);
		BigDecimal ciClassId = clsObj.getJSONObject("ciClass").getBigDecimal("id");
		JSONObject attrs = new JSONObject();
		String ciCode = "NewCI"+System.currentTimeMillis();
		String label = clsObj.getJSONObject("fixMapping").getString("nmCiCode");
		attrs.put(label, ciCode);
		JSONObject ciResult = (new com.uinnova.test.step_definitions.api.dmv.ci.SaveOrUpdate()).saveOrUpdateSingCi(ciClassId, attrs);
		String ciId = ciResult.getString("data");
		QueryById QueryById = new QueryById();
		JSONObject newCIInfo = QueryById.queryById(ciCode).getJSONObject("data");
		//组装xml
		String xml = oldParam.getString("xml");
		XmlUtil xmlUtil = new XmlUtil();

		List utdataLabelValObj =  new ArrayList();
		JSONArray attrsArr = newCIInfo.getJSONArray("attrs");
		for (int j=0; j<attrsArr.length(); j++){
			JSONObject temp = (JSONObject) attrsArr.get(j);
			JSONObject m= new JSONObject();
			if (temp.has("value")){
				m.put("\"value\"", "\""+temp.get("value")+"\"");
			}
			m.put("\"key\"", "\""+temp.get("key")+"\"");
			utdataLabelValObj.add(m);
		}

		xml = xmlUtil.imgCreateCI(xml,imgName, ciCode, ciId, label, utdataLabelValObj, x, y);
		oldParam.put("xml", xml);

		JSONArray diagramEles = new JSONArray();
		if(oldParam.has("diagramEles")){
			diagramEles = (JSONArray) oldParam.get("diagramEles");
		}
		JSONObject diagramEle = new JSONObject();
		diagramEle.put("eleType", 1);
		diagramEle.put("eleId", ciId);
		diagramEles.put(diagramEle);
		oldParam.put("diagramEles", diagramEles);
		oldParam.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
		return updateDiagramContent(oldParam);
	}


	/**
	 * @param diagramName
	 * @param imgName
	 * @param x
	 * @param y
	 * @return
	 * @throws DocumentException
	 */
	public JSONObject updateDiagramContentImgRemoveCI(String diagramName, String imgName, String x, String y) throws DocumentException {
		JSONObject oldParam = getDiagramInfo(diagramName);
		BigDecimal ciId =new BigDecimal(0);
		String xml = oldParam.getString("xml");
		XmlUtil xmlUtil = new XmlUtil();
		List utdataLabelValObj =  new ArrayList();
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
						String dataid = userObjectEle.attributeValue("data-id");
						ciId = new BigDecimal(dataid.substring(3));
						break;
					}
				}
			}
		}

		xml = xmlUtil.imgRemoveCI(xml,imgName, x, y);
		oldParam.put("xml", xml);

		//还原现场， 删除CI
		RemoveById removeById = new RemoveById();
		removeById.removeById(ciId);

		JSONArray diagramEles = (JSONArray) oldParam.get("diagramEles");
		for (int j=0; j<diagramEles.length(); j++){
			JSONObject diagramEle = diagramEles.getJSONObject(j);
			if (diagramEle.getBigDecimal("eleId").compareTo(ciId)==0){
				diagramEles.remove(j);
				break;
			}
		}
		oldParam.put("diagramEles", diagramEles);
		oldParam.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
		return updateDiagramContent(oldParam);
	}

	/**
	 * @param diagramName
	 * @param imgName
	 * @param x
	 * @param y
	 * @return
	 * @throws DocumentException
	 * 删除一个图标
	 */
	public JSONObject updateDiagramContentRemoveImg(String diagramName, String imgName, String x, String y) throws DocumentException {
		JSONObject oldParam = getDiagramInfo(diagramName);
		String xml = oldParam.getString("xml");
		XmlUtil xmlUtil = new XmlUtil();
		//组装xml
		xml = xmlUtil.removeImgUserObject(xml,imgName, x, y);//增加一个图标
		oldParam.put("xml", xml);
		oldParam.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
		return updateDiagramContent(oldParam);
	}

	/**
	 * @param diagramName
	 * @param rltClassName
	 * @param sourceCiCode
	 * @param targetCiCode
	 * @return
	 * @throws DocumentException
	 * CI之间增加连线
	 */
	public JSONObject updateDiagramContentAddCILine(String diagramName, String sourceCiCode, String targetCiCode) throws DocumentException {
		JSONObject oldParam = getDiagramInfo(diagramName);
		String xml = oldParam.getString("xml");
		//String thumbnail = oldParam.getString("thumbnail");
		String  ci3dPoint= oldParam.getString("ci3dPoint");
		XmlUtil xmlUtil = new XmlUtil();
		SvgUtil svgUtil = new SvgUtil();
		//组装xml
		xml = xmlUtil.addLine(xml, sourceCiCode, targetCiCode);//增加CI
		oldParam.put("xml", xml);
		oldParam.put("ci3dPoint", ci3dPoint);

		oldParam.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
		return updateDiagramContent(oldParam);
	}

	/**
	 * @param diagramName
	 * @param sourceCiCode
	 * @param targetCiCode
	 * @return
	 * @throws DocumentException
	 * 连线箭头反向
	 */
	public JSONObject updateDiagramContentLineReverse(String diagramName, String sourceCiCode, String targetCiCode) throws DocumentException {
		JSONObject oldParam = getDiagramInfo(diagramName);
		String xml = oldParam.getString("xml");
		//String thumbnail = oldParam.getString("thumbnail");
		String  ci3dPoint= oldParam.getString("ci3dPoint");
		XmlUtil xmlUtil = new XmlUtil();
		SvgUtil svgUtil = new SvgUtil();
		//组装xml
		xml = xmlUtil.updateLineReverse(xml, sourceCiCode, targetCiCode);//增加CI
		oldParam.put("xml", xml);
		oldParam.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
		return updateDiagramContent(oldParam);
	}

	/**
	 * @param diagramName
	 * @param sourceCiCode
	 * @param targetCiCode
	 * @return
	 * @throws DocumentException
	 * 删除连线
	 */
	public JSONObject updateDiagramContentRemoveCILine(String diagramName, String sourceCiCode, String targetCiCode) throws DocumentException {
		JSONObject oldParam = getDiagramInfo(diagramName);
		String xml = oldParam.getString("xml");
		String  ci3dPoint= oldParam.getString("ci3dPoint");
		XmlUtil xmlUtil = new XmlUtil();
		SvgUtil svgUtil = new SvgUtil();
		//组装xml
		xml = xmlUtil.delLine(xml, sourceCiCode, targetCiCode);//删除CI
		oldParam.put("xml", xml);
		oldParam.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
		return updateDiagramContent(oldParam);
	}

	/**
	 * @param diagramName
	 * @param clsName
	 * @param ciCode
	 * @return
	 * 给画布增加CI
	 * @throws DocumentException 
	 */
	public JSONObject updateDiagramContentAddRlt(String diagramName, String rltClassName, String sourceCiCode, String targetCiCode) throws DocumentException {
		JSONObject oldParam = getDiagramInfo(diagramName);
		String xml = oldParam.getString("xml");
		String  ci3dPoint= oldParam.getString("ci3dPoint");
		XmlUtil xmlUtil = new XmlUtil();
		SvgUtil svgUtil = new SvgUtil();

		/*	//增加关系
			String rltClsName = ciClsUtil.getClsNameByClsId(tempObj.getBigDecimal("classId"));
			xml = xmlUtil.addRlt(xml, rltClsName, String.valueOf(tempObj.getBigDecimal("id")), tempObj.getString("sourceCiCode"), tempObj.getString("targetCiCode"));
		}*/

		oldParam.put("xml", xml);
		oldParam.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
		return updateDiagramContent(oldParam);
	}


	/**
	 * @param diagramName
	 * @param clsName
	 * @param x
	 * @param pageSize
	 * @return
	 * @throws DocumentException
	 * 给画布增加一个分类的多个cI
	 */
	public JSONObject updateDiagramContentAddAllClsCI(String diagramName, String clsName, int x , int pageSize) throws DocumentException {
		JSONObject oldParam = getDiagramInfo(diagramName);
		String xml = oldParam.getString("xml");
		String  ci3dPoint= oldParam.getString("ci3dPoint");
		JSONArray diagramEles = new JSONArray();
		if(oldParam.has("diagramEles")){
			diagramEles = (JSONArray) oldParam.get("diagramEles");
		}
		XmlUtil xmlUtil = new XmlUtil();
		SvgUtil svgUtil = new SvgUtil();
		//组装xml
		String icon = "";
		QueryPageByIndex queryPageByIndex = new QueryPageByIndex();
		JSONObject CIPageByIndex = queryPageByIndex.queryCIPageByIndex(clsName, 1, pageSize);
		JSONArray data = CIPageByIndex.getJSONObject("data").getJSONArray("data");
		//assertTrue(data.length()>0);
		if (data!=null){
			for (int i=0; i<data.length(); i++){
				JSONObject ciAllObj = data.getJSONObject(i);
				JSONObject ciObj =  ciAllObj.getJSONObject("ci");
				String ciCode = ciObj.getString("ciCode");
				String ciId = String.valueOf(ciObj.getBigDecimal("id"));
				if (icon.isEmpty()){
					icon = getClsIconByCiCode(ciCode);
				}
				String label = "";
				JSONArray attrDefs = ciAllObj.getJSONArray("attrDefs");
				for (int j=0; j<attrDefs.length(); j++){
					JSONObject temp = (JSONObject) attrDefs.get(j);
					if (temp.getInt("isMajor")==1){
						label = temp.getString("proName");
					}
				}

				List utdataLabelValObj =  new ArrayList();
				List ci3dPointLabelValObj =  new ArrayList();
				JSONArray attrsArr = ciAllObj.getJSONArray("attrs");
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
				//thumbnail = svgUtil.addCiSvg(thumbnail, xml, icon, ciCode, String.valueOf(x), String.valueOf(x+i*80));//增加CI
				xml = xmlUtil.addCI(xml,ciCode,ciId,icon,label, utdataLabelValObj, String.valueOf(x), String.valueOf(x+i*80));//增加CI
				Ci3dPointUtil ci3dPointUtil = new Ci3dPointUtil();
				ci3dPoint = ci3dPointUtil.ci3dPointAddNode(ci3dPoint,"ci",ciCode,ciId,icon,label, ci3dPointLabelValObj, String.valueOf(x), String.valueOf(x+i*80));
				JSONObject diagramEle = new JSONObject();
				diagramEle.put("eleType", 1);
				diagramEle.put("eleId",new BigDecimal(ciId));
				diagramEles.put(diagramEle);
			}
		}

		oldParam.put("diagramEles", diagramEles);

		//查看关系
		QueryCiBetweenRlt queryCiBetweenRlt = new QueryCiBetweenRlt();
		String ciCodeIds = "";
		for (int i=0; i<diagramEles.length(); i++){
			JSONObject tempEle = diagramEles.getJSONObject(i);
			if (ciCodeIds.isEmpty())
				ciCodeIds=String.valueOf(tempEle.getBigDecimal("eleId"));
			else
				ciCodeIds+=","+ String.valueOf(tempEle.getBigDecimal("eleId"));
		}
		JSONObject rltObjs = queryCiBetweenRlt.queryMultiCiBetweenRlt(ciCodeIds.split(","));
		JSONArray rltDataArr = rltObjs.getJSONArray("data");

		CiClassUtil ciClsUtil = new CiClassUtil();
		for (int i=0; i<rltDataArr.length(); i++){
			JSONObject ciRlts = (JSONObject) rltDataArr.get(i);
			JSONObject tempObj = ciRlts.getJSONObject("ciRlt");
			//增加关系
			String rltClsName = ciClsUtil.getClsNameByClsId(tempObj.getBigDecimal("classId"));
			xml = xmlUtil.addRlt(xml, rltClsName, String.valueOf(tempObj.getBigDecimal("id")), tempObj.getString("sourceCiCode"), tempObj.getString("targetCiCode"));
		}
		oldParam.put("xml", xml);
		oldParam.put("ci3dPoint", ci3dPoint);

		oldParam.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
		return updateDiagramContent(oldParam);
	}

	/**
	 * @param diagramName
	 * @param ciCode
	 * @return
	 * 画布删除CI
	 * @throws DocumentException 
	 */
	public JSONObject updateDiagramContentRemoveCI(String diagramName, String ciCode) throws DocumentException{
		JSONObject oldParam = getDiagramInfo(diagramName);
		//组装xml
		String xml = oldParam.getString("xml");
		XmlUtil xmlUtil = new XmlUtil();
		xml = xmlUtil.removeUserObject(xml, ciCode);//增加CI
		oldParam.put("xml", xml);
		//ci3dPoint
		String  ci3dPoint= oldParam.getString("ci3dPoint");
		JSONObject ci3dPointObj = new JSONObject(ci3dPoint);
		JSONArray nodes = ci3dPointObj.getJSONArray("nodes");
		for (int i=0; i<nodes.length(); i++){
			JSONObject tempObj = nodes.getJSONObject(i);
			if (tempObj.has("code"))
			{
				if (ciCode.compareToIgnoreCase(tempObj.getString("code"))==0){
					nodes.remove(i);
					break;
				}
			}

		}
		ci3dPointObj.put("nodes", nodes);
		oldParam.put("ci3dPoint", ci3dPointObj.toString());

		//svg 待补充

		CiUtil ciUtil = new CiUtil();
		BigDecimal ciId = ciUtil.getCiId(ciCode);
		JSONArray diagramEles = new JSONArray();
		if(oldParam.has("diagramEles")){
			diagramEles = (JSONArray) oldParam.get("diagramEles");
		}
		for (int i=0; i<diagramEles.length(); i++){
			JSONObject tmp = (JSONObject) diagramEles.get(i);
			if (tmp.getBigDecimal("eleId").compareTo(ciId)==0){
				diagramEles.remove(i);
				break;
			}
		}
		oldParam.put("diagramEles", diagramEles);
		oldParam.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
		return updateDiagramContent(oldParam);
	}

	/**
	 * @param diagramName
	 * @param clsName
	 * @param ciCode
	 * @return
	 * 右键CI添加至容器
	 * @throws DocumentException 
	 */
	public JSONObject updateDiagramContentAddCIToContainer(String diagramName, String ciCode) throws DocumentException {
		JSONObject oldParam = getDiagramInfo(diagramName);
		String xml = oldParam.getString("xml");
		//组装xml
		XmlUtil xmlUtil = new XmlUtil();
		xml = xmlUtil.ciAddContainer(xml, ciCode);//增加CI
		oldParam.put("xml", xml);

		String ci3dPoint=oldParam.getString("ci3dPoint");
		Ci3dPointUtil ci3dPointUtil = new Ci3dPointUtil();
		ci3dPoint = ci3dPointUtil.ci3dPointAddContainer(ci3dPoint, ciCode);
		oldParam.put("ci3dPoint", ci3dPoint);
		oldParam.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
		//需要自己组装
		return updateDiagramContent(oldParam);
	}


	/**
	 * @param diagramName
	 * @param ciCode
	 * @param dirllDiagramName
	 * @return
	 * @throws DocumentException
	 * 给ci增加钻取视图
	 */
	public JSONObject updateDiagramContentCIAddDrillDown(String diagramName, String ciCode, String dirllDiagramName) throws DocumentException {
		JSONObject oldParam = getDiagramInfo(diagramName);
		String xml = oldParam.getString("xml");
		//组装xml
		XmlUtil xmlUtil = new XmlUtil();
		xml = xmlUtil.ciAddDrillDown(xml, ciCode, dirllDiagramName);//给cI添加钻取视图
		oldParam.put("xml", xml);
		String ci3dPoint=oldParam.getString("ci3dPoint");
		Ci3dPointUtil ci3dPointUtil = new Ci3dPointUtil();
		ci3dPoint = ci3dPointUtil.ci3dPointCIAddDrillDown(ci3dPoint, ciCode, dirllDiagramName);
		oldParam.put("ci3dPoint", ci3dPoint);
		oldParam.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
		//SVG
		return updateDiagramContent(oldParam);
	}

	/**
	 * @param diagramName
	 * @param ciCode
	 * @param dirllDiagramName
	 * @return
	 * @throws DocumentException
	 * 给连线增加钻取视图
	 */
	public JSONObject updateDiagramContentLineAddDrillDown(String diagramName, String sourceCiCode, String targetCiCode, String dirllDiagramName) throws DocumentException {
		JSONObject oldParam = getDiagramInfo(diagramName);
		String xml = oldParam.getString("xml");
		//组装xml
		XmlUtil xmlUtil = new XmlUtil();
		xml = xmlUtil.lineAddDrillDown(xml, sourceCiCode, targetCiCode, dirllDiagramName);//给cI添加钻取视图
		oldParam.put("xml", xml);
		String ci3dPoint=oldParam.getString("ci3dPoint");
		Ci3dPointUtil ci3dPointUtil = new Ci3dPointUtil();
		oldParam.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
		return updateDiagramContent(oldParam);
	}

	/**
	 * @param diagramName
	 * @param ciCode
	 * @param dirllDiagramName
	 * @return
	 * @throws DocumentException
	 * 关系增加钻取视图
	 */
	public JSONObject updateDiagramContentRltAddDrillDown(String diagramName, String sourceCiCode, String targetCiCode, String rltClassName, String dirllDiagramName) throws DocumentException {
		JSONObject oldParam = getDiagramInfo(diagramName);
		String xml = oldParam.getString("xml");
		//组装xml
		XmlUtil xmlUtil = new XmlUtil();
		xml = xmlUtil.rltAddDrillDown(xml, sourceCiCode,targetCiCode,  rltClassName, dirllDiagramName);//给cI添加钻取视图
		oldParam.put("xml", xml);
		oldParam.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
		return updateDiagramContent(oldParam);
	}


	/**
	 * @param diagramName
	 * @param sourceCiCode
	 * @param targetCiCode
	 * @param rltCls
	 * @return
	 * @throws DocumentException
	 * 给连线创建关系
	 */
	public JSONObject updateDiagramContentLineCreateRlt(String diagramName, String sourceCiCode, String targetCiCode, String rltCls) throws DocumentException {
		JSONObject oldParam = getDiagramInfo(diagramName);
		String xml = oldParam.getString("xml");
		SaveOrUpdate rltSu = new SaveOrUpdate();
		JSONObject rltResult = rltSu.saveOrUpdate(rltCls, sourceCiCode, targetCiCode);
		(new Scenario_ciRlt()).ciRltIdList.add(rltResult.getBigDecimal("data"));//@After使用删除关系创建的关系
		//以下代码确认关系数据同步到noah
		assertTrue((new SyncToNoahUtil()).syncCiRltToNoah(rltCls, sourceCiCode, targetCiCode));

		String rltId= rltResult.getString("data");
		//组装xml
		XmlUtil xmlUtil = new XmlUtil();
		xml = xmlUtil.lineCreateRlt(xml, sourceCiCode, targetCiCode, rltCls, rltId);//给cI添加钻取视图
		oldParam.put("xml", xml);
		oldParam.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
		return updateDiagramContent(oldParam);
	}


	/**
	 * @param diagramName
	 * @param sourceCiCode
	 * @param targetCiCode
	 * @param rltCls
	 * @return
	 * @throws DocumentException
	 * 关系右键创建关系
	 */
	public JSONObject updateDiagramContentRltCreateRlt(String diagramName, String sourceCiCode, String targetCiCode, String rltCls) throws DocumentException {
		JSONObject oldParam = getDiagramInfo(diagramName);
		String xml = oldParam.getString("xml");
		SaveOrUpdate rltSu = new SaveOrUpdate();
		JSONObject rltResult = rltSu.saveOrUpdate(rltCls, sourceCiCode, targetCiCode);
		(new Scenario_ciRlt()).ciRltIdList.add(rltResult.getBigDecimal("data"));//@After使用删除关系创建的关系
		//以下代码确认关系数据同步到noah
		assertTrue((new SyncToNoahUtil()).syncCiRltToNoah(rltCls, sourceCiCode, targetCiCode));

		String rltId= rltResult.getString("data");
		//组装xml
		XmlUtil xmlUtil = new XmlUtil();
		xml = xmlUtil.addRlt(xml, rltCls, rltId, sourceCiCode, targetCiCode);//给cI添加钻取视图
		oldParam.put("xml", xml);
		oldParam.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
		return updateDiagramContent(oldParam);
	}


	public JSONObject updateDiagramContentRemoveRlt(String diagramName, String sourceCiCode, String targetCiCode, String rltCls) throws DocumentException {
		JSONObject oldParam = getDiagramInfo(diagramName);
		String xml = oldParam.getString("xml");
		//组装xml
		XmlUtil xmlUtil = new XmlUtil();
		xml = xmlUtil.removeRlt(xml, sourceCiCode, targetCiCode, rltCls);//给cI添加钻取视图
		oldParam.put("xml", xml);
		oldParam.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
		return updateDiagramContent(oldParam);
	}


	/**
	 * @param diagramName
	 * @param sourceCiCode
	 * @param targetCiCode
	 * @param rltCls
	 * @return
	 * @throws DocumentException
	 * 隐藏关系
	 */
	public JSONObject updateDiagramContentHideOrDisplayRlt(String diagramName, String sourceCiCode, String targetCiCode, String rltCls, String diaplayFlag) throws DocumentException {
		JSONObject oldParam = getDiagramInfo(diagramName);
		String xml = oldParam.getString("xml");
		//组装xml
		XmlUtil xmlUtil = new XmlUtil();
		xml = xmlUtil.hideORDisplayRlt(xml, sourceCiCode, targetCiCode, rltCls, diaplayFlag);//给cI添加钻取视图
		oldParam.put("xml", xml);
		oldParam.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
		return updateDiagramContent(oldParam);
	}

	/**
	 * @param diagramName
	 * @param ciCode
	 * @param dirllDiagramName
	 * @return
	 * @throws DocumentException
	 * 给ci删除钻取视图
	 */
	public JSONObject updateDiagramContentCIRemoveDrillDown(String diagramName, String ciCode, String dirllDiagramName) throws DocumentException {
		JSONObject oldParam = getDiagramInfo(diagramName);
		String xml = oldParam.getString("xml");
		//组装xml
		XmlUtil xmlUtil = new XmlUtil();
		xml = xmlUtil.ciRemoveDrillDown(xml, ciCode, dirllDiagramName);//给cI添加钻取视图
		oldParam.put("xml", xml);
		String ci3dPoint=oldParam.getString("ci3dPoint");
		Ci3dPointUtil ci3dPointUtil = new Ci3dPointUtil();
		ci3dPoint = ci3dPointUtil.ci3dPointCIRemoveDrillDown(ci3dPoint, ciCode, dirllDiagramName);
		oldParam.put("ci3dPoint", ci3dPoint);
		oldParam.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
		return updateDiagramContent(oldParam);
	}


	/**
	 * @param diagramName
	 * @param ciCode
	 * @param dirllDiagramName
	 * @return
	 * @throws DocumentException
	 * 给连线删除钻取视图
	 */
	public JSONObject updateDiagramContentLineRemoveDrillDown(String diagramName, String sourceCiCode, String targetCiCode, String dirllDiagramName) throws DocumentException {
		JSONObject oldParam = getDiagramInfo(diagramName);
		String xml = oldParam.getString("xml");
		//组装xml
		XmlUtil xmlUtil = new XmlUtil();
		xml = xmlUtil.lineRemoveDrillDown(xml, sourceCiCode, targetCiCode, dirllDiagramName);//给cI添加钻取视图
		oldParam.put("xml", xml);
		oldParam.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
		return updateDiagramContent(oldParam);
	}


	/**
	 * @param diagramName
	 * @param sourceCiCode
	 * @param targetCiCode
	 * @param rltClassName
	 * @param dirllDiagramName
	 * @return
	 * @throws DocumentException
	 * 给关系删除钻取视图
	 */
	public JSONObject updateDiagramContentRltRemoveDrillDown(String diagramName, String sourceCiCode, String targetCiCode, String rltClassName, String dirllDiagramName) throws DocumentException {
		JSONObject oldParam = getDiagramInfo(diagramName);
		String xml = oldParam.getString("xml");
		//组装xml
		XmlUtil xmlUtil = new XmlUtil();
		xml = xmlUtil.rltRemoveDrillDown(xml, sourceCiCode, targetCiCode, rltClassName, dirllDiagramName);//给cI添加钻取视图
		oldParam.put("xml", xml);
		oldParam.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
		return updateDiagramContent(oldParam);
	}

	/**
	 * @param diagramName
	 * @param ciCode
	 * @param labelPosition
	 * @return
	 * @throws DocumentException
	 * 给CI设置label位置
	 */
	public JSONObject updateDiagramContentCILabelSetup(String diagramName, String ciCode, String labelPosition) throws DocumentException {
		JSONObject oldParam = getDiagramInfo(diagramName);
		String xml = oldParam.getString("xml");
		//组装xml
		XmlUtil xmlUtil = new XmlUtil();
		xml = xmlUtil.ciLabelSetup(xml, ciCode, labelPosition);//给cI添加钻取视图
		oldParam.put("xml", xml);
		oldParam.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
		return updateDiagramContent(oldParam);
	}

	/**
	 * @param diagramName
	 * @param ciCode
	 * @return
	 * @throws DocumentException
	 * CI右键解除CI
	 */
	public JSONObject updateDiagramContentCIGetRidOfCi(String diagramName, String ciCode) throws DocumentException {
		JSONObject oldParam = getDiagramInfo(diagramName);
		CiUtil ciUtil = new CiUtil();
		BigDecimal ciId = ciUtil.getCiId(ciCode);
		JSONArray diagramEles = new JSONArray();
		if(oldParam.has("diagramEles")){
			diagramEles = (JSONArray) oldParam.get("diagramEles");
		}
		for (int i=0; i<diagramEles.length(); i++){
			JSONObject tmp = (JSONObject) diagramEles.get(i);
			if (tmp.getBigDecimal("eleId") == ciId){
				diagramEles.remove(i);
				break;
			}
		}
		//xml
		String xml = oldParam.getString("xml");
		XmlUtil xmlUtil = new XmlUtil();
		xml = xmlUtil.ciGetRidOfCi(xml, ciCode);//给cI解除CI
		oldParam.put("xml", xml);
		String ci3dPoint=oldParam.getString("ci3dPoint");
		Ci3dPointUtil ci3dPointUtil = new Ci3dPointUtil();
		ci3dPoint = ci3dPointUtil.ci3dPointCIGetRidOfCi(ci3dPoint, ciCode);
		oldParam.put("ci3dPoint", ci3dPoint);
		//thumbnail
		oldParam.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
		return updateDiagramContent(oldParam);
	}


	/**
	 * @param diagramName
	 * @param ciCode
	 * @param x
	 * @param y
	 * @return
	 * @throws DocumentException
	 * 在解除过CI的基础上新建ci
	 */
	public JSONObject updateDiagramContentCICreateCi(String diagramName, String oldCiCode, String newCiCode, String x, String y) throws DocumentException {
		JSONObject oldParam = getDiagramInfo(diagramName);
		String xml = oldParam.getString("xml");
		String  ci3dPoint= oldParam.getString("ci3dPoint");
		//组装xml
		String clsName = "";
		CiClassUtil ciClsUtil = new CiClassUtil();
		clsName = ciClsUtil.getCiClassNameByCiCode(newCiCode);
		QueryPageByIndex queryPageByIndex = new QueryPageByIndex();
		JSONObject CIPageByIndex = queryPageByIndex.queryCIPageByIndex(clsName, 1, 10000);
		JSONArray data = CIPageByIndex.getJSONObject("data").getJSONArray("data");
		//assertTrue(data.length()>0);
		if (data!=null){
			for (int i=0; i<data.length(); i++){
				JSONObject ciAllObj = data.getJSONObject(i);
				JSONObject ciObj =  ciAllObj.getJSONObject("ci");
				if (ciObj.getString("ciCode").compareToIgnoreCase(newCiCode)==0){
					String ciId = String.valueOf(ciObj.getBigDecimal("id"));
					String icon = getClsIconByCiCode(newCiCode);
					String label = "";
					JSONArray attrDefs = ciAllObj.getJSONArray("attrDefs");
					for (int j=0; j<attrDefs.length(); j++){
						JSONObject temp = (JSONObject) attrDefs.get(j);
						if (temp.getInt("isMajor")==1){
							label = temp.getString("proName");
						}
					}

					List utdataLabelValObj =  new ArrayList();
					List ci3dPointLabelValObj =  new ArrayList();
					JSONArray attrsArr = ciAllObj.getJSONArray("attrs");
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

					XmlUtil xmlUtil = new XmlUtil();
					xml = xmlUtil.ciCreateCi(xml,oldCiCode,newCiCode,ciId,icon,label, utdataLabelValObj, x, y);//增加CI
					Ci3dPointUtil ci3dPointUtil = new Ci3dPointUtil();
					ci3dPoint = ci3dPointUtil.ci3dPointciCreateCi(ci3dPoint,oldCiCode,newCiCode,ciId,icon,label, ci3dPointLabelValObj, x, y);
					break;
				}
			}
		}
		oldParam.put("xml", xml);
		oldParam.put("ci3dPoint", ci3dPoint);
		//需要自己组装
		oldParam.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
		JSONArray diagramEles = new JSONArray();
		if(oldParam.has("diagramEles")){
			diagramEles = (JSONArray) oldParam.get("diagramEles");
		}
		CiUtil ciUtil = new CiUtil();
		BigDecimal ciId = ciUtil.getCiId(newCiCode);
		JSONObject diagramEle = new JSONObject();
		diagramEle.put("eleType", 1);
		diagramEle.put("eleId", ciId);
		diagramEles.put(diagramEle);
		oldParam.put("diagramEles", diagramEles);
		return updateDiagramContent(oldParam);
	}


	/**
	 * @param objType 操作对象类型 ci， canvas， img
	 * @param diagramName
	 * @param ciCode
	 * @param dynamicCiCode
	 * @return
	 * @throws DocumentException
	 * 挂载动态节点 修改挂载节点
	 */
	public JSONObject updateDiagramContentMountDynamicNode(String objType, String diagramName, String ciCode, List<String> dynamicCiCodeList) throws DocumentException {
		JSONObject oldParam = getDiagramInfo(diagramName);
		String xml = oldParam.getString("xml");
		String  ci3dPoint= oldParam.getString("ci3dPoint");
		CiUtil ciUtil = new CiUtil();
		for (int i=0; i<dynamicCiCodeList.size(); i++){
			String dynamicCiCode = dynamicCiCodeList.get(i);
			BigDecimal dynamicCiId = ciUtil.getCiId(dynamicCiCode);
			//组装xml
			XmlUtil xmlUtil = new XmlUtil();
			if ("ci".compareToIgnoreCase(objType)==0){
				xml = xmlUtil.ciMountDynamicNode(xml,ciCode,String.valueOf(dynamicCiId));//增加CI
				//修改ci3dPoint
				Ci3dPointUtil ci3dPointUtil = new Ci3dPointUtil();
				ci3dPoint = ci3dPointUtil.ci3dPointciMountDynamicNode(ci3dPoint,ciCode,String.valueOf(dynamicCiId));
				//需要自己组装 svg 前台功能不处理
			}
			if ("canvas".compareToIgnoreCase(objType)==0){
				xml = xmlUtil.canvasMountDynamicNode(xml,ciCode,String.valueOf(dynamicCiId));//增加CI
				//修改ci3dPoint svg 前台功能暂时不处理
			}
		}
		oldParam.put("xml", xml);
		oldParam.put("ci3dPoint", ci3dPoint);
		oldParam.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
		return updateDiagramContent(oldParam);
	}

	/**
	 * @param diagramName
	 * @param ciCode
	 * @param dynamicCiCode
	 * @return
	 * @throws DocumentException
	 * 取消挂载节点
	 */
	public JSONObject updateDiagramContentRemoveMountDynamicNode(String opType, String diagramName, String ciCode) throws DocumentException {
		JSONObject oldParam = getDiagramInfo(diagramName);
		String xml = oldParam.getString("xml");
		String  ci3dPoint= oldParam.getString("ci3dPoint");
		//组装xml
		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement();      
		Element rootElement  = graphElement.element("root");
		List<Element> eleList = rootElement.elements();

		if ("ci".compareToIgnoreCase(opType)==0){
			for (int i=0; i<eleList.size(); i++){
				Element tempEle = eleList.get(i);

				if (tempEle.attribute("code")!=null){
					if (ciCode.compareToIgnoreCase(tempEle.attributeValue("code"))==0){
						Element mxEle = tempEle.element("mxCell"); 
						mxEle.remove(mxEle.attribute("uttags-info"));
						mxEle.remove(mxEle.attribute("uttags-params-info"));
						mxEle.setAttributeValue("uttags-selection-cis", "[]");
						break;
					}
				}
			}
			//修改ci3dPoint
			//ci3dPoint = ci3dPointciMountDynamicNode(ci3dPoint,ciCode);
			JSONObject obj = new JSONObject(ci3dPoint);
			JSONArray nodes=obj.getJSONArray("nodes");
			for (int j=0; j<nodes.length();j++){
				JSONObject tempObj = nodes.getJSONObject(j);
				if (ciCode.compareToIgnoreCase(tempObj.getString("code"))==0){
					tempObj.remove("tags-info");
					tempObj.remove("tags-params-info");
					tempObj.put("tags-selection-cis", new ArrayList<>());
					break;
				}
			}
			obj.put("nodes", nodes);
			ci3dPoint= obj.toString();
		}

		if ("canvas".compareToIgnoreCase(opType)==0){
			for (int i=0; i<eleList.size(); i++){
				Element tempEle = eleList.get(i);
				if ("1".compareToIgnoreCase(tempEle.attributeValue("id")) ==0){
					tempEle.remove(tempEle.attribute("uttags-info"));
					tempEle.remove(tempEle.attribute("uttags-params-info"));
					tempEle.setAttributeValue("uttags-selection-cis", "[]");
					break;
				}
			}
		}
		xml = doc.getRootElement().asXML();
		oldParam.put("xml", xml);
		oldParam.put("ci3dPoint", ci3dPoint);
		oldParam.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
		return updateDiagramContent(oldParam);
	}


	/**
	 * @param diagramName
	 * @param ciCode
	 * @return
	 * @throws DocumentException
	 * 右键CI关系绘图
	 */
	public JSONObject updateDiagramContentCIRelationDraw(String diagramName, String ciCode) throws DocumentException {
		JSONObject oldParam = getDiagramInfo(diagramName);
		JSONArray diagramEles = new JSONArray();
		if(oldParam.has("diagramEles")){
			diagramEles = (JSONArray) oldParam.get("diagramEles");
		}
		String xml = oldParam.getString("xml");
		String  ci3dPoint= oldParam.getString("ci3dPoint");
		Document doc = DocumentHelper.parseText(xml); 
		Element graphElement = doc.getRootElement();      
		Element rootElement  = graphElement.element("root");
		Iterator userObjectIters = rootElement.elementIterator("UserObject"); 
		// 遍历root节点
		//取ci的坐标
		String x = "";
		String y = "";
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
		XmlUtil xmlUtil = new XmlUtil();
		SvgUtil svgUtil = new SvgUtil();
		Ci3dPointUtil ci3dPointUtil = new Ci3dPointUtil();
		QueryUpAndDownRltAndClass queryUpAndDownRltAndClass = new QueryUpAndDownRltAndClass();
		JSONObject upDownRltAndClassResult = queryUpAndDownRltAndClass.queryUpAndDownRltAndClass(ciCode, "5", "5");
		Map rltClasssMap= new HashMap<>();
		JSONArray rltArr =upDownRltAndClassResult.getJSONObject("data").getJSONArray("ciClasss");
		for (int i=0; i<rltArr.length(); i++){
			JSONObject temp = rltArr.getJSONObject(i);
			rltClasssMap.put(temp.getBigDecimal("id"), temp.getString("className"));
		}

		Map ciClasssMap= new HashMap<>();
		JSONArray clsArr =upDownRltAndClassResult.getJSONObject("data").getJSONArray("ciClasss");
		for (int i=0; i<clsArr.length(); i++){
			JSONObject temp = clsArr.getJSONObject(i);
			ciClasssMap.put(temp.getBigDecimal("id"), temp.getString("icon"));
		}

		QueryUpAndDownRlt queryUpAndDownRlt = new QueryUpAndDownRlt();
		JSONObject upDownRltResult = queryUpAndDownRlt.queryUpAndDownRlt(ciCode, "5", "5");
		assertTrue(upDownRltResult.getBoolean("success"));
		assertEquals(upDownRltResult.getInt("code"), -1);
		assertTrue(upDownRltResult.has("data"));
		JSONArray  upDownRltData= upDownRltResult.getJSONArray("data");
		for (int i=0; i<upDownRltData.length(); i++){
			JSONObject tempObj = (JSONObject) upDownRltData.get(i);
			JSONObject sourceCiInfo =  tempObj.getJSONObject("sourceCiInfo");
			JSONObject targetCiInfo =  tempObj.getJSONObject("targetCiInfo");
			JSONObject ciRlt =  tempObj.getJSONObject("ciRlt");
			//增加新节点
			String sourceCiCode = sourceCiInfo.getJSONObject("ci").getString("ciCode");
			String targetCiCode = targetCiInfo.getJSONObject("ci").getString("ciCode");
			JSONObject newCiInfo = new JSONObject();
			if(sourceCiCode.compareToIgnoreCase(ciCode)!=0){
				newCiInfo = sourceCiInfo;
			}
			if(targetCiCode.compareToIgnoreCase(ciCode)!=0){
				newCiInfo = targetCiInfo;
			}

			JSONObject ciObj = newCiInfo.getJSONObject("ci");
			BigDecimal ciId = ciObj.getBigDecimal("id");
			String icon =(String) ciClasssMap.get(ciObj.getBigDecimal("classId"));
			List utdataLabelValObj =  new ArrayList();
			List ci3dPointLabelValObj =  new ArrayList();
			JSONObject attrsArr = newCiInfo.getJSONObject("attrs");
			Iterator iterator = attrsArr.keys();
			while(iterator.hasNext()){
				String key = (String) iterator.next();
				String  value = attrsArr.getString(key);
				JSONObject m= new JSONObject();
				JSONObject ci3dm= new JSONObject();

				m.put("\"value\"", "\""+value+"\"");
				m.put("\"key\"", "\""+key+"\"");
				m.put("\"$$hashkey\"", "\"object:3000\"");

				ci3dm.put("value",value);
				ci3dm.put("key", key);
				ci3dm.put("$$hashkey", "object:3000");
				utdataLabelValObj.add(m);
				ci3dPointLabelValObj.add(ci3dm);
			}

			x = String.valueOf(Integer.valueOf(x)+(i+1)*200);
			y = String.valueOf(Integer.valueOf(y)+(i+1)*30);

			//先组装svg 在组装xml ， 因为svg用到了该节点上一个节点的坐标
			//svg = svgUtil.addCiSvg(svg, xml, icon, ciCode, x, y);//增加CI

			xml = xmlUtil.addCI(xml,ciObj.getString("ciCode"),String.valueOf(ciId),icon,"", utdataLabelValObj, x, y);//增加CI

			ci3dPoint = ci3dPointUtil.ci3dPointAddNode(ci3dPoint,"ci",ciObj.getString("ciCode"),String.valueOf(ciId),icon,"", ci3dPointLabelValObj, x, y);

			String clsName = (String) rltClasssMap.get(ciRlt.getBigDecimal("classId"));
			xml = xmlUtil.addRlt(xml, clsName, String.valueOf(ciRlt.getBigDecimal("id")), ciRlt.getString("sourceCiCode"), ciRlt.getString("targetCiCode"));

			ci3dPoint = ci3dPointUtil.ci3dPointAddEdges(ci3dPoint, clsName, ciRlt.getString("sourceCiCode"), ciRlt.getString("targetCiCode"));
			JSONObject diagramEle = new JSONObject();
			diagramEle.put("eleType", 1);
			diagramEle.put("eleId", ciId);
			diagramEles.put(diagramEle);

		}
		oldParam.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
		oldParam.put("xml", xml);
		oldParam.put("ci3dPoint", ci3dPoint);
		oldParam.put("diagramEles", diagramEles);
		return updateDiagramContent(oldParam);
	}


	/**
	 * @param diagramName
	 * @return
	 * 清空画布
	 */
	public JSONObject  	updateDiagramContentClearCanvas(String diagramName){
		JSONObject oldParam = getDiagramInfo(diagramName);
		String ci3dPoint = Contants.DMV_INIT_CI3DPOINT;
		String xml = Contants.DMV_INIT_XML;
		oldParam.put("xml", xml);
		oldParam.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);

		oldParam.put("ci3dPoint", ci3dPoint);
		oldParam.put("diagramEles", new JSONArray());
		UpdateDiagramContent ud = new UpdateDiagramContent();
		return ud.updateDiagramContent(oldParam);
	}
	/**
	 * @param ciCode
	 * @return
	 * 根据ciCode获取class图标链接
	 */
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
	 * @param ciResult
	 * @return
	 * sunsl
	 */
	public HashMap updateDiagramContentAddCI(JSONObject ciResult){
		JSONArray data = ciResult.getJSONObject("data").getJSONArray("data");
		List ciCodeList = new ArrayList();
		JSONArray ciIds = new JSONArray();
		HashMap resutlHashMap = new HashMap();
		JSONArray diagramEles = new JSONArray();
		int k = 0;
		for (int i = 0; i < data.length(); i++) {
			HashMap ciCodeHashMap = new HashMap();
			JSONObject ciObj = (JSONObject) data.get(i);
			JSONObject ci = ciObj.getJSONObject("ci");
			BigDecimal ciId = ci.getBigDecimal("id");
			// 我的查看视图导出配置信息用
			ciIds.put(ciId);
			ciCodeHashMap.put("ciCode", ci.getString("ciCode"));
			ciCodeList.add(ciCodeHashMap);
			JSONObject diagramEle = new JSONObject();
			diagramEle.put("eleType", 1);
			diagramEle.put("eleId", ciId);
			diagramEles.put(diagramEle);
			if (k==2){
				break;
			}
			k = k+1;
		}
		resutlHashMap.put("ciCodeList", ciCodeList);
		resutlHashMap.put("diagramEles", diagramEles);
		resutlHashMap.put("ciIds",ciIds);
		return resutlHashMap;
	}
	/**
	 * @param diagramName
	 * @param dataUpType
	 * @return result
	 * sunsl
	 */
	public JSONObject updateDiagramContentDataUpType(String diagramName,String dataUpType){
		//取得视图信息
		JSONObject oldParam = getDiagramInfo(diagramName);
		JSONObject oldDiagram = oldParam.getJSONObject("diagram");
		//设置数据开关
		oldDiagram.put("dataUpType", dataUpType);
		oldParam.put("diagram", oldDiagram);
		oldParam.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
		return updateDiagramContent(oldParam);
	}


	/**
	 * @param diagramName
	 * @return
	 * 清除背景
	 */
	public JSONObject updateDiagramContentClearBgImg(String diagramName){
		//取得视图信息
		JSONObject oldParam = getDiagramInfo(diagramName);
		JSONObject oldDiagram = oldParam.getJSONObject("diagram");
		//设置数据开关
		oldDiagram.put("diagramBgImg", "");
		oldParam.put("diagram", oldDiagram);
		oldParam.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
		return updateDiagramContent(oldParam);

	}


	/**
	 * @param diagramName
	 * @return
	 * CI右键还原关系
	 * @throws DocumentException 
	 * @throws JSONException 
	 */
	public JSONObject updateDiagramContentCiReductionRlt(String diagramName, String ciCode1, String ciCode2) throws JSONException, DocumentException{
		//取得视图信息
		JSONObject oldParam = getDiagramInfo(diagramName);
		String xml = oldParam.getString("xml");
		QueryCiBetweenRlt queryCiBetweenRlt = new QueryCiBetweenRlt();
		JSONObject rltResult = queryCiBetweenRlt.queryMultiCiBetweenRlt(ciCode1,ciCode2);
		JSONArray rltDataArr = rltResult.getJSONArray("data");
		XmlUtil xmlUtil = new XmlUtil();
		CiClassUtil ciClsUtil = new CiClassUtil();
		for (int i=0; i<rltDataArr.length(); i++){
			JSONObject ciRlts = (JSONObject) rltDataArr.get(i);
			JSONObject tempObj = ciRlts.getJSONObject("ciRlt");
			//增加关系
			String rltClsName = ciClsUtil.getClsNameByClsId(tempObj.getBigDecimal("classId"));
			xml = xmlUtil.addRlt(xml, rltClsName, String.valueOf(tempObj.getBigDecimal("id")), tempObj.getString("sourceCiCode"), tempObj.getString("targetCiCode"));
		}
		oldParam.put("xml", xml);
		oldParam.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
		return updateDiagramContent(oldParam);
	}

	/**
	 * @param diagramName
	 * @return
	 * 获取视图信息
	 * 该方法用的地方比较多， 如修改， 请加入修改记录
	 */
	private JSONObject getDiagramInfo(String diagramName){
		if (diagramName.isEmpty() ){
			fail("画布视图不存在");
		}
		QueryDiagramInfoById queryDiagramInfoById  = new QueryDiagramInfoById();
		JSONObject result = queryDiagramInfoById.queryDiagramInfoById(diagramName, true);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
		JSONObject oldParam = result.getJSONObject("data");
		oldParam.put("id",  oldParam.getJSONObject("diagram").getBigDecimal("id"));
		return oldParam;
	}


}


