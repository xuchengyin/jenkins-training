package com.uinnova.test.step_definitions.api.dcv.dataCheck;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class UsiteRepeat extends RestApi{
	
	public JSONObject usiteRepeat(){
	String url = ":1511/tarsier-vmdb/dcv/dataCheck/usiteRepeat";
	return doRest(url, null, "POST");
	}	


}
