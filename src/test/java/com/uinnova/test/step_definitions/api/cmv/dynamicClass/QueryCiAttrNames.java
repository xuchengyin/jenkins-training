package com.uinnova.test.step_definitions.api.cmv.dynamicClass;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class QueryCiAttrNames extends RestApi{

	public JSONObject queryCiAttrNames(){
		String url = ":1511/tarsier-vmdb/cmv/dynamicClass/queryCiAttrNames";
		return doRest(url, "", "POST");
	}
}
