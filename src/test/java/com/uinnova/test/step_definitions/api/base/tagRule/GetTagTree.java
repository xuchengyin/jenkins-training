package com.uinnova.test.step_definitions.api.base.tagRule;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class GetTagTree extends RestApi{

	/**
	 * @param tagName
	 * @return
	 */
	public JSONObject getTagTree(String tagName){
		String url = ":1511/tarsier-vmdb/cmv/tagRule/getTagTree";
		JSONObject param = new JSONObject();
		if (!tagName.isEmpty())
			param.put("tagName", tagName);
		return doRest(url, param.toString(), "POST");
	}
}
