package com.uinnova.test.step_definitions.api.dmv.diagram;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2018-01-09
 * 编写人:sunsl
 * 功能介绍:收藏夹搜索视图
 */
public class QueryEnshDiagramPage extends RestApi{
  public JSONObject queryEnshDiagramPage(String searchKey){
	  String url = ":1511/tarsier-vmdb/dmv/diagram/queryEnshDiagramPage";
	  JSONObject param = new JSONObject();
	  JSONObject cdt = new JSONObject();
	  cdt.put("name", "%" + searchKey +"%");
	  param.put("pageNum", 1);
	  param.put("pageSize", 10000);
	  param.put("cdt", cdt);
	  param.put("orders", "MODIFY_TIME DESC");
	  return doRest(url, param.toString(), "POST");
  }
}
