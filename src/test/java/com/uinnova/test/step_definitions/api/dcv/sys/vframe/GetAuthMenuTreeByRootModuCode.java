package com.uinnova.test.step_definitions.api.dcv.sys.vframe;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class GetAuthMenuTreeByRootModuCode extends RestApi{
	public JSONObject getAuthMenuTreeByRootModuCode(){
		String url = ":1511/tarsier-vmdb/dcv/sys/vframe/getAuthMenuTreeByRootModuCode";
		JSONObject parameter = new JSONObject();
		parameter.put("rootCode", "07");
		return doRest(url, parameter.toString(), "POST");
	}

}
