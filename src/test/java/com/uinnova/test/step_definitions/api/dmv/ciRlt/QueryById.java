package com.uinnova.test.step_definitions.api.dmv.ciRlt;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * @author wsl
 *
 */
public class QueryById extends RestApi{

	public JSONObject queryById(int rltId){
		String url = ":1511/tarsier-vmdb/dmv/ciRlt/queryById";
		return doRest(url, String.valueOf(rltId), "POST");
	}

}
