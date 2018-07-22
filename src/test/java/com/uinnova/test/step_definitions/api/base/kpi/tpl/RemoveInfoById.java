package com.uinnova.test.step_definitions.api.base.kpi.tpl;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-11-10
 * 编写人:sunsl
 * 功能介绍:指标模板删除类
 */
public class RemoveInfoById extends RestApi{
	/**
	 * @param kpiTplId
	 * @return
	 */
	public JSONObject removeInfoById(String kpiTplId){
		String url =":1511/tarsier-vmdb/cmv/kpi/tpl/removeInfoById";
		return doRest(url, kpiTplId, "POST");
	}
}
