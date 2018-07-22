package com.uinnova.test.step_definitions.api.dmv.comb;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-12-06
 * 编写人:sunsl
 * 功能介绍:DMV新建组合视图的类
 */
public class SaveOrUpdateCombDiagram extends RestApi{
	public JSONObject saveOrUpdateCombDiagram(JSONObject diagramInfo){
		String url =":1511/tarsier-vmdb/dmv/comb/saveOrUpdateCombDiagram";
		JSONObject param = new JSONObject();
		param.put("diagramInfo", diagramInfo);
		return doRest(url, param.toString(), "POST");
	}
}
