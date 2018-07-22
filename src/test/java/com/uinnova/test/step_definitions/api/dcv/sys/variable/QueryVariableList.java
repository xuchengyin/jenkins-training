package com.uinnova.test.step_definitions.api.dcv.sys.variable;

import org.json.JSONObject;
import org.relaxng.datatype.helpers.StreamingValidatorImpl;

import com.uinnova.test.step_definitions.api.RestApi;

public class QueryVariableList extends RestApi{
	public JSONObject queryVariableList() {
		String url = ":1511/tarsier-vmdb/dcv/sys/variable/queryVariableList";
		return doRest(url, null, "GET");
		
	}
}
