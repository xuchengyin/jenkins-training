package com.uinnova.test.step_definitions.api.dmv.group;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.dmv.GroupUtil;

/**
 * 编写时间:2017-11-23
 * 编写人:sunsl
 * 功能介绍:查询DMV小组动态日志类
 */
public class QueryGroupLogPage extends RestApi{
  public JSONObject queryGroupLogPage(String groupName){
	  String url =":1511/tarsier-vmdb/dmv/group/queryGroupLogPage";
	  GroupUtil groupUtil = new GroupUtil();
	  JSONObject param = new JSONObject();
	  JSONObject cdt = new JSONObject();
	  cdt.put("groupId", groupUtil.getGroupId(groupName));
	  param.put("pageNum", 1);
	  param.put("pageSize", 500);
	  param.put("cdt", cdt);
	  return doRest(url, param.toString(), "POST");
  }
}
