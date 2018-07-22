package com.uinnova.test.step_definitions.api.dcv.dataCheck;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class QueryDcCiCheckDataInfoPage extends RestApi{
	
	public JSONObject queryDcCiCheckDataInfoPage(String code) {
		String url = ":1511/tarsier-vmdb/dcv/dataCheck/queryDcCiCheckDataInfoPage";
		JSONObject parameter = new JSONObject();
		JSONObject cdt = new JSONObject();
		
		cdt.put("code", code);
		parameter.put("cdt", cdt);
		parameter.put("pageNum", 1);
		parameter.put("pageSize", 10);
		parameter.put("orders", "");
		
		return doRest(url, parameter.toString(), "POST");
	}
}
