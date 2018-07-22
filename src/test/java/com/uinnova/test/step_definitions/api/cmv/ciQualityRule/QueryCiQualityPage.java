package com.uinnova.test.step_definitions.api.cmv.ciQualityRule;

import static org.junit.Assert.fail;

import org.json.JSONObject;

import com.uinnova.test.contant.Contants;
import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.cmv.ciQualityRule.CiQualityRuleType;

/**
 * @author wsl
 * 查询仪表盘规则
 */
public class QueryCiQualityPage extends RestApi {

	/**
	 * 分页查询规则描述信息
	 * @param searchKey
	 * @param ruleType
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public JSONObject queryCiQualityPage(String searchKey, String ruleTypeName, int pageNum, int pageSize){
		int ruleType = 10;
		switch (ruleTypeName){
		case Contants.CIQUALITYRULE_VERACITY:
			ruleType = CiQualityRuleType.VERACITYALONE.getRuleType();
			break;
		case Contants.CIQUALITYRULE_CIATTRCOMPLIANCE:
			ruleType = CiQualityRuleType.CIATTRCOMPLIANCE.getRuleType();
			break;
		case Contants.CIQUALITYRULE_CIATTRINTEGRITY:
			ruleType = CiQualityRuleType.CIATTRINTEGRITY.getRuleType();
			break;
		default:
			fail(Contants.CIQUALITYRULE_UNLAWFULRULE+ruleTypeName);
		}
		String url = ":1511/tarsier-vmdb/cmv/ciQualityRule/queryCiQualityPage";
		JSONObject param = new JSONObject();
		JSONObject cdt = new JSONObject();
		cdt.put("ruleName", "%"+searchKey+"%");
		cdt.put("ruleType", ruleType);
		param.put("cdt", cdt);
		param.put("pageNum", pageNum);
		param.put("pageSize", pageSize);
		param.put("orders", "MODIFY_TIME DESC");
		return doRest(url, param.toString(), "POST");
	}

}
