package com.uinnova.test.step_definitions.api.dmv.ci;

import java.math.BigDecimal;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;

/**
 * 编写时间:2018-03-01
 * 编写人:sunsl
 * 功能介绍:根据条件获得CI
 */
public class SearchInfoByRuleItem extends RestApi{
    public JSONObject searchInfoByRuleItem(String className,String attrName,String attrValue){
    	String url = ":1511/tarsier-vmdb/dmv/ci/searchInfoByRuleItem";
    	CiClassUtil  ciClassUtil = new CiClassUtil();
    	BigDecimal ciClassId = ciClassUtil.getCiClassId(className);
    	BigDecimal attrId = ciClassUtil.getAttrIdByAttrName(className, attrName);
    	JSONObject param = new JSONObject();
    	JSONObject ruleItem = new JSONObject();
    	JSONArray ruleItems = new JSONArray();
    	JSONArray classIds = new JSONArray();
    	JSONObject ciCdt = new JSONObject();
    	JSONObject cdt = new JSONObject();
    	ciCdt.put("classIds", classIds);
    	ruleItem.put("ruleType", 1);
    	ruleItem.put("classId", ciClassId);
    	ruleItem.put("ruleOp", 1);
    	ruleItem.put("classAttrId", attrId);
    	ruleItem.put("ruleVal", attrValue);
    	ruleItems.put(ruleItem);
    	cdt.put("ciCdt", ciCdt);
    	cdt.put("ruleItems", ruleItems);
    	param.put("cdt", cdt);
    	param.put("pageNum", 1);
    	param.put("pageSize", 10000);
    	return doRest(url,param.toString(),"POST");
    }
}
