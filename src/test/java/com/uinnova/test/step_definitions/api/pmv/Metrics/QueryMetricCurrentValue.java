package com.uinnova.test.step_definitions.api.pmv.Metrics;
import org.json.JSONObject;
import com.uinnova.test.step_definitions.api.RestApi;
/**
 * @author kyn
 * 指标服务-查询当前指标值
 *
 */
public class QueryMetricCurrentValue extends RestApi{

	public JSONObject queryMetricCurrentValue(String metric) {
		String url = ":1518/pmv-web/metric/queryMetricCurrentValue";						
			JSONObject param = new JSONObject();
			param.put("metrics ",metric);
			JSONObject result =   doRest(url, param.toString(), "POST");			
			return result;	
		}
	}

