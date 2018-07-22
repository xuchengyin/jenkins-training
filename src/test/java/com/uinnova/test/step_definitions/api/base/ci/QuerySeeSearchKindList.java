package com.uinnova.test.step_definitions.api.base.ci;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-11-09
 * 编写人:sunsl
 * 功能介绍:搜索分类标签类
 */
public class QuerySeeSearchKindList extends RestApi{

	/**
	 * @return
	 */
	public JSONObject querySeeSearchKindList(){
		String url=":1511/tarsier-vmdb/cmv/search/ci/querySeeSearchKindList";
		return doRest(url, "", "POST");
	}
}
