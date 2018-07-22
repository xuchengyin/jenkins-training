package com.uinnova.test.step_definitions.api.dmv.diagram;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-11-15
 * 编写人:sunsl
 * 功能介绍:查询视图的用户类
 */
public class QueryOpenDiagramCountByUser extends RestApi{
  public JSONObject queryOpenDiagramCountByUser(){
	  String url=":1511/tarsier-vmdb/dmv/diagram/queryOpenDiagramCountByUser";
	  return doRest(url, "{}", "POST");
  }
}
