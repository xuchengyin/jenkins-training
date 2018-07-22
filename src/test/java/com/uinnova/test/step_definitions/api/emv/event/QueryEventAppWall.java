package com.uinnova.test.step_definitions.api.emv.event;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2018-05-10 
 * 编写人:sunsl
 * 功能介绍:查询应用告警墙
 */
public class QueryEventAppWall extends RestApi {
   public JSONObject queryEventAppWall(BigDecimal startTime,BigDecimal endTime){
	   String url = ":1516/monitor-web/event/queryEventAppWall";
	   JSONObject param = new JSONObject();
	   param.put("startTime", startTime);
	   param.put("endTime", endTime);
	   return doRest(url,param.toString(),"POST");
   }
}
