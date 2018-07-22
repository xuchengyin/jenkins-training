package com.uinnova.test.step_definitions.api.dcv.dataCheck;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class ParentAttrIsNull extends RestApi{
	
	public JSONObject parentAttrIsNull(){
	String url = ":1511/tarsier-vmdb/dcv/dataCheck/parentAttrIsNull";
	return doRest(url, null, "POST");
	}

}
