package com.uinnova.test.step_definitions.api.dmv.diagram;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.dmv.DiagramUtil;

/**
 * 编写时间:2017-12-07
 * 编写人:sunsl
 * 功能介绍:DMV我的将视图转化为模板
 */
public class ConvertDiagramAndTemplate extends RestApi{
	public JSONObject convertDiagramAndTemplate(String diagramName){
		String url =":1511/tarsier-vmdb/dmv/diagram/convertDiagramAndTemplate";
		JSONArray param = new JSONArray();
		DiagramUtil diagramUtil = new DiagramUtil();
		JSONObject diagramObj = new JSONObject();
		diagramObj.put("id", diagramUtil.getDiagramIdByName(diagramName));
		diagramObj.put("diagramType", 3);
		param.put(diagramObj);
		return doRest(url, param.toString(), "POST");
	}
}
