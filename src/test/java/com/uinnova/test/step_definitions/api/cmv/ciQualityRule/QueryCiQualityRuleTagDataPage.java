package com.uinnova.test.step_definitions.api.cmv.ciQualityRule;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 仪表盘规则定义筛选CI范围预览数据
 * @author wsl
 */
public class QueryCiQualityRuleTagDataPage extends RestApi {

	/**
	 * 分页查询规则描述信息
	 * @param searchKey
	 * @param ruleType
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public JSONObject queryCiQualityRuleTagDataPage(String tagId, BigDecimal classId, int pageNum, int pageSize){
		String url = ":1511/tarsier-vmdb/cmv/ciQualityRule/queryCiQualityRuleTagDataPage";
		JSONObject param = new JSONObject();
		JSONObject cdt = new JSONObject();
		cdt.put("classId", String.valueOf(classId));
		cdt.put("tagId", tagId);
		param.put("cdt", cdt);
		param.put("pageNum", pageNum);
		param.put("pageSize", pageSize);
		return doRest(url, param.toString(), "POST");
	}
	
}
