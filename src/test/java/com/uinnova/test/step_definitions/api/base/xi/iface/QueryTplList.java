package com.uinnova.test.step_definitions.api.base.xi.iface;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-11-08
 * 编写人:sunsl
 * 功能介绍:数据集成功能的搜索类
 */
public class QueryTplList extends RestApi{
	/**
	 * @return
	 */
	public JSONObject queryTplList(){
		String url = ":1511/tarsier-vmdb/cmv/xi/iface/queryTplList";
		JSONObject param = new JSONObject();
		JSONObject cdt = new JSONObject();
		param.put("cdt", cdt);
		return doRest(url, param.toString(), "POST");
	}
}
