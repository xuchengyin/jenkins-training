package com.uinnova.test.step_definitions.api.dcv.dataCheck;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class UsiteVeraciry extends RestApi{
	
	public JSONObject usiteVeracity(){
	String url = ":1511/tarsier-vmdb/dcv/dataCheck/usiteVeracity";
	return doRest(url, null, "POST");
	}	


}
