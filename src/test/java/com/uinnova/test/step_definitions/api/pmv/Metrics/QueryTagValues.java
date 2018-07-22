package com.uinnova.test.step_definitions.api.pmv.Metrics;

import org.json.JSONArray;
import org.json.JSONObject;
import com.uinnova.test.step_definitions.api.RestApi;

/**
 * @author kyn
 * 	查询指标具体标签值tagVs
 *
 */
public class QueryTagValues extends RestApi{
	public JSONArray queryTagValues(String tag) {
		
		String url = ":1518/pmv-web/metric/queryTagValues";				
		JSONObject param = new JSONObject();		
		JSONArray metrics=new JSONArray();
		JSONArray tagFilters=new JSONArray();
		param.put("tag", tag);
		param.put("metrics", metrics);
		param.put("tagFilters",tagFilters);	
		JSONObject result =   doRest(url, param.toString(), "POST");
		JSONArray tagdata  =  result.getJSONArray("data");
		return tagdata;
		
	}
}
