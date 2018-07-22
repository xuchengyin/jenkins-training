package com.uinnova.test.step_definitions.api.dcv.systemSource;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class GetRsmBaseUrl extends RestApi{
	public JSONObject getRsmBaseUrl() {
		String url = ":1511/tarsier-vmdb/dcv/systemSource/getRsmBaseUrl";		
		return doRest(url, null, "GET");	
	}

}
