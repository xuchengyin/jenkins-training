package com.uinnova.test.step_definitions.api.dmv.diagram;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2018-03-06
 * 编写人:sunsl
 * 功能介绍:从Base获得告警颜色级别等
 */
public class QueryEventWallCdt extends RestApi{
  public JSONObject queryEventWallCdt(){
	  String url =":1511/tarsier-vmdb/dmv/diagram/queryEventWallCdt";
	  return doRest(url,"","POST");
  }
}
