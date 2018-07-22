package com.uinnova.test.step_definitions.api.base.kpi;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.KpiUtil;

/**
 * 编写时间:2017-11-02
 * 编写人:sun
 * 功能介绍:根据KpiId删除指标模型类
 * 
 */
public class RemoveKpiById extends RestApi{
	/**
	 * @param kpiCode
	 * @return
	 */
	public JSONObject removeKpiById(String kpiCode){
		String url = ":1511/tarsier-vmdb/cmv/kpi/removeKpiById";
		KpiUtil kpiUtil = new KpiUtil();
		ArrayList kpiList = kpiUtil.getKpi(kpiCode);
		HashMap kpiHashMap = (HashMap) kpiList.get(0);
		String kpiId = kpiHashMap.get("ID").toString();
		return doRest(url, kpiId, "POST");
	}
}
