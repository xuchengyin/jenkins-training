package com.uinnova.test.step_definitions.api.pmv.Metrics;

import org.json.JSONArray;
import org.json.JSONObject;
import com.uinnova.test.step_definitions.api.RestApi;

/**
 * @author kyn
 * 查询指标标签&值（返回tagName:v,tagName:v格式）
 *
 */
public class QueryMetricNames extends RestApi{
	public JSONObject queryMetricNames() {
		
		String url = ":1518/pmv-web/metric/queryMetricNames";					
		JSONObject result =   doRest(url, "", "POST");
		return result;
		
	}
}
