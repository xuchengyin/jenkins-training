package com.uinnova.test.step_definitions.api.base.sys.loginAuthConfig;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;


public class SetEffective extends RestApi{
	
	public JSONObject setEffective(String effective){
		String url = ":1511/tarsier-vmdb/cmv/sys/loginAuthConfig/setEffective";
		return doRest(url, effective, "POST");
	}
	
	public JSONObject setEffectiveFailed(String effective, String kw){
		String url = ":1511/tarsier-vmdb/cmv/sys/loginAuthConfig/setEffective";
		return doFailRest(url, effective, "POST", kw);
	}
}
