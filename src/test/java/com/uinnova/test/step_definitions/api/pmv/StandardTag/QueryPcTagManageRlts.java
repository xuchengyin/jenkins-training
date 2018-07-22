package com.uinnova.test.step_definitions.api.pmv.StandardTag;

import org.json.JSONArray;
import org.json.JSONObject;
import com.uinnova.test.step_definitions.api.RestApi;

/**
 * @author kyn
 * 分页查询-指标值映射
 *
 */
public class QueryPcTagManageRlts extends RestApi{

	public JSONArray queryPcTagManageRlts(String param) {
		String url = ":1518/pmv-web/sTag/queryPcTagManageRlts";				
		JSONObject result =   doRest(url, param.toString(), "POST");
		JSONArray kpidata  =  result.getJSONObject("data").getJSONArray("tmRlt");
		return kpidata;
		
	}
}