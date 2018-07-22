package com.uinnova.test.step_definitions.api.cmv.ciRltRule;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2018-01-29
 * 编写人:sunsl
 * 功能介绍:朋友圈规则查询类
 */
public class QueryRltRuleDefListHasClass extends RestApi{
    public JSONObject queryRltRuleDefListHasClass(String defName){
    	String url = ":1511/tarsier-vmdb/cmv/ciRltRule/queryRltRuleDefListHasClass";
    	JSONObject param = new JSONObject();
    	param.put("defName", defName);
    	return doRest(url, param.toString(), "POST");
    }
}
