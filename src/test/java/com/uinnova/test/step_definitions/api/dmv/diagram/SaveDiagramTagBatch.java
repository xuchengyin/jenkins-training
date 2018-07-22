package com.uinnova.test.step_definitions.api.dmv.diagram;

import java.math.BigDecimal;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.dmv.DiagramUtil;

/**
 * 编写时间:2017-11-17
 * 编写人:sunsl
 * 功能介绍:DMV视图新建标签类
 */
public class SaveDiagramTagBatch extends RestApi{
	public JSONObject saveDiagramTagBatch(String diagramName,String tagName){
		String url =":1511/tarsier-vmdb/dmv/diagram/saveDiagramTagBatch";
		DiagramUtil diagramUtil = new DiagramUtil();
		BigDecimal diagramId = diagramUtil.getDiagramIdByName(diagramName);
		JSONObject param = new JSONObject();
		JSONArray tags = new JSONArray();
		JSONObject tagNameObj = new JSONObject();
		tagNameObj.put("tagName", tagName);
		tags.put(tagNameObj);
		param.put("diagramId", String.valueOf(diagramId));
		param.put("tags", tags);
		return doRest(url, param.toString(), "POST");
	}
}
