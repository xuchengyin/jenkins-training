package com.uinnova.test.step_definitions.api.pmv.Metrics;
import org.json.JSONObject;
import com.uinnova.test.step_definitions.api.RestApi;
/**
 * @author kyn
 * 指标服务-查询指标值
 *
 */
public class QueryMetrics extends RestApi{

	public JSONObject queryMetrics(String param) {
		String url = ":1518/pmv-web/metric/queryMetrics";		

			return doRest(url, param, "POST");
		}
	}

