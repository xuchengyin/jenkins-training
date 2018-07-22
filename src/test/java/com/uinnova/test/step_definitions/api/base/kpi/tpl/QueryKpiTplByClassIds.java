package com.uinnova.test.step_definitions.api.base.kpi.tpl;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;

/**
 * 根据CI分类查询挂载指标模板
 * @author wsl
 *
 */
public class QueryKpiTplByClassIds extends RestApi{


	/**
	 * 根据CI分类查询挂载指标模板
	 * @param classNames
	 * @return
	 */
	public JSONObject queryKpiTplByClassIds(JSONArray classNames) {
		String url = ":1511/tarsier-vmdb/cmv/kpi/tpl/queryKpiTplByClassIds";
		JSONObject param = new JSONObject();
		JSONArray arr = new JSONArray();
		CiClassUtil ciClassUtil= new CiClassUtil();
		for (int i =0; i<classNames.length(); i++){
			String ciClassName = classNames.getString(i);
			arr.put(String.valueOf(ciClassUtil.getCiClassId(ciClassName)));
		}
		param.put("classIds",arr);
		return doRest(url, param.toString(), "POST");
	}

}
