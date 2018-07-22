package com.uinnova.test.step_definitions.api.cmv.ciRlt;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class QueryCount extends RestApi{

	public JSONObject queryCount(BigDecimal sourceCiId,ArrayList<BigDecimal> targetClassId){
		String url = ":1511/tarsier-vmdb/cmv/ciRlt/queryCount";
		JSONArray param = new JSONArray();
		for(int i = 0; i < targetClassId.size(); i++){
			JSONObject temp = new JSONObject();
			temp.put("sourceCiId", sourceCiId);
			temp.put("targetClassId", targetClassId.get(i));
			param.put(temp);
		}
		return doRest(url, param.toString(), "POST");		
	}
}
