package com.uinnova.test.step_definitions.api.base.sys.auth;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.AuthUtil;

public class RemoveRoleById extends RestApi{
	/**
	 * @param roleName
	 * @return
	 */
	public JSONObject removeRoleById(String roleName){
		String url = ":1511/tarsier-vmdb/cmv/sys/auth/removeRoleById";
		AuthUtil authUtil = new AuthUtil();
		return doRest(url, authUtil.getRoleId(roleName), "POST");
	}
}
