package com.uinnova.test.step_definitions.api.base.ci;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.base.TagRuleUtil;

public class QueryAttrValuesByAttrDef extends RestApi{

	/**
	 * 通过属性定义查询属性的值
	 * @param ciClsName
	 * @param attrName
	 * @param ruleVal
	 * @return
	 */
	public JSONObject queryAttrValuesByAttrDef(String ciClsName,String attrName,String ruleVal){
		String url = ":1511/tarsier-vmdb/cmv/ci/queryAttrValuesByAttrDef";
		JSONObject param  =  new JSONObject();
		JSONObject cdt  =  new JSONObject();
		BigDecimal attrDefId = (new TagRuleUtil()).getAttrId(ciClsName, attrName);
		cdt.put("attrDefId",attrDefId );
		BigDecimal ciClassId = (new CiClassUtil()).getCiClassId(ciClsName);
		cdt.put("ciClassId",ciClassId );
		cdt.put("like", ruleVal);
		param.put("pageNum", 1);
		param.put("pageSize", 11);
		param.put("cdt", cdt);
		return doRest(url, param.toString(), "POST");
	}
}
