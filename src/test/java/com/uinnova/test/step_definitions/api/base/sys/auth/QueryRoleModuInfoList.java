package com.uinnova.test.step_definitions.api.base.sys.auth;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
/**
 * 编写时间:2018-3-20
 * 编写人:lidw
 * 功能介绍:取得角色的功能权限,取代getRoleModulds
 */
public class QueryRoleModuInfoList extends RestApi{

	public JSONObject queryRoleModuInfoList(String ruleId){
		String url = ":1511/tarsier-vmdb/cmv/sys/auth/queryRoleModuInfoList";
		return doRest(url, ruleId, "POST");
	}
}
