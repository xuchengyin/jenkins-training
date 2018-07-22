package com.uinnova.test.step_definitions.api.dmv.diagram;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.dmv.GroupUtil;
/**
 * 编写时间:2017-11-23
 * 编写人:sunsl
 * 功能介绍:根据小组查询视图类
 */
public class QueryDiagramByGroupId extends RestApi{
  public JSONObject queryDiagramByGroupId(String groupName){
	  String url=":1511/tarsier-vmdb/dmv/diagram/queryDiagramByGroupId";
	  GroupUtil groupUtil = new GroupUtil();
	  JSONObject param = new JSONObject();
	  param.put("id", groupUtil.getGroupId(groupName));
	  return doRest(url, param.toString(), "POST");
  }
}
