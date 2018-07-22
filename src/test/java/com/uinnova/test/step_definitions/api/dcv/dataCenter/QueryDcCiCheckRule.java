package com.uinnova.test.step_definitions.api.dcv.dataCenter;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class QueryDcCiCheckRule extends RestApi{
	public JSONObject queryDcCiCheckRule(String rule){
		String url = ":1511/tarsier-vmdb/dcv/dataCheck/queryDcCiCheckRule";
		JSONArray parameter = new JSONArray();
		parameter.put(rule);
		return doRest(url, parameter.toString(), "POST");
	}

}
