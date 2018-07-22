package com.uinnova.test.step_definitions.api.dmv.png2mxgraph;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class GetAiProjectRoot extends RestApi{
	public JSONObject getAiProjectRoot(){
		String url =":1511/tarsier-vmdb/dmv/png2mxgraph/getAiProjectRoot";
		return doRest(url, "{}", "POST");
	}

}
