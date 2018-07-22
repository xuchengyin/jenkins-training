package com.uinnova.test.step_definitions.api.base.sys.loginAuthConfig;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class SaveLdapConfigInfo extends RestApi{

	public JSONObject saveLdapConfigInfo(JSONObject record, JSONObject ldapProp){
		String url = ":1511/tarsier-vmdb/cmv/sys/loginAuthConfig/saveLdapConfigInfo";
		JSONObject param = new JSONObject();
		param.put("record", record);
		param.put("ldapProp", ldapProp);
		return doRest(url, param.toString(), "POST");
	}
	
	
	public JSONObject saveLdapConfigInfoFailed(JSONObject record, JSONObject ldapProp, String kw){
		String url = ":1511/tarsier-vmdb/cmv/sys/loginAuthConfig/saveLdapConfigInfo";
		JSONObject param = new JSONObject();
		param.put("record", record);
		param.put("ldapProp", ldapProp);
		return doFailRest(url, param.toString(), "POST", kw);
	}
}
