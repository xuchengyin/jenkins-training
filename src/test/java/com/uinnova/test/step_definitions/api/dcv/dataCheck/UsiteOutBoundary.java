package com.uinnova.test.step_definitions.api.dcv.dataCheck;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class UsiteOutBoundary extends RestApi{
	
	public JSONObject usiteOutBoundary(){
	String url = ":1511/tarsier-vmdb/dcv/dataCheck/usiteOutBoundary";
	return doRest(url, null, "POST");
	}	

}
