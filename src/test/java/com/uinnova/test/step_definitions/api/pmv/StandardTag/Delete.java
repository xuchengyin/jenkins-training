package com.uinnova.test.step_definitions.api.pmv.StandardTag;

import java.math.BigDecimal;

import org.json.JSONArray;
import org.json.JSONObject;
import com.uinnova.test.step_definitions.api.RestApi;

/**
 * @author kyn
 * 标准标签-标签删除
 *
 */
public class Delete extends RestApi{

	public JSONObject delete(BigDecimal Id) {
		String url = ":1518/pmv-web/sTag/delete";		
		JSONObject result =   doRest(url, String.valueOf(Id), "POST");							
		return result;
		
	}
}