package com.uinnova.test.step_definitions.api.base.ciClass;


import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;

public class QueryById extends RestApi{

	/**
	 * @param className
	 * @return
	 */
	public JSONObject queryById(String className) {
		String url = ":1511/tarsier-vmdb/cmv/ciClass/queryById";
		BigDecimal classId = (new CiClassUtil()).getCiClassId(className);
		return doRest(url, String.valueOf(classId), "POST");
	}
}
