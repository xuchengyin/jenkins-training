package com.uinnova.test.step_definitions.api.base.sys.loginAuthConfig;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class TestLdapConnection extends RestApi{


	public JSONObject testLdapConnection(String type,String protoName, String hostName, String port, String baseDn, String loginUserDn, String password, String testUserName, String testPassword){
		String url = ":1511/tarsier-vmdb/cmv/sys/loginAuthConfig/TestLdapConnection";
		JSONObject param = new JSONObject();
		JSONObject record = new JSONObject();
		JSONObject ldapProp = new JSONObject();
		if(type.equals("1")){
			record.put("protoName", protoName);
			ldapProp.put("hostName", hostName);
			ldapProp.put("port", port);
			ldapProp.put("loginUserDn", "");
			ldapProp.put("password", "");
			ldapProp.put("baseDn", baseDn);
			ldapProp.put("userIdAttr", "uid");
			ldapProp.put("userNameRdnAttr", "uid");
			ldapProp.put("displayNameAttr", "gecos");
			ldapProp.put("mailAttr", "");
			param.put("record", record);
			param.put("ldapProp", ldapProp);
			param.put("testUserName", testUserName);
			param.put("testPassword", testPassword);
			
		}else{
			record.put("protoName", protoName);
			ldapProp.put("hostName", hostName);
			ldapProp.put("port", port);
			ldapProp.put("loginUserDn", loginUserDn);
			ldapProp.put("password", password);
			ldapProp.put("baseDn", baseDn);
			ldapProp.put("userIdAttr", "uid");
			ldapProp.put("userNameRdnAttr", "uid");
			ldapProp.put("displayNameAttr", "gecos");
			ldapProp.put("mailAttr", "");
			param.put("record", record);
			param.put("ldapProp", ldapProp);
		}

		return doRest(url, param.toString(), "POST");
	}
	
}
