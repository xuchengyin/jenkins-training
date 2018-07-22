package com.uinnova.test.step_definitions.api.pmv.Metrics;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * @author kyn
 * 指标值映射-查询值映射
 *
 */
public class QueryKPIValueRltStr extends RestApi{

	public JSONObject queryKPIValueRltStr(String kpiclass, String metric) {
		String url = ":1518/pmv-web/metric/queryKPIValueRltStr";		
		JSONObject param = new JSONObject();
		param.put("kpiClass ",kpiclass);
		param.put("kpi ", metric);
		JSONObject result =   doRest(url, param.toString(), "POST");
		JSONObject kpidata  =  result.getJSONObject("data");
		return kpidata;
		
	}
}