package com.uinnova.test.step_definitions.api.dmv.theme;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-01-11
 * 编写人:sunsl
 * 功能介绍:DMV查询指标模型类
 */
public class QueryKpiList extends RestApi{
   public JSONObject queryKpiList(){
	   String url=":1511/tarsier-vmdb/dmv/theme/queryKpiList";
	   JSONObject param = new JSONObject();
	   JSONObject cdt = new JSONObject();
	   JSONArray kpiCodes = new JSONArray();
	   kpiCodes.put("W");
	   kpiCodes.put("开关");
	   cdt.put("kpiCodes", kpiCodes);
	   param.put("cdt", cdt);
	   return doRest(url, param.toString(), "POST");
   }
}
