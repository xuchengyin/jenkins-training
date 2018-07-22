package com.uinnova.test.step_definitions.api.cmv.monitor.event;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2018-01-30
 * 编写人:sunsl
 * 功能介绍:查询CI故障类
 */
public class QueryEventSimple extends RestApi{
   public JSONObject queryEventSimple(List<String> codeList){
	   String url = ":1511/tarsier-vmdb/cmv/monitor/event/queryEventSimple";
	   JSONArray ciCodes = new JSONArray();
	   for(int i = 0; i < codeList.size(); i ++){
	       ciCodes.put(codeList.get(i));
	   }
	   JSONObject param = new JSONObject();
	   param.put("ciCodes", ciCodes);
	   return doRest(url,param.toString(),"POST");
   }
}
