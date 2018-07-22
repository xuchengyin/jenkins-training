package com.uinnova.test.step_definitions.api.base.kpi;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-11-02
 * 编写人:sunsl
 * 功能介绍:KPI模型检索类 
 */
public class QueryKpiInfoPage extends RestApi{
	/**
	 * @param searchKey
	 * @return
	 */
	public JSONObject queryKpiInfoPage(String searchKey){
		String url = ":1511/tarsier-vmdb/cmv/kpi/queryKpiInfoPage";
		JSONObject param = new JSONObject();
		JSONObject cdt = new JSONObject();
		if (!searchKey.isEmpty())
			cdt.put("searchValue", searchKey.trim().toUpperCase());
		param.put("pageNum", 1);
		param.put("pageSize", 100);
		param.put("orders", "modify_Time desc");
		param.put("cdt", cdt);
		return doRest(url, param.toString(), "POST");
	}
}
