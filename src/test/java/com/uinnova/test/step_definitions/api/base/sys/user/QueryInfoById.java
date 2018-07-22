package com.uinnova.test.step_definitions.api.base.sys.user;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-11-05
 * 编写人:sunsl
 * 功能介绍:根据主键查询用户信息类
 */
public class QueryInfoById extends RestApi{
	/**
	 * @param id
	 * @return
	 */
	public JSONObject queryInfoById(String id){
		String url = ":1511/tarsier-vmdb/cmv/sys/user/queryInfoById";
		return doRest(url, id, "POST");
	}

}
