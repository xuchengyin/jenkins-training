package com.uinnova.test.step_definitions.api.cmv.monitor.performance;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
/*
 * 导入性能查询CI-CODE是否挂有KPI，1-有，0-无
 * 
 * */
public class GetKpiByCiCode extends RestApi{

	public JSONObject getKpiByCiCode(String ciCode){
		String url = ":1511/tarsier-vmdb/cmv/monitor/performance/getKpiByCiCode";
		
		return doRest(url, ciCode, "POST");
		
	}
}
