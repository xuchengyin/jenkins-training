package com.uinnova.test.step_definitions.api.base.kpi.tpl;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiUtil;

/**
 * 根据CI-ID数组查询CI对应的指标模板和指标信息
 * @author wsl
 *
 */
public class QueryKpiTplInfoByCiIds extends RestApi{


	/**
	 * 根据CI-ID数组查询CI对应的指标模板和指标信息
	 * @param ciIds
	 * @return
	 */
	public JSONObject queryKpiTplInfoByCiIds(JSONArray ciIds) {
		String url = ":1511/tarsier-vmdb/cmv/kpi/tpl/queryKpiTplInfoByCiIds";
		JSONObject param = new JSONObject();
		param.put("ciIds", ciIds);
		return doRest(url, param.toString(), "POST");
	}

	/**
	 * 根据CI-CODE数组查询CI对应的指标模板和指标信息
	 * @param classNames
	 * @return
	 */
	public JSONObject queryKpiTplInfoByCiCodes(JSONArray ciCodes) {
		String url = ":1511/tarsier-vmdb/cmv/kpi/tpl/queryKpiTplByClassIds";
		JSONArray ciIds = new JSONArray();
		CiUtil ciUtil= new CiUtil();
		for (int i =0; i<ciCodes.length(); i++){
			String ciCode = ciCodes.getString(i);
			ciIds.put(ciUtil.getCiId(ciCode));
		}
		return queryKpiTplInfoByCiIds(ciIds);
	}

}
