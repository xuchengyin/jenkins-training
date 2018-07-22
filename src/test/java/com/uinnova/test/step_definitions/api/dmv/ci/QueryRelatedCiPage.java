package com.uinnova.test.step_definitions.api.dmv.ci;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2018-05-17
 * 编写人:sunsl
 * 功能介绍:查询分类的关联端口CI
 */
public class QueryRelatedCiPage extends RestApi{
   public JSONObject queryRelatedCiPage(String ciCode){
	   String url =":1511/tarsier-vmdb/dmv/ci/queryRelatedCiPage";
	   JSONObject param = new JSONObject();
	   JSONObject cdt = new JSONObject();
	   JSONArray ciQ = new JSONArray();
	   ciQ.put("ATTR");
	   cdt.put("ciCode", ciCode);
	   cdt.put("ciQ", ciQ);
	   param.put("pageNum", 1);
	   param.put("pageSize", 10);
	   param.put("cdt", cdt);
	   
	   return doRest(url,param.toString(),"POST");
   }
}
