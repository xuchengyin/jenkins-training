package com.uinnova.test.step_definitions.api.cmv.monitor.event;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 
 * @author lidw
 * @param String类型列表
 * 
 */
public class QueryEventDetails extends RestApi{
	public JSONObject queryEventDetails(JSONArray ciCodes){
	   String url = ":1511/tarsier-vmdb/cmv/monitor/event/queryEventDetails";
	   JSONObject param = new JSONObject();
	   param.put("ciCodes", ciCodes);
	   return doRest(url,param.toString(),"POST");
	}

}