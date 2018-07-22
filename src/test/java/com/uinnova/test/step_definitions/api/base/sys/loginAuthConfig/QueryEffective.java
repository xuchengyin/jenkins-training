package com.uinnova.test.step_definitions.api.base.sys.loginAuthConfig;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class QueryEffective extends RestApi{

	public JSONObject queryEffective(){
		String url = ":1511/tarsier-vmdb/cmv/sys/loginAuthConfig/queryEffective";
		//"OWNER","LDAP" 
		return doRest(url, "", "POST");
	}
}
