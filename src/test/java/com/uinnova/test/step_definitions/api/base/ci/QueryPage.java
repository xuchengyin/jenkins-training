package com.uinnova.test.step_definitions.api.base.ci;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;

public class QueryPage extends RestApi{


	/**
	 * @param className
	 * @return
	 */
	public JSONObject queryPage(String className){
		CiClassUtil ciclass = new CiClassUtil();
		String url = ":1511/tarsier-vmdb/cmv/ci/queryPage";
		JSONObject param = new JSONObject();
		BigDecimal classId = ciclass.getCiClassId(className);
		JSONObject cdt = new JSONObject();
		cdt.put("classId", classId);
		String[] ciQ = {"ATTR"};
		cdt.put("ciQ", ciQ);
		param.put("orders", "");
		param.put("pageNum", 1);
		param.put("pageSize", 100000);
		param.put("cdt", cdt);
		return doRest(url, param.toString(), "POST");
	}
}
