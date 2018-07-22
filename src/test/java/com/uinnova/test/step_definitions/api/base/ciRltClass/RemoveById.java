package com.uinnova.test.step_definitions.api.base.ciRltClass;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.api.base.ciRlt.RemoveByClassId;
import com.uinnova.test.step_definitions.utils.base.RltClassUtil;
import com.uinnova.test.step_definitions.utils.common.TxtUtil;

public class RemoveById extends RestApi{

	/**
	 * @param rltClassName
	 * @return
	 */
	public JSONObject removeById(String rltClassName){
		String url = ":1511/tarsier-vmdb/cmv/ciRltClass/removeById";
		if(rltClassName.indexOf(".txt")>0){
			String filePath = RemoveById.class.getResource("/").getPath()+"testData/rlt/"+rltClassName;
			rltClassName = (new TxtUtil()).readTxt(filePath);
		}
		BigDecimal  rltClassId  =  (new RltClassUtil()).getRltClassId(rltClassName);
		JSONObject result = (new RemoveByClassId()).removeByClassId(rltClassName);
		return  doRest(url, String.valueOf(rltClassId), "POST");

	}
}
