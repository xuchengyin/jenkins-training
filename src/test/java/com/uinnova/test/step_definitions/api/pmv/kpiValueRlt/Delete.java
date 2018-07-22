package com.uinnova.test.step_definitions.api.pmv.kpiValueRlt;

import java.math.BigDecimal;

import org.json.JSONArray;
import org.json.JSONObject;
import com.uinnova.test.step_definitions.api.RestApi;

/**
 * @author kyn
 * 指标值映射-删除指标
 *
 */
public class Delete extends RestApi{

	public JSONObject delete(BigDecimal Id) {
		String url = ":1518/pmv-web/kpiValueRlt/delete";		
		JSONObject result =   doRest(url, String.valueOf(Id), "POST");							
		return result;
		
	}
}