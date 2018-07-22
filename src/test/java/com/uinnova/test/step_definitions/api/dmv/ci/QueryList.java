package com.uinnova.test.step_definitions.api.dmv.ci;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-12-13 编写人:sunsl 功能介绍：查询视图CI
 */
public class QueryList extends RestApi{
	public JSONObject queryList(JSONArray ciQ,JSONArray ciCodes) {
		String url = ":1511/tarsier-vmdb/dmv/ci/queryList";
		JSONObject param = new JSONObject();
		JSONObject cdt = new JSONObject();
		cdt.put("ciCodes", ciCodes);
		cdt.put("ciQ", ciQ);
		param.put("cdt", cdt);
		return doRest(url, param.toString(), "POST");
	}
}
