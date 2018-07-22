package com.uinnova.test.step_definitions.api.dmv.group;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.dmv.GroupUtil;

/**
 * 编写时间:2017-11-23
 * 编写人:sunsl
 * 功能介绍:DMV小组更换所有者类
 */
public class TransfeGroupOwner extends RestApi{
  public JSONObject transfeGroupOwner(String groupName,BigDecimal id){
	  String url=":1511/tarsier-vmdb/dmv/group/transfeGroupOwner";
	  JSONObject param = new JSONObject();
	  GroupUtil groupUtil = new GroupUtil();
	  param.put("groupId", groupUtil.getGroupId(groupName));
	  param.put("userId", id);
	  return doRest(url, param.toString(), "POST");
  }
}
