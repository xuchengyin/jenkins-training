package com.uinnova.test.step_definitions.api.dmv.diagram;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-12-28 编写人:sunsl 功能介绍:查询视图类
 */
public class QueryDiagramTree extends RestApi {
	public JSONObject queryDiagramTree() {
		String url = ":1511/tarsier-vmdb/dmv/diagram/queryDiagramTree";
		return doRest(url, "{}", "POST");
	}
}
