package com.uinnova.test.step_definitions.api.cmv.ciQualityRule;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.cmv.ciQualityRule.CiQualityRuleUtil;

/**
 * 保存或更新CI质量描述信息
 * @author wsl
 */
public class SaveOrUpdate extends RestApi {

	/**
	 * 保存仪表盘规则
	 * @param ruleTypeName
	 * @param ruleName
	 * @param ruleDesc
	 * @return
	 */
	public JSONObject saveOrUpdate(String ruleTypeName, String ruleName, String ruleDesc){
		int ruleType = CiQualityRuleUtil.getRuleType(ruleTypeName);
		int ruleSubType = CiQualityRuleUtil.getRuleSubType(ruleTypeName);
		String url = ":1511/tarsier-vmdb/cmv/ciQualityRule/saveOrUpdate";
		JSONObject param = new JSONObject();
		param.put("ruleName", ruleName);
		param.put("ruleDesc", ruleDesc);
		param.put("ruleType", ruleType);
		param.put("ruleSubType", String.valueOf(ruleSubType));
		String s=null;
		param.put("tagId", s);
		param.put("status", 0);
		return doRest(url, param.toString(), "POST");
	}

	/**
	 * 再次用相同的名字新建同样类型的规则， 失败场景使用
	 * @param ruleTypeName
	 * @param ruleName
	 * @param ruleDesc
	 * @return
	 */
	public JSONObject saveOrUpdateAgain(String ruleTypeName, String ruleName, String ruleDesc, String kw){
		int ruleType = CiQualityRuleUtil.getRuleType(ruleTypeName);
		int ruleSubType = CiQualityRuleUtil.getRuleSubType(ruleTypeName);
		String url = ":1511/tarsier-vmdb/cmv/ciQualityRule/saveOrUpdate";
		JSONObject param = new JSONObject();
		param.put("ruleName", ruleName);
		param.put("ruleDesc", ruleDesc);
		param.put("ruleType", ruleType);
		param.put("ruleSubType", String.valueOf(ruleSubType));
		String s=null;
		param.put("tagId", s);
		param.put("status", 0);
	    return doFailRest(url, param.toString(), "POST", kw);
	}

	/**
	 * 修改仪表盘规则
	 * @param ruleTypeName 
	 * @param ruleName
	 * @param ruleDesc
	 * @param newRuleName
	 * @param newRuleDesc
	 * @return
	 */
	public JSONObject saveOrUpdate(String ruleTypeName, String ruleName, String ruleDesc, String newRuleName, String newRuleDesc){
		QueryCiQualityInfoById queryCiQualityInfoById = new QueryCiQualityInfoById();
		JSONObject result = queryCiQualityInfoById.queryCiQualityInfoById(ruleTypeName, ruleName);
		JSONObject ciQualityRule = result.getJSONObject("data").getJSONObject("ciQualityRule");
		String url = ":1511/tarsier-vmdb/cmv/ciQualityRule/saveOrUpdate";
		JSONObject param = new JSONObject();
		param.put("ruleName", newRuleName);
		param.put("ruleDesc", newRuleDesc);
		param.put("ruleType", ciQualityRule.get("ruleType"));
		param.put("ruleSubType", ciQualityRule.get("ruleSubType"));
		String s=null;
		param.put("tagId", s);
		param.put("status", ciQualityRule.get("status"));
		param.put("id", ciQualityRule.get("id"));
		return doRest(url, param.toString(), "POST");
	}
}
