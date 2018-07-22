package com.uinnova.test.step_definitions.api.dcv.integration.authority;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class GetCurUser extends RestApi{
	public JSONObject getCurUser(){
		String url = "1511/tarsier-vmdb/dcv/integration/authority/getCurUser";
		return doRest(url, null, "POST");
		
	}
}
