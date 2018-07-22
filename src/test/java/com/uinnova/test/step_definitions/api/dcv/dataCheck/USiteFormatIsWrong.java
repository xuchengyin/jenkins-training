package com.uinnova.test.step_definitions.api.dcv.dataCheck;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class USiteFormatIsWrong extends RestApi{
	
	public JSONObject uSiteFormatIsWrong(){
	String url = ":1511/tarsier-vmdb/dcv/dataCheck/uSiteFormatIsWrong";
	return doRest(url, null, "POST");
	}	

}
