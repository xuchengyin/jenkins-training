package com.uinnova.test.step_definitions.api.base.xi.iface;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-11-08
 * 编写人:sunsl
 * 功能介绍:数据集成的搜索类
 */
public class QueryList extends RestApi{
	/**
	 * @param ifaceName
	 * @return
	 */
	public JSONObject queryList(String ifaceName){
		String url = ":1511/tarsier-vmdb/cmv/xi/iface/queryList";
		JSONObject param = new JSONObject();
		param.put("ifaceName", ifaceName);
		return doRest(url, param.toString(), "POST");
	}

}
