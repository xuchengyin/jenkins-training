package com.uinnova.test.step_definitions.api.dmv.group;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-11-23
 * 编写人:sunsl
 * 功能介绍:查询所有的用户类
 */
public class QueryOpList extends RestApi{
  public JSONObject queryOpList(){
	  String url=":1511/tarsier-vmdb/dmv/group/queryOpList";
	  JSONObject param = new JSONObject();
	  JSONObject cdt = new JSONObject();
	  param.put("cdt", cdt);
	  return doRest(url, param.toString(), "POST");
  }
}
