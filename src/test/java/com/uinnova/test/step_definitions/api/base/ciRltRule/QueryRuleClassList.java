package com.uinnova.test.step_definitions.api.base.ciRltRule;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;

public class QueryRuleClassList extends RestApi{

	/**
	 * 获取分类及朋友分类属性
	 * @param ciClsName
	 * @return
	 */
	public JSONObject queryRuleClassList(String ciClsName){
		String url = ":1511/tarsier-vmdb/cmv/ciRltRule/queryRuleClassList";
		BigDecimal ciClsId = (new CiClassUtil()).getCiClassId(ciClsName);
		return doRest(url, String.valueOf(ciClsId), "POST");
	}
}
