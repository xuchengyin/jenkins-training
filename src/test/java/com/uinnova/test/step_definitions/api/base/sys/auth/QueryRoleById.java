package com.uinnova.test.step_definitions.api.base.sys.auth;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-11-07
 * 编写人:sunsl
 * 功能介绍:根据主键查询角色
 */
public class QueryRoleById extends RestApi{

	/**
	 * @param roleId
	 * @return
	 */
	public JSONObject queryRoleById(String roleId){
		String url=":1511/tarsier-vmdb/cmv/sys/auth/queryRoleById";
		return doRest(url, roleId, "POST");
	}
}
