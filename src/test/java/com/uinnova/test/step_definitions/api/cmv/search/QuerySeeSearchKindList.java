package com.uinnova.test.step_definitions.api.cmv.search;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class QuerySeeSearchKindList extends RestApi{

	/**
	 * @return
	 */
	public JSONObject querySeeSearchKindList(String keyWord){
		String url = ":1511/tarsier-vmdb/cmv/search/ci/querySeeSearchKindList";
		return doRest(url, keyWord, "POST");
	}
}
