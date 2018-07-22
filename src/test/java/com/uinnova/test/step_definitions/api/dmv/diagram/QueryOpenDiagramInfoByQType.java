package com.uinnova.test.step_definitions.api.dmv.diagram;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-11-15
 * 编写人:sunsl
 * 功能介绍:DMV广场搜索视图的类
 */
public class QueryOpenDiagramInfoByQType extends RestApi{
  public JSONObject queryOpenDiagramInfoByQType(String searchKey,int searchType){
	  String url = ":1511/tarsier-vmdb/dmv/diagram/queryOpenDiagramInfoByQType";
	  JSONObject param = new JSONObject();
	  JSONObject cdt = new JSONObject();
	  cdt.put("type", searchType);
	  cdt.put("like", "%" + searchKey + "%");
	  param.put("cdt", cdt);
	  param.put("pageSize", 32);
	  param.put("pageNum", 1);
	  
	  return doRest(url, param.toString(), "POST");
  }
  
  public JSONObject queryOpenDiagramInfoByQType(String searchKey,int searchType,int filterType){
	  String url = ":1511/tarsier-vmdb/dmv/diagram/queryOpenDiagramInfoByQType";
	  JSONObject param = new JSONObject();
	  JSONObject cdt = new JSONObject();
	  cdt.put("type", searchType);
	  cdt.put("like", "%" + searchKey + "%");
	  cdt.put("filterType", filterType);
	  param.put("cdt", cdt);
	  param.put("pageSize", 32);
	  param.put("pageNum", 1);
	  
	  return doRest(url, param.toString(), "POST");
  }
}
