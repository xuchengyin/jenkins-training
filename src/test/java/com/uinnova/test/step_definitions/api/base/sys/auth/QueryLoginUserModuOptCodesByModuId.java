package com.uinnova.test.step_definitions.api.base.sys.auth;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 跟据模块id查询当前登录用户对应的模块及操作信息
 * @author wsl
 *2.18-3-16
 */
public class QueryLoginUserModuOptCodesByModuId extends RestApi{
	public JSONObject queryLoginUserModuOptCodesByModuId(){
		String url=":1511/tarsier-vmdb/cmv/sys/auth/queryLoginUserModuOptCodesByModuId";
		JSONObject param = new JSONObject();
		return doRest(url, param.toString(), "POST");
	}

}
