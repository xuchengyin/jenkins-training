package com.uinnova.test.step_definitions.api.cmv.monitor.performance;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2018-03-12
 * 编写人:sunsl
 * 功能介绍:CMV配置查询获得CI性能类
 */
public class GetPerformanceList extends RestApi{
   public JSONObject getPerformanceList(String ciCode){
	   String url = ":1511/tarsier-vmdb/cmv/monitor/performance/getPerformanceList";
	   JSONObject param = new JSONObject();
	   param.put("ciCode", ciCode);
	   return doRest(url,param.toString(),"POST");
   }
}
