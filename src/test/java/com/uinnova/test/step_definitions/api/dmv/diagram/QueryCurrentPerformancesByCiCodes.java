package com.uinnova.test.step_definitions.api.dmv.diagram;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-12-11
 * 编写人:sunsl
 * 功能介绍:根据CICode查询性能
 */
public class QueryCurrentPerformancesByCiCodes extends RestApi{
    public JSONObject queryCurrentPerformancesByCiCodes(String ciCode){
    	String url =":1511/tarsier-vmdb/dmv/diagram/queryCurrentPerformancesByCiCodes";
		JSONObject param = new JSONObject();
		JSONArray ciCodes = new JSONArray();
		ciCodes.put(ciCode);
		param.put("ciCodes", ciCodes);
    	return doRest(url, param.toString(), "POST");
    }
}
