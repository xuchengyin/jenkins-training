package com.uinnova.test.step_definitions.api.base.sys.auth;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
/**
 * 编写时间:2017-11-8
 * 编写人:sunsl
 * 功能介绍:角色管理功能的查询类
 */
public class QueryRolePage extends RestApi{
	/**
	 * @param roleName
	 * @return
	 */
	public JSONObject queryRolePage(String roleName){
		String url =":1511/tarsier-vmdb/cmv/sys/auth/queryRolePage";
		JSONObject param = new JSONObject();
		JSONObject cdt = new JSONObject();
		cdt.put("roleName", roleName);
		param.put("cdt", cdt);
		param.put("pageNum", 1);
		param.put("pageSize", 30);
		return doRest(url, param.toString(), "POST");
	}
}
