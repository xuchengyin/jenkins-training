package com.uinnova.test.step_definitions.api.cmv.ciQualityRule;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.cmv.ciQualityRule.CiQualityRuleUtil;

/**
 * 通过规则ID查询规则最后一次计算结果
 * @author wsl
 *
 */
public class QueryLastSumByRuleId extends RestApi {
	
	/**
	 * 通过规则ID查询规则最后一次计算结果
	 * @param ruleTypeName
	 * @param ruleName
	 * @return
	 */
	public JSONObject queryLastSumByRuleId(String ruleTypeName, String ruleName){
		String url = ":1511/tarsier-vmdb/cmv/ciQualityRule/queryLastSumByRuleId";
		return doRest(url,String.valueOf(CiQualityRuleUtil.getIDByNameAndRuleType(ruleTypeName, ruleName)), "POST");
	}

}
