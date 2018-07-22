package com.uinnova.test.step_definitions.api.pmv.kpiValueRlt;

import org.json.JSONObject;
import com.uinnova.test.step_definitions.api.RestApi;

/**
 * @author kyn
 * 分页查询-指标值映射
 *
 */
public class QueryPage extends RestApi{

	public JSONObject queryPage(String kpiclass, String metric) {
		String url = ":1518/pmv-web/kpiValueRlt/queryPage";		
		JSONObject param = new JSONObject();
		JSONObject cdt = new JSONObject();
		cdt.put("kpiClass", kpiclass);
		cdt.put("kpi ", metric);
		param.put("cdt", cdt);
		param.put("orders", "");
		param.put("pageNum", 1);
		param.put("pageSize", 10);
		JSONObject result =   doRest(url, param.toString(), "POST");
		JSONObject kpidata  =  result.getJSONObject("data").getJSONArray("data").getJSONObject(0);
		return kpidata;
		
	}
}