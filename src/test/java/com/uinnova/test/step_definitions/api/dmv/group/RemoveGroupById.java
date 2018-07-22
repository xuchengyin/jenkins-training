package com.uinnova.test.step_definitions.api.dmv.group;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.dmv.GroupUtil;
/**
 * 编写时间:2017-11-22
 * 编写人:sunsl
 * 功能介绍:DMV小组的删除类
 */
public class RemoveGroupById extends RestApi{
	
  public JSONObject removeGroupById(String updateGroupName){
	  GroupUtil groupUtil = new GroupUtil();
	  String url=":1511/tarsier-vmdb/dmv/group/removeGroupById";
	  JSONObject param = new JSONObject();
	  param.put("id", groupUtil.getGroupId(updateGroupName));
	  return doRest(url, param.toString(), "POST");
  }
}
