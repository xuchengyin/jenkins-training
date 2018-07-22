package com.uinnova.test.step_definitions.api.dcv.asset;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class SearchCount extends Search{
	public JSONObject searchCount(){
		String url = ":1511/tarsier-vmdb/dcv/asset/searchCount";
		JSONObject parameter = new JSONObject();
		parameter.put("jsonId", super.jsonId);
		parameter.put("strMap", super.strMap);
		parameter.put("term", super.term);
		parameter.put("sc", super.sc);
		parameter.put("pageSize", 20);
		parameter.put("pageNum", 1);
		
		return doRest(url, parameter.toString(), "POST");
	}

}
