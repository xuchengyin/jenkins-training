package com.uinnova.test.step_definitions.api.cmv.monitor.event;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class QueryDefaultUrl extends RestApi{

	public JSONObject queryDefaultUrl(){
		String url = ":1511/tarsier-vmdb/cmv/monitor/event/queryDefaultUrl";
		//无参数
		return doRest(url, "", "POST");
	}
}
