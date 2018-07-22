package com.uinnova.test.step_definitions.api.cmv.ciQualityRule;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.cmv.ciQualityRule.CiQualityRuleUtil;

/**
 * 根据ID查询仪表盘规则
 * @author wsl
 *
 */
public class QueryCiQualityInfoById extends RestApi {
	
	/**
	 * 根据规则和名称查询仪表盘规则
	 * @param ruleTypeName
	 * @param ruleName
	 * @return
	 */
	public JSONObject queryCiQualityInfoById(String ruleTypeName, String ruleName){
		String url=":1511/tarsier-vmdb/cmv/ciQualityRule/queryCiQualityInfoById";
		BigDecimal id = CiQualityRuleUtil.getIDByNameAndRuleType(ruleTypeName, ruleName);
		return this.doRest(url, String.valueOf(id), "POST");
	}

}
