package com.uinnova.test.step_definitions.api.dmv.ciRltClass;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2018-01-12
 * 编写人:sunsl
 * 功能介绍:dmv 查询关系分类类
 */
public class QueryPage extends RestApi{
	public JSONObject queryPage(){
		String url =":1511/tarsier-vmdb/dmv/ciRltClass/queryPage";
		JSONObject param = new JSONObject();
		JSONObject cdt = new JSONObject();
		param.put("pageNum", 1);
		param.put("pageSize", 5);
		param.put("cdt", cdt);
		param.put("orders", "CLASS_NAME");
		return doRest(url, param.toString(), "POST");
	}
}
