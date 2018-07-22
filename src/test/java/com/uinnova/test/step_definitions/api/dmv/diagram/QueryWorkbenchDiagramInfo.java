package com.uinnova.test.step_definitions.api.dmv.diagram;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2018-04-04
 * 编写人:sunsl
 * 功能介绍:DMV_工作台查询接口
 */
public class QueryWorkbenchDiagramInfo extends RestApi{
   public JSONObject queryWorkbenchDiagramInfo(int pageSize,int type){
	   String url =":1511/tarsier-vmdb/dmv/diagram/queryWorkbenchDiagramInfo";
	   JSONObject param = new JSONObject();
	   JSONObject cdt = new JSONObject();
	   cdt.put("type", type);
	   param.put("cdt", cdt);
	   param.put("pageNum", 1);
	   param.put("pageSize", pageSize);

	   return doRest(url,param.toString(),"POST");
   }
}
