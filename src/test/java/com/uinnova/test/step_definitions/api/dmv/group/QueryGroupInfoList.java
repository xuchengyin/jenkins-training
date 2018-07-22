package com.uinnova.test.step_definitions.api.dmv.group;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-11-23
 * 编写人:sunsl
 * 功能介绍:DMV查询所有小组类
 */
public class QueryGroupInfoList extends RestApi{
  public JSONObject queryGroupInfoList(){
	  String url = ":1511/tarsier-vmdb/dmv/group/queryGroupInfoList";
	  return doRest(url, "{}", "POST");
  }
}
