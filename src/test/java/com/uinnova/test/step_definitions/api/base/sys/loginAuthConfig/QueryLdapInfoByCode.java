package com.uinnova.test.step_definitions.api.base.sys.loginAuthConfig;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class QueryLdapInfoByCode extends RestApi{

	public JSONObject queryLdapInfoByCode(String str){
		String url = ":1511/tarsier-vmdb/cmv/sys/loginAuthConfig/queryLdapInfoByCode";
		return doRest(url, str, "POST");
	}
}
