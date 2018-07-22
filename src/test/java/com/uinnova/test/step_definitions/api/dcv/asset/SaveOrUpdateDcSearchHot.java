package com.uinnova.test.step_definitions.api.dcv.asset;

import org.json.JSONObject;

public class SaveOrUpdateDcSearchHot extends Search{
	public JSONObject saveOrUpdateDcSearchHot(){
		String url = ":1511/tarsier-vmdb/dcv/asset/saveOrUpdateDcSearchHot";
		JSONObject parameter = new JSONObject();
		parameter.put("condition", super.term);
		parameter.put("dcId", sc);
		
		return doRest(url, parameter.toString(), "POST");
		
	}

}
