package com.uinnova.test.step_definitions.api.base.license.auth;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-11-07
 * 编写人:sunsl
 * 功能介绍:产品授权取得信息
 */
public class QueryLicenseAuthInfo extends RestApi{
	/**
	 * @return
	 */
	public JSONObject queryLicenseAuthInfo(){
		String url = ":1511/tarsier-vmdb/cmv/license/auth/queryLicenseAuthInfo";
		return doRest(url, "", "POST");
	}
}
