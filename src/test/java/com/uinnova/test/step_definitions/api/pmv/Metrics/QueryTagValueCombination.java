package com.uinnova.test.step_definitions.api.pmv.Metrics;

import org.json.JSONArray;
import org.json.JSONObject;
import com.uinnova.test.step_definitions.api.RestApi;

/**
 * @author kyn
 * 查询指标标签&值（返回tagName:v,tagName:v格式）
 *
 */
public class QueryTagValueCombination extends RestApi{
	public JSONArray queryTagValueCombination(String kpi) {
		String url = ":1518/pmv-web/metric/queryTagValueCombination";				
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
