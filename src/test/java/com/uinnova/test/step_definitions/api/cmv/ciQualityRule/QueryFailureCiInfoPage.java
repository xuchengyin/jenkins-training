package com.uinnova.test.step_definitions.api.cmv.ciQualityRule;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.cmv.ciQualityRule.CiQualityRuleUtil;

/**
 * @author wsl
 * 查询仪表盘规则
 */
public class QueryFailureCiInfoPage extends RestApi {

	/**
	 * 分页查询规则描述信息
	 * @param ruleId
	 * @param classId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public JSONObject queryFailureCiInfoPage(BigDecimal ruleId, BigDecimal classId, int pageNum, int pageSize){
		String url = ":1511/tarsier-vmdb/cmv/ciQualityRule/queryFailureCiInfoPage";
		JSONObject param = new JSONObject();
		JSONObject cdt = new JSONObject();
		cdt.put("sumClassId", String.valueOf(classId));
		cdt.put("ruleId", String.valueOf(ruleId));
		param.put("cdt", cdt);
		param.put("pageNum", pageNum);
		param.put("pageSize", pageSize);
		return doRest(url, param.toString(), "POST");
	}
	
	
	/**
	 * 根据名字获取失败的数据
	 * @param ruleTypeName
	 * @param ruleName
	 * @param className
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public JSONObject queryFailureCiInfoPage(String ruleTypeName, String ruleName, String className, int pageNum, int pageSize){
		BigDecimal ruleId= CiQualityRuleUtil.getIDByNameAndRuleType(ruleTypeName, ruleName);
		CiClassUtil ciclsU = new CiClassUtil();
		BigDecimal classId = ciclsU.getCiClassId(className);
		return queryFailureCiInfoPage(ruleId, classId, pageNum, pageSize);
	}

}
