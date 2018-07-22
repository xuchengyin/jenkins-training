package com.uinnova.test.step_definitions.api.dmv.diagram;

import java.math.BigDecimal;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-11-15
 * 编写人:sunsl
 * 功能介绍:查询视图的告警
 */
public class QueryDigramEventByIds extends RestApi{
   public JSONObject queryDigramEventByIds(JSONObject searchNameResult){
	   String url = ":1511/tarsier-vmdb/dmv/diagram/queryDiagramEventByIds";
	   JSONObject param = new JSONObject();
	   JSONObject data = searchNameResult.getJSONObject("data");
		JSONArray dataArray = data.getJSONArray("data");
		JSONArray ids = new JSONArray();
		if (dataArray.length() > 0) {
			for (int i = 0; i < dataArray.length(); i++) {
				JSONObject dataObj = (JSONObject) dataArray.get(i);
				JSONObject diagram = dataObj.getJSONObject("diagram");
				BigDecimal id = diagram.getBigDecimal("id");
				ids.put(id);
			}
			
		}
	   param.put("ids", ids);
	   return doRest(url, param.toString(), "POST");
   }
}
