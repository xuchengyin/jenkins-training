package com.uinnova.test.step_definitions.api.dmv.ciRlt;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class QueryInfoList extends RestApi{
	public JSONObject queryInfoList(){
		String url = ":1511/tarsier-vmdb/dmv/ciRlt/queryInfoList";
		return doRest(url, "{}", "POST");
	}
}
