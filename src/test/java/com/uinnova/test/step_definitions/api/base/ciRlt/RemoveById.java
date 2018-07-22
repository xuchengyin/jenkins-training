package com.uinnova.test.step_definitions.api.base.ciRlt;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class RemoveById extends RestApi{

	/**
	 * @param ciRltId
	 * @return
	 */
	public JSONObject removeById(BigDecimal ciRltId){
		String url = ":1511/tarsier-vmdb/cmv/ciRlt/removeById";
		return doRest(url, String.valueOf(ciRltId), "POST");
	}
	
	
}
