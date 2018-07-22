package com.uinnova.test.step_definitions.api.cmv.ciQualityRule;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.cmv.ciQualityRule.CiQualityRuleUtil;

/**
 * 根据ID删除仪表盘规则
 * @author wsl
 *
 */
public class RemoveRuleById extends RestApi {
	
	/**
	 * @param ruleTypeName
	 * @param ruleName
	 * @return
	 */
	public JSONObject removeRuleById(String ruleTypeName, String ruleName){
		String url = ":1511/tarsier-vmdb/cmv/ciQualityRule/removeRuleById";
		BigDecimal ruleId = CiQualityRuleUtil.getIDByNameAndRuleType(ruleTypeName, ruleName);
		return this.doRest(url, String.valueOf(ruleId), "POST");
	}

}
