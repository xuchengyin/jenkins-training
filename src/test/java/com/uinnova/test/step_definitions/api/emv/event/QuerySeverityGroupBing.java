package com.uinnova.test.step_definitions.api.emv.event;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2018-05-10
 * 编写人:sunsl
 * 功能介绍:查询事件级别统计
 */
public class QuerySeverityGroupBing extends RestApi{
   public JSONObject querySeverityGroupBing(){
	   String url =":1516/monitor-web/event/querySeverityGroupBing";
	   return doRest(url,"{}","POST");
   }
}
