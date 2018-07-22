package com.uinnova.test.step_definitions.api.dmv.diagram;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.dmv.DiagramUtil;

/**
 * 编写时间:2017-12-13
 * 编写人:sunsl
 * 功能介绍:DMV查看视图查询标签类
 */
public class QueryDiagramTagListByIds extends RestApi{
    public JSONObject queryDiagramTagListByIds(String diagramName){
    	String url =":1511/tarsier-vmdb/dmv/diagram/queryDiagramTagListByIds";
    	DiagramUtil diagramUtil = new DiagramUtil();
    	JSONObject param = new JSONObject();
		JSONArray ids = new JSONArray();
		ids.put(diagramUtil.getDiagramIdByName(diagramName));
		param.put("ids", ids);
    	return doRest(url, param.toString(), "POST");
    }
}
