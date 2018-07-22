package com.uinnova.test.step_definitions.api.dmv.theme;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-11-24
 * 编写人:sunsl
 * 功能介绍:DMV根据分类ID查询指标类
 */
public class QueryKpiInfoByClassId extends RestApi{
	
   public JSONObject queryKpiInfoByClassId(BigDecimal classId,int groupType){
	   String url =":1511/tarsier-vmdb/dmv/theme/queryKpiInfoByClassId";
	   JSONObject param = new JSONObject();
	   param.put("classId", classId);
	   param.put("groupType", groupType);
	   return doRest(url, param.toString(), "POST");
   }
}
