package com.uinnova.test.step_definitions.api.dmv.diagram;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-12-06
 * 编写人:sunsl
 * 功能介绍：DMV回收站查询视图类
 */
public class QueryRecycleDiagram extends RestApi{

	public JSONObject queryRecycleDiagram(String searchKey){
		String url = ":1511/tarsier-vmdb/dmv/diagram/queryRecycleDiagram";
		JSONObject param = new JSONObject();
		JSONObject cdt = new JSONObject();
		param.put("pageNum", 1);
		param.put("pageSize", 10000);
		cdt.put("name", "%" + searchKey + "%");
		param.put("cdt", cdt);
		param.put("orders", "MODIFY_TIME DESC");
		return doRest(url, param.toString(), "POST");
	}

	public JSONObject queryRecycleDiagram(){
		String url = ":1511/tarsier-vmdb/dmv/diagram/queryRecycleDiagram";
		JSONObject param = new JSONObject();
		JSONObject cdt = new JSONObject();
		param.put("pageNum", 1);
		param.put("pageSize", 10000);
		cdt.put("name", "%%");
		param.put("cdt", cdt);
		param.put("orders", "MODIFY_TIME DESC");
		return doRest(url, param.toString(), "POST");
	}
}
