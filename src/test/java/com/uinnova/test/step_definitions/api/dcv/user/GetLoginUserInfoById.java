package com.uinnova.test.step_definitions.api.dcv.user;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class GetLoginUserInfoById extends RestApi{
	public JSONObject getLoginUserInfoById(){
		String url = ":1511/tarsier-vmdb/dcv/user/getLoginUserInfoById";
		String parameter = "JSON=1";
		return doRest(url, parameter, "POST");
	}

}
