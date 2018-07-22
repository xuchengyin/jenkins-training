package com.uinnova.test.step_definitions.api.base.kpi;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-11-09
 * 编写人:sunsl
 * 功能介绍:搜索指标模型类
 */
public class QueryKpiList extends RestApi{
	/**
	 * @return
	 */
	public JSONObject queryKpiList(){
		String url = ":1511/tarsier-vmdb/cmv/kpi/queryKpiList";
		return doRest(url, "{}", "POST");
	}
}