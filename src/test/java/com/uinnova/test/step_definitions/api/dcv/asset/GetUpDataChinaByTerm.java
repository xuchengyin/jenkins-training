package com.uinnova.test.step_definitions.api.dcv.asset;

import java.awt.List;
import java.math.BigDecimal;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class GetUpDataChinaByTerm extends RestApi{
	public JSONObject getUpDataChinaByTerm(ArrayList ids, BigDecimal dcId, BigDecimal scId){
		String url = ":1511/tarsier-vmdb/dcv/asset/getUpDataChainByTerm";
		JSONObject parameter = new JSONObject();
		JSONArray jsonIds = new JSONArray();
		JSONArray endfilters = new JSONArray();
		for(int i = 0; i < ids.size(); i++){
			jsonIds.put(i);
		}
		parameter.put("jsonIds", jsonIds);
		parameter.put("endfilters", endfilters);
		parameter.put("depth", 2);
		parameter.put("dcId", dcId);
		parameter.put("scId", scId);
		
		return doRest(url, parameter.toString(), "POST");
		
	}

}
