package com.uinnova.test.step_definitions.api.dmv.ciRlt;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2018-05-23
 * 编写人:sunsl
 * 功能介绍:Emv影响分析接口 
 */
public class QueryRcaByCiIds extends RestApi{
  public JSONObject queryRcaByCiIds(JSONArray ciIds){
	  String url = ":1511/tarsier-vmdb/dmv/ciRlt/queryRcaByCiIds";
	  JSONObject param = new JSONObject();
	  param.put("ciIds", ciIds);
	  return doRest(url,param.toString(),"POST");
  }
}
