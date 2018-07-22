package com.uinnova.test.step_definitions.api.emv.severity;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
/*
 * 编写时间:2018-05-27
 * 编写人:yll
 * 功能介绍:查询配置字典里面的事件级别列表
 */
public class SeverityList extends RestApi{
	public JSONObject severityList(){
		String url =":1516/monitor-web/severity/list";
		   JSONObject param = new JSONObject();
		   return doRest(url, "{}", "POST");
	}

}
