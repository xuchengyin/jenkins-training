package com.uinnova.test.step_definitions.api.pmv.Rule;

import org.json.JSONObject;
import com.uinnova.test.step_definitions.api.RestApi;

/**
 * @author kyn
 * 策略count
 *
 */
public class QueryCountByCdt extends RestApi{
	public int queryCountByCdt(String kpi) {
		JSONObject param = new JSONObject();
		String url = ":1518/pmv-web/rule/queryCountByCdt";
		if (!kpi.isEmpty()) {
			param.put("metric", kpi);
		}
		int result = doRest(url, param.toString(), "POST").getInt("data");
		return result;

	}
}
