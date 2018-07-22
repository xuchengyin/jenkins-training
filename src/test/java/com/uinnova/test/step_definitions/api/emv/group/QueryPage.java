package com.uinnova.test.step_definitions.api.emv.group;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
/*
 * 编写时间:2018-05-22
 * 编写人:yll
 * 功能介绍:查询团队管理页面里面所有的团队
 */
public class QueryPage extends RestApi {
	public JSONObject queryPage(){
		   String url =":1516/monitor-web/group/queryPage";
		   JSONObject param = new JSONObject();
		   return doRest(url, "{}", "POST");
	   }

}
