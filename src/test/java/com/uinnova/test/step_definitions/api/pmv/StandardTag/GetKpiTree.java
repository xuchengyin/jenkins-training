package com.uinnova.test.step_definitions.api.pmv.StandardTag;

import org.json.JSONObject;
import com.uinnova.test.step_definitions.api.RestApi;

/**
 * @author kyn
 * 指标标签树
 *
 */
public class GetKpiTree extends RestApi{

	public JSONObject getKpiTree(String kpi) {
		String url = ":1518/pmv-web/sTag/getKpiTree";									
		JSONObject param = new JSONObject();
		if (!kpi.isEmpty()){
			 param.put("kpi", kpi);
		}
			JSONObject result =   doRest(url, param.toString(), "POST");
		
			return result;
			
		}
		
	}
