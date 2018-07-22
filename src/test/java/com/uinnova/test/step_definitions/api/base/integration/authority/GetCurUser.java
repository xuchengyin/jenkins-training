package com.uinnova.test.step_definitions.api.base.integration.authority;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class GetCurUser extends RestApi{
	/**
	 * 获取当前用户信息
	 * @return
	 */
	public JSONObject getCurUser() {
		String url = ":1511/tarsier-vmdb/cmv/integration/authority/getCurUser";
		return doRest(url, "", "POST");
	}

}
