package com.uinnova.test.step_definitions.api.pmv.Metrics;

import org.json.JSONArray;
import org.json.JSONObject;
import com.uinnova.test.step_definitions.api.RestApi;

/**
 * @author kyn
 * 查询指标标签tags
 *
 */
public class QueryMetricsTags extends RestApi{

	public JSONArray queryMetricsTags(String kpi) {
		String url = ":1518/pmv-web/metric/queryMetricsTags";				
		JSONObject param = new JSONObject();	
		JSONArray metrics=new JSONArray();
		metrics.put(kpi);
		JSONArray tagFilters=new JSONArray();
		param.put("metrics", metrics);
		param.put("tagFilters",tagFilters);
		param.put("db", "");		
		JSONObject result =   doRest(url, param.toString(), "POST");
		JSONArray kpidata  =  result.getJSONArray("data");
		return kpidata;
		
	}
}
	
