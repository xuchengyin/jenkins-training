package com.uinnova.test.step_definitions.api.base.ciRltClass;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.RltClassUtil;
import com.uinnova.test.step_definitions.utils.common.TxtUtil;

public class QueryById extends RestApi{

	/**
	 * @param rltClassName
	 * @return
	 */
	public JSONObject queryById(String rltClassName){
		String url = ":1511/tarsier-vmdb/cmv/ciRltClass/queryById";
		if(rltClassName.indexOf(".txt")>0){
			String filePath = QueryById.class.getResource("/").getPath()+"testData/rlt/"+rltClassName;
			rltClassName = (new TxtUtil()).readTxt(filePath);
		}
		BigDecimal rltClsId = (new RltClassUtil()).getRltClassId(rltClassName);
		return doRest(url, String.valueOf(rltClsId), "POST");
	}
}
