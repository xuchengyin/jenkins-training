package com.uinnova.test.step_definitions.api.cmv.ciQualityRule;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.cmv.ciQualityRule.CiQualityRuleUtil;

/**
 * 激活或取消激活规则
 * @author wsl
 *
 */
public class QueryCiQualityChartDataInfoByRuleId extends RestApi {
	
	/**
	 * @param ruleTypeName
	 * @param ruleName
	 * @return
	 */
	public JSONObject queryCiQualityChartDataInfoByRuleId(String ruleTypeName, String ruleName){
		String url = ":1511/tarsier-vmdb/cmv/ciQualityRule/queryCiQualityChartDataInfoByRuleId";
		JSONObject param = new JSONObject();
		param.put("ruleId", CiQualityRuleUtil.getIDByNameAndRuleType(ruleTypeName, ruleName));
		return doRest(url, param.toString(), "POST");
	}

}
