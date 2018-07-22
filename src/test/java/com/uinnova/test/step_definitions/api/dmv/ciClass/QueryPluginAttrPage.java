package com.uinnova.test.step_definitions.api.dmv.ciClass;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-12-13
 * 编写人:sunsl
 * 功能介绍:查询CI外挂的属性类
 */
public class QueryPluginAttrPage extends RestApi{
	public JSONObject queryPluginAttrPage(){
		String url =":1511/tarsier-vmdb/dmv/ciClass/queryPluginAttrPage";
		JSONObject cdt = new JSONObject();
		JSONObject param = new JSONObject();
		param.put("pageNum", 1);
		param.put("pageSize", 100);
		param.put("cdt", cdt);
		param.put("order", "ID ASC");
		return doRest(url, param.toString(), "POST");
	}
}
