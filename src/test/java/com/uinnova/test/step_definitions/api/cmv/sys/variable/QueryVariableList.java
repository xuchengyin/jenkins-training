package com.uinnova.test.step_definitions.api.cmv.sys.variable;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class QueryVariableList extends RestApi{

	public JSONObject queryVariableList(){
		String url = ":1511/tarsier-vmdb/cmv/sys/variable/queryVariableList";
		JSONObject obj = new JSONObject();
		return doRest(url, obj.toString(), "POST");
	}
}
