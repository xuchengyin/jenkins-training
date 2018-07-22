package com.uinnova.test.step_definitions.api.dmv.diagram;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.dmv.DiagramUtil;

/**
 * 编写时间:2017-12-06
 * 编写人:sunsl
 * 功能介绍:删除回收站中的视图类
 */
public class RemoveDiagramByIds extends RestApi{

	public JSONObject removeDiagramByIds(String diagramName){ 
		String url=":1511/tarsier-vmdb/dmv/diagram/removeDiagramByIds";
		JSONObject param = new JSONObject();
		DiagramUtil diagramUtil = new DiagramUtil();
		JSONArray ids = new JSONArray();
		ids.put(diagramUtil.getDiagramIdByName(diagramName));
		param.put("ids", ids);
		return doRest(url, param.toString(), "POST");
	}

	public JSONObject removeDiagramByIds(){
		String url=":1511/tarsier-vmdb/dmv/diagram/removeDiagramByIds";
		JSONObject param = new JSONObject();
		DiagramUtil diagramUtil = new DiagramUtil();
		JSONArray ids = new JSONArray();
		param.put("ids", ids);
		return doRest(url, param.toString(), "POST");
	}
}
