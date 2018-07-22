package com.uinnova.test.step_definitions.api.base.ciClass;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * @author wsl
 * ciClass queryAll
 *
 */
public class QueryAll extends RestApi{

	/**
	 * @return
	 */
	public JSONObject queryAll(){
		String url = ":1511/tarsier-vmdb/cmv/ciClass/queryAll";
		return doRest(url, "", "POST");
	}
}
