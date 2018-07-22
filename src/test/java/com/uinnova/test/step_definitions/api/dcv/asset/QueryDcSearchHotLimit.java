package com.uinnova.test.step_definitions.api.dcv.asset;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class QueryDcSearchHotLimit extends RestApi{
	public JSONObject queryDcSearchHotLimit(BigDecimal dcId){
		String url = ":/tarsier-vmdb/dcv/asset/queryDcSearchHotLimit";
		JSONObject parameter = new JSONObject();
		parameter.put("condition", "");
		parameter.put("dcId", dcId);
		return doRest(url, parameter.toString(), "POST");
	}
	
	public JSONObject queryDcSearchHotLimit(String condition, BigDecimal dcId){
		String url = ":/tarsier-vmdb/dcv/asset/queryDcSearchHotLimit";
		JSONObject parameter = new JSONObject();
		parameter.put("condition", condition);
		parameter.put("dcId", dcId);
		return doRest(url, parameter.toString(), "POST");
	}

}
