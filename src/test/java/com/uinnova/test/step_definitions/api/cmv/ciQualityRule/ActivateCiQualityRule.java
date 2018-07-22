package com.uinnova.test.step_definitions.api.cmv.ciQualityRule;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.cmv.ciQualityRule.CiQualityRuleUtil;

/**
 * 保存仪表盘规则
 * @author wsl
 *
 */
public class ActivateCiQualityRule extends RestApi {
	
	/**
	 * 根据规则类型和名称激活仪表盘规则
	 * @param ruleTypeName
	 * @param ruleName
	 * @param status
	 * @return
	 */
	public JSONObject activateCiQualityRule(String ruleTypeName, String ruleName, int status){
		String url = ":1511/tarsier-vmdb/cmv/ciQualityRule/activateCiQualityRule";
		BigDecimal ruleId = CiQualityRuleUtil.getIDByNameAndRuleType(ruleTypeName, ruleName);
		JSONObject param = new JSONObject();
		param.put("id", ruleId);
		param.put("status", status);
		return doRest(url, param.toString(), "POST");
	}
}
