package com.uinnova.test.step_definitions.api.dmv.config;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-12-22
 * 编写人:sunsl
 * 功能介绍:获取属性文件信息
 */
public class QueryConfigList extends RestApi{
	public JSONObject queryConfigList(String orders){
		String url = ":1511/tarsier-vmdb/dmv/config/queryConfigList";
		JSONObject param = new JSONObject();
		JSONObject cdt = new JSONObject();
		param.put("cdt", cdt);
		param.put("orders", orders);
		return doRest(url, param.toString(), "POST");
	}
}
