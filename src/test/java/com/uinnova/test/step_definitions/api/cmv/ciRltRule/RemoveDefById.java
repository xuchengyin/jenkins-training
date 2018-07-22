package com.uinnova.test.step_definitions.api.cmv.ciRltRule;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.cmv.CiRltRuleUtil;

public class RemoveDefById extends RestApi{
	/**
	 * @param defName
	 * @return
	 */
	public JSONObject removeDefById(String defName){
		BigDecimal defId = CiRltRuleUtil.getDefIdByName(defName);
		String url = ":1511/tarsier-vmdb/cmv/ciRltRule/removeDefById";
		return doRest(url, String.valueOf(defId), "POST");
	}
}
