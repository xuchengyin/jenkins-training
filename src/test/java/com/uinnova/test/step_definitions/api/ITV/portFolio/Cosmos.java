package com.uinnova.test.step_definitions.api.ITV.portFolio;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * @author wjx          
 * @请求方式 get
 * 功能介绍：获取组合视图
 */
public class Cosmos extends RestApi {
	public JSONObject getViewList() {
		String url = ":1511/tarsier-vmdb/dcv/vcDiagram/portfolio/cosmos?";
		return doRest(url, null, "GET");
	}
}
