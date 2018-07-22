package com.uinnova.test.step_definitions.api.base.kpi.tpl;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-11-13
 * 编写人:sunsl
 * 功能介绍:指标模板的搜索类
 */
public class QueryKpiTplInfoPage extends RestApi{

	/**
	 * @param searchKey
	 * @return
	 */
	public JSONObject queryKpiTplInfoPage(String searchKey){
		String url=":1511/tarsier-vmdb/cmv/kpi/tpl/queryKpiTplInfoPage";
		JSONObject cdt = new JSONObject();
		JSONObject param = new JSONObject();
		String searchField = "%" + searchKey.trim().toUpperCase() + "%";
		cdt.put("searchField", searchField);
		param.put("cdt", cdt);
		param.put("orders", "MODIFY_TIME DESC");
		param.put("pageNum", 1);
		param.put("pageSize", 30);
		return doRest(url, param.toString(), "POST");
	}
}
