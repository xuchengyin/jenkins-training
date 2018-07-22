package com.uinnova.test.step_definitions.api.base.sys.auth;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-11-05
 * 编写人:sunsl
 * 功能介绍:查询角色类
 */
public class QueryRoleList extends RestApi{
	/**
	 * @return
	 */
	public JSONObject queryRoleList(){
		String url =":1511/tarsier-vmdb/cmv/sys/auth/queryRoleList";
		JSONObject param = new JSONObject();
		JSONObject cdt = new JSONObject();
		param.put("cdt", cdt);
		return doRest(url, param.toString(), "POST");
	}
}
