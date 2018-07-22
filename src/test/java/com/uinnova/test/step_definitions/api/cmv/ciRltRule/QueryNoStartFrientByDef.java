package com.uinnova.test.step_definitions.api.cmv.ciRltRule;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.cmv.CiRltRuleUtil;

/**
 * 编写时间:2018-01-31
 * 编写人:sunsl
 * 功能介绍:无源朋友圈规则查询CI关系类
 */
public class QueryNoStartFrientByDef extends RestApi{
   public JSONObject queryNoStartFrientByDef(String firendDefName){
	   String url =":1511/tarsier-vmdb/cmv/ciRlt/queryNoStartFrientByDef";
	   JSONObject param = new JSONObject();
	   CiRltRuleUtil ciRltRuleUtil = new CiRltRuleUtil();
	   BigDecimal firendDefId = ciRltRuleUtil.getDefIdByName(firendDefName);
	   param.put("firendDefId", firendDefId);
	   return doRest(url,param.toString(),"POST");
   }
}
