package com.uinnova.test.step_definitions.api.cmv.ci;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class RemoveHaveAuthByIds extends RestApi{
	/**
	 * 配置查询，批量删除功能(需要权限)。
	 * @author lidw
	 * 
	 * @请求方式  post
	 * */
	public JSONObject removeHaveAuthByIds(JSONObject ids){
		String url = ":1511/tarsier-vmdb/cmv/ci/removeHaveAuthByIds";
		return doRest(url,ids.toString(),"POST");
		
	}
}
