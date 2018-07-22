package com.uinnova.test.step_definitions.api.dmv.group;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-11-22 编写人:sunsl 功能介绍：DMV查询小组视图数
 */
public class QueryGroupDiagramCount extends RestApi {
	public JSONObject queryGroupDiagramCount() {
		String url = ":1511/tarsier-vmdb/dmv/group/queryGroupDiagramCount";
		return doRest(url, "{}", "POST");
	}
}
