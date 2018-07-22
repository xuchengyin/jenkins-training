package com.uinnova.test.step_definitions.api.cmv.monitor.severity;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2018-01-19
 * 编写人:sunsl
 * 功能介绍:查询事件级别定义类
 */
public class QuerySeverityList extends RestApi{
   public JSONObject querySeverityList(){
	   String url = ":1511/tarsier-vmdb/cmv/monitor/severity/querySeverityList";
	   JSONObject param = new JSONObject();
	   JSONObject cdt = new JSONObject();
	   param.put("cdt", cdt);
	   return doRest(url, param.toString(), "POST");
   }
}
