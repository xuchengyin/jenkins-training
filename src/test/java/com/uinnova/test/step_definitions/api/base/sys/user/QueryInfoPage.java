package com.uinnova.test.step_definitions.api.base.sys.user;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
/**
 * 编写时间:2017-11-06
 * 编写人:sunsl
 * 功能介绍:用户管理查询用户类
 */
public class QueryInfoPage extends RestApi{
	/**
	 * @param opName
	 * @return
	 */
	public JSONObject queryInfoPage(String opName){
		String url = ":1511/tarsier-vmdb/cmv/sys/user/queryInfoPage";
		JSONObject param = new JSONObject();
		JSONObject cdt = new JSONObject();
		cdt.put("opName", opName);
		param.put("cdt", cdt);
		param.put("pageNum", 1);
		param.put("pageSize", 30);
		return doRest(url, param.toString(), "POST");
	}
}
