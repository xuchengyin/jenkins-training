package com.uinnova.test.step_definitions.api.dmv.diagram;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.dmv.DiagramUtil;

/**
 * 编写时间:2017-11-15
 * 编写人:sunsl
 * 功能介绍:删除视图类
 */
public class RemoveDirByIds extends RestApi{
	public JSONObject removeDirByIds(String diagramName){
		String url = ":1511/tarsier-vmdb/dmv/diagram/removeDirByIds";
		DiagramUtil diagramUtil = new DiagramUtil();
		JSONObject param = new JSONObject();
		JSONArray dirIds = new JSONArray();
		JSONArray diagramIds = new JSONArray();
		diagramIds.put(diagramUtil.getDiagramIdByName(diagramName));
		param.put("dirIds", dirIds);
		param.put("diagramIds", diagramIds);
		return doRest(url, param.toString(), "POST");
	}
	
	public JSONObject removeDirByIds(JSONArray dirIds,JSONArray diagramIds){
		String url = ":1511/tarsier-vmdb/dmv/diagram/removeDirByIds";
		JSONObject param = new JSONObject();
		param.put("dirIds", dirIds);
		param.put("diagramIds", diagramIds);
		return doRest(url, param.toString(), "POST");
	}
}
