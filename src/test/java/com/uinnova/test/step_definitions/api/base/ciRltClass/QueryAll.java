package com.uinnova.test.step_definitions.api.base.ciRltClass;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class QueryAll extends RestApi{

	/**
	 * @return
	 */
	public JSONObject queryAll(){
		String url = ":1511/tarsier-vmdb/cmv/ciRltClass/queryAll";
		return doRest(url, "", "POST");
	}
}
