package com.uinnova.test.step_definitions.api.dcv.dataCheck;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class CheckCabnetIsNull extends RestApi{
	public JSONObject checkCabnetIsNull(){
	String url = ":1511/tarsier-vmdb/dcv/dataCheck/checkCabnetIsNull";
	return doRest(url, null, "POST");
	}
}
