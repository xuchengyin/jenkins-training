package com.uinnova.test.step_definitions.api.cmv.sys.user;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.api.cmv.integration.GetCurUser;

public class UpdateCurUser extends RestApi{

	public JSONObject updateCurUser(JSONObject obj){
		GetCurUser gcu = new GetCurUser();
		obj.put("id", gcu.getCurUser().getJSONObject("data").getBigDecimal("id"));
		String url = ":1511/tarsier-vmdb/cmv/sys/user/updateCurUser";
		return doRest(url, obj.toString(), "POST");
	}
}
