package com.uinnova.test.step_definitions.api.dmv.ciRlt;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2018-05-23
 * 编写人:sunsl
 * 功能介绍:查询路由关系 
 */
public class QueryRltInfoByRelatedCiCode extends RestApi{
   public JSONObject queryRltInfoByRelatedCiCode(String ciCode){
	   String url =":1511/tarsier-vmdb/dmv/ciRlt/queryRltInfoByRelatedCiCode";
	   JSONObject param = new JSONObject();
	   JSONArray ciQ = new JSONArray();
	   ciQ.put("ATTR");
	   param.put("ciCode", ciCode);
	   param.put("ciQ", ciQ);
	   return doRest(url,param.toString(),"POST");
   }
}
