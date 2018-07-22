package com.uinnova.test.step_definitions.api.dmv.diagram;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2018-01-12
 * 编写人:sunsl
 * 功能介绍:组合视图中搜索视图类
 */
public class QueryWorkabilityDiagram extends RestApi{
   public JSONObject queryWorkabilityDiagram(Integer diagramType,String searchKey){
	   String url =":1511/tarsier-vmdb/dmv/diagram/queryWorkabilityDiagram";
	   JSONObject param = new JSONObject();
	   JSONObject cdt = new JSONObject();
	   param.put("pageNum", 1);
	   param.put("pageSize", 50);
	   param.put("orders", "modify_Time desc");
	   cdt.put("diagramType", diagramType);
	   cdt.put("name", "%" + searchKey +"%");
	   param.put("cdt", cdt);
	   return doRest(url, param.toString(), "POST");
   }
}
