package com.uinnova.test.step_definitions.api.cmv.ciclassForm;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class QueryByClassId extends RestApi{
	public JSONObject queryByClassId(String classId){
		String url = ":1511/tarsier-vmdb/cmv/ciclassForm/queryByClassId";
		return doRest(url,classId,"POST");
	}
}
