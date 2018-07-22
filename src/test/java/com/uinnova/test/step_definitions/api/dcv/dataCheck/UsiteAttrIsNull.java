package com.uinnova.test.step_definitions.api.dcv.dataCheck;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class UsiteAttrIsNull extends RestApi{
	
	public JSONObject usiteAttrIsNul(){
	String url = ":1511/tarsier-vmdb/dcv/dataCheck/usiteAttrIsNul";
	return doRest(url, null, "POST");
	}	
}
