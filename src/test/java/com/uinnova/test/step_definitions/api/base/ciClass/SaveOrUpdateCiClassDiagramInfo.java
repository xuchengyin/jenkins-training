package com.uinnova.test.step_definitions.api.base.ciClass;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.contant.Contants;
import com.uinnova.test.step_definitions.api.RestApi;

/**
 * @author wsl
 * 可视化建模保存
 *
 */
public class SaveOrUpdateCiClassDiagramInfo extends RestApi{
	/**
	 * @return
	 */
	public JSONObject saveOrUpdateCiClassDiagramInfo() {
		JSONObject ciClassDiagramInfo = new JSONObject();
		String xml = Contants.BASE_VISUAL_XML;
		ciClassDiagramInfo.put("xml", xml);
		//ciClassDiagramInfo.put("thumbnail", Contants.BASE_VISUAL_THUMBNAIL);
		ciClassDiagramInfo.put("svg", "<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" width=\"20px\" height=\"20px\" version=\"1.1\" style=\"background-color: rgb(255, 255, 255);\"><defs/><g transform=\"translate(0.5,0.5)\"/></svg>");
		QueryCiClassDiagramInfoList queryCiClassDiagramInfoList = new QueryCiClassDiagramInfoList();
		JSONObject queryResult = queryCiClassDiagramInfoList.queryCiClassDiagramInfoList();
		JSONArray array = queryResult.getJSONArray("data");
		JSONObject classDiagram = new JSONObject();
		if (array!=null && array.length()>0){
			JSONObject tempObj = (JSONObject) array.get(0);
			//classDiagram.put("id",tempObj.getJSONObject("classDiagram").get("id"));
			classDiagram =  tempObj.getJSONObject("classDiagram");
		}
		
			ciClassDiagramInfo.put("classDiagram", classDiagram);
		String url = ":1511/tarsier-vmdb/cmv/ciClass/saveOrUpdateCiClassDiagramInfo";
		return doRest(url,ciClassDiagramInfo.toString(), "POST");
	}

}
