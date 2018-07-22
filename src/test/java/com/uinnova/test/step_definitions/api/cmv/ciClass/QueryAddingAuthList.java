package com.uinnova.test.step_definitions.api.cmv.ciClass;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编辑时间:2018-05-17
 * 编辑人:sunl
 * 功能介绍:
 */
public class QueryAddingAuthList extends RestApi{
  public JSONObject queryAddingAuthList(){
	  String url =":1511/tarsier-vmdb/cmv/ciClass/queryAddingAuthList";
	  return doRest(url,"","POST");
  }
}
