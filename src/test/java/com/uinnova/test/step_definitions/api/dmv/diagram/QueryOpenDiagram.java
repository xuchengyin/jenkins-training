package com.uinnova.test.step_definitions.api.dmv.diagram;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-11-15
 * 编写人:sunsl
 * 功能介绍:广场的根据用户检索视图类
 */
public class QueryOpenDiagram extends RestApi{
   public JSONObject queryOpenDiagram(JSONObject cdt){
	   String url = ":1511/tarsier-vmdb/dmv/diagram/queryOpenDiagram";
	   JSONObject param = new JSONObject();
	   param.put("cdt", cdt);
	   param.put("pageNum", 1);
	   param.put("pageSize", 20);
	   return doRest(url, param.toString(), "POST");
   }
}
