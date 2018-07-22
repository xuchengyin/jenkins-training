package com.uinnova.test.step_definitions.api.cmv.ci;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class RemoveByIds extends RestApi{
	

	/**
	 * 配置查询，批量删除功能。
	 * @author lidw
	 * 
	 * @请求方式  post
	 * */
	public JSONObject removeByIds(JSONObject ids){
		String url = ":1511/tarsier-vmdb/cmv/ci/removeByIds";
		return doRest(url,ids.toString(),"POST");
		
	}

}
