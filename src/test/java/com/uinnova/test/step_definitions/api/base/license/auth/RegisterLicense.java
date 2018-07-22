package com.uinnova.test.step_definitions.api.base.license.auth;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-11-08
 * 编写人:sunsl
 * 编写功能:产品授权功能注册功能类
 */
public class RegisterLicense extends RestApi{
	/**
	 * @param authCode
	 * @return
	 */
	public JSONObject registerLicense(String authCode){
		String url = ":1511/tarsier-vmdb/cmv/license/auth/registerLicense";
		return doRest(url, authCode, "POST");
	}
}
