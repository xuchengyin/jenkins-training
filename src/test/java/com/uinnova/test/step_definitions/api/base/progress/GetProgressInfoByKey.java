package com.uinnova.test.step_definitions.api.base.progress;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 通过key获取进度
 * @author wsl
 *
 */
public class GetProgressInfoByKey extends RestApi{
	
	public JSONObject getProgressInfoByKey(String key){
		String url =":1511/tarsier-vmdb/cmv/progress/getProgressInfoByKey";
		return this.doRest(url, key, "POST");
	}

}
