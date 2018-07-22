package com.uinnova.test.step_definitions.api.dmv.ci;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;

/**
 * 编写时间:2018-03-01
 * 编写人:sunsl
 * 功能介绍:根据分类ID,属性ID取得属性的所有值
 */
public class QueryAttrValuesByAttrDef extends RestApi{
   public JSONObject queryAttrValuesByAttrDef(String className,String attrName){
	   String url = ":1511/tarsier-vmdb/dmv/ci/queryAttrValuesByAttrDef";
	   CiClassUtil  ciClassUtil = new CiClassUtil();
	   BigDecimal ciClassId = ciClassUtil.getCiClassId(className);
	   BigDecimal attrDefId = ciClassUtil.getAttrIdByAttrName(className,attrName);
	   JSONObject cdt = new JSONObject();
	   cdt.put("ciClassId", ciClassId);
	   cdt.put("attrDefId", attrDefId);
	   cdt.put("like", "");
	   JSONObject param = new JSONObject();
	   param.put("cdt", cdt);
	   param.put("pageNum", 1);
	   param.put("pageSize", 100);
	   return doRest(url,param.toString(),"POST");
   }
}
