package com.uinnova.test.step_definitions.api.base.monitor.severity;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * @author wsl
 * 不分页查询告警级别定义下拉列表
 *
 */
public class QuerySeverityDropList extends RestApi{
	/**
	 * @return
	 */
	public JSONObject querySeverityDropList(){
		String url = ":1511/tarsier-vmdb/cmv/monitor/severity/querySeverityDropList";
		return  doRest(url, "{}", "POST");
	}
}
