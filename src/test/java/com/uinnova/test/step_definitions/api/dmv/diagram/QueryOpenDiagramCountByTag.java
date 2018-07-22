package com.uinnova.test.step_definitions.api.dmv.diagram;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/*
 * 编写时间:2017-11-17
 * 编写人:sunsl
 * 功能介绍:查找标签类
 */
public class QueryOpenDiagramCountByTag extends RestApi{
	public JSONObject queryOpenDiagramCountByTag(){
		String url =":1511/tarsier-vmdb/dmv/diagram/queryOpenDiagramCountByTag";
		return doRest(url, "{}", "POST");
	}
}
