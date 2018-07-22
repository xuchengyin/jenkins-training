package com.uinnova.test.step_definitions.api.cmv.kpi;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.KpiUtil;

public class QueryKpiInfoById extends RestApi{
	public JSONObject queryKpiInfoById(String kpiCode){
		String url =":1511/tarsier-vmdb/cmv/kpi/queryKpiInfoById";
		JSONObject param = new JSONObject();
		KpiUtil ku = new KpiUtil();
		param.put("id", ku.getKpiIdByKpiCode(kpiCode));
		return doRest(url, param.toString(), "POST");
	}
}
