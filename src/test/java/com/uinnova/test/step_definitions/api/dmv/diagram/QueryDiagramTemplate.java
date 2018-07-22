package com.uinnova.test.step_definitions.api.dmv.diagram;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-11-15 编写人:sunsl 功能介绍:DMV广场新建视图时取得模板类
 */
public class QueryDiagramTemplate extends RestApi {
	public JSONObject queryDiagramTemplate() {
		String url = ":1511/tarsier-vmdb/dmv/diagram/queryDiagramTemplate";
		return doRest(url, "{}", "POST");
	}
}
