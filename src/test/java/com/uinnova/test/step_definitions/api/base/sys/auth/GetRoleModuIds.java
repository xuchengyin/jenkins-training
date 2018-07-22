package com.uinnova.test.step_definitions.api.base.sys.auth;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
/**
 * 编写时间:2017-11-08
 * 编写人:sunsl
 * 功能介绍:取得角色的功能权限
 */
public class GetRoleModuIds extends RestApi{

	/**
	 * @param roleId
	 * @return
	 */
	public JSONObject getRoleModuIds(String roleId){
		String url = ":1511/tarsier-vmdb/cmv/sys/auth/getRoleModuIds";
		return doRest(url, roleId, "POST");
	}
}
