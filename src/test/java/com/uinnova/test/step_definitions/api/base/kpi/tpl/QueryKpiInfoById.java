package com.uinnova.test.step_definitions.api.base.kpi.tpl;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-11-09
 * 编写人:sunsl
 * 功能介绍:根据ID取得指标模板
 */
public class QueryKpiInfoById extends RestApi{
	/**
	 * @param kpiId
	 * @return
	 */
	public JSONObject queryKpiInfoById(String kpiId){
		String url= ":1511/tarsier-vmdb/cmv/kpi/tpl/queryKpiInfoById";
		return doRest(url, kpiId, "POST");
	}
}
