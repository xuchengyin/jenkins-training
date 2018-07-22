package com.uinnova.test.step_definitions.api.base.ciClass;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class QueryDefaultImage extends RestApi{

	/**
	 * 获取默认图标
	 * @return
	 */
	public JSONObject defaultImageUrl() {
		String url = ":1511/tarsier-vmdb/cmv/image/queryDefaultImage";
		return doRest(url, "", "POST");
	}
}
