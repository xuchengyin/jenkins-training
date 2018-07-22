package com.uinnova.test.step_definitions.api.base.license.auth;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
/**
 * 编写时间:2017-11-08
 * 编写人:sunsl
 * 功能介绍:产品授权功能生成客户识别码类 
 */
public class CreateClientCode extends RestApi{
	/**
	 * @return
	 */
	public JSONObject createClientCode(){
		String url = ":1511/tarsier-vmdb/cmv/license/auth/createClientCode";
		return doRest(url, "", "POST");
	}
}
