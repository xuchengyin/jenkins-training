package com.uinnova.test.step_definitions.api.cmv.sys.user;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class QueryById extends RestApi{

	public JSONObject queryById(String id){
		
		String url = ":1511/tarsier-vmdb/cmv/sys/user/queryById";
		
		return doRest(url, id, "POST");
	}
}
