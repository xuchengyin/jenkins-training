package com.uinnova.test.step_definitions.api.dmv.ciRlt;

import java.math.BigDecimal;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.api.base.search.ci.SearchInfoList;
import com.uinnova.test.step_definitions.utils.base.RltClassUtil;

/**
 * 编写时间:2018-03-23
 * 编写人:sunsl
 * 功能介绍:DMV新建视图两个CI间创建关系
 */
public class QueryRltInfoList extends RestApi{
   public JSONObject queryRltInfoList(String rltClassName,String sourceCiClass,String sourceCiCode,String targetCiClass,String targetCiCode){
	   String url = ":1511/tarsier-vmdb/dmv/ciRlt/queryRltInfoList";
	   BigDecimal rltClsId =  (new RltClassUtil()).getRltClassId(rltClassName);
	   BigDecimal sourceCiId = (new SearchInfoList()).searchInfoList_getSingleCiId(sourceCiClass, sourceCiCode);
	   BigDecimal targetCiId = (new SearchInfoList()).searchInfoList_getSingleCiId(targetCiClass, targetCiCode);
	   JSONObject param = new JSONObject();
	   JSONArray ciRltQs = new JSONArray();
	   ciRltQs.put("CI_RLt_ATTR");
	   JSONArray classIds = new JSONArray();
	   classIds.put(rltClsId);
	   JSONArray sourceCiIds = new JSONArray();
	   sourceCiIds.put(sourceCiId);
	   sourceCiIds.put(targetCiId);
	   JSONArray targetCiIds = new JSONArray();
	   targetCiIds.put(sourceCiId);
	   targetCiIds.put(targetCiId);
	   param.put("ciRltQs", ciRltQs);
	   param.put("ciRltLvl", 2);
	   param.put("sourceCiIds", sourceCiIds);
	   param.put("targetCiIds", targetCiIds);
	   return doRest(url,param.toString(),"POST");
   }
}
