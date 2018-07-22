package com.uinnova.test.step_definitions.api.base.ciRlt;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.RltClassUtil;

public class RemoveByClassId extends RestApi{

	/**
	 * @param rltClsName
	 * @return
	 */
	public JSONObject removeByClassId(String rltClsName){
		String url = ":1511/tarsier-vmdb/cmv/ciRlt/removeByClassId";
		BigDecimal rltClsId = (new RltClassUtil()).getRltClassId(rltClsName);
		return doRest(url, String.valueOf(rltClsId), "POST");
	}
}
