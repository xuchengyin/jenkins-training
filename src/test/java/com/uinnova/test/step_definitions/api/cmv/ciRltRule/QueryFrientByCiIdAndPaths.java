package com.uinnova.test.step_definitions.api.cmv.ciRltRule;

import java.math.BigDecimal;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.base.CiUtil;

public class QueryFrientByCiIdAndPaths extends RestApi{

	public JSONObject queryFrientByCiIdAndPaths(String startCiCode,String endCiCode,JSONArray ciClassRltPaths){

		String url = ":1511/tarsier-vmdb/cmv/ciRlt/queryFrientByCiIdAndPaths";
		JSONObject parameter = new JSONObject();
		CiUtil cu = new CiUtil();
		CiClassUtil ccu = new CiClassUtil();
		BigDecimal startCiId = cu.getCiId(startCiCode);
		BigDecimal endCiId = cu.getCiId(endCiCode);
		BigDecimal startCiClassId = ccu.getCiClassId(ccu.getCiClassNameByCiCode(startCiCode));
		BigDecimal endCiClassId = ccu.getCiClassId(ccu.getCiClassNameByCiCode(endCiCode));
		parameter.put("startCiId", startCiId);
//		parameter.put("endCiId", endCiId);
		parameter.put("endCiClassId", endCiClassId);
		for(int i = 0; i< ciClassRltPaths.length(); i++){
			JSONObject temp = ciClassRltPaths.getJSONObject(i);
			temp.put("startCiClassId", startCiClassId);
			temp.put("endCiClassId", endCiClassId);
		}
		parameter.put("ciClassRltPaths", ciClassRltPaths);
		return doRest(url, parameter.toString(), "POST");
	}
}
