package com.uinnova.test.step_definitions.api.cmv.ciclassForm;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class SaveOrUpdate extends RestApi{
	public JSONObject saveOrUpdate(JSONObject jsonObject){
		String url = ":1511/tarsier-vmdb/cmv/ciclassForm/saveOrUpdate";
		return doRest(url,jsonObject.toString(),"POST");
	}
}
