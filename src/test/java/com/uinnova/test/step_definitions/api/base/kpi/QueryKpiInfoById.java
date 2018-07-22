package com.uinnova.test.step_definitions.api.base.kpi;

import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.KpiUtil;

/**
 *编写时间: 2017-11-10
 *编写人:sunsl
 *功能介绍:指标模型根据ID取信息类
 */
public class QueryKpiInfoById extends RestApi{
	public JSONObject queryKpiInfoById(String kpiCode){
		String url=":1511/tarsier-vmdb/cmv/kpi/queryKpiInfoById";
		KpiUtil kpiUtil = new KpiUtil();
		List kpiList = kpiUtil.getKpi(kpiCode);
		String kpiId = ((HashMap)kpiList.get(0)).get("ID").toString();
		return doRest(url, kpiId, "POST");
	}
}
