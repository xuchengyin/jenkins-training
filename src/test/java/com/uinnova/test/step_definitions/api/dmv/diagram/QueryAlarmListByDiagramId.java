package com.uinnova.test.step_definitions.api.dmv.diagram;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.RltClassUtil;

/**
 * 编写时间:2017-12-11
 * 编写人:sunsl
 * 功能介绍:我的查看视图查询告警类
 */
public class QueryAlarmListByDiagramId extends RestApi{
	public JSONObject queryAlarmListByDiagramId(String rltClassName,String ciCode){
		JSONObject param = new JSONObject();
		String url = ":1511/tarsier-vmdb/dmv/diagram/queryAlarmListByDiagramId";
		JSONArray rltIds = new JSONArray();
		RltClassUtil rltClassUtil = new RltClassUtil();
		JSONArray ciCodes = new JSONArray();
		ciCodes.put(ciCode);
		rltIds.put(rltClassUtil.getRltClassId(rltClassName));
		param.put("rltIds", rltIds);
		param.put("ciCodes", ciCodes);
		return doRest(url, param.toString(), "POST");
	}
}
