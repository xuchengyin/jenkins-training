package com.uinnova.test.step_definitions.api.dmv.group;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 *编写时间:2017-12-23
 *编写人:sunsl 
 *功能介绍:根据小组名字查询小组信息
 */
public class QueryGroupList extends RestApi{
	public JSONObject queryGroupList(String groupName){
       String url =":1511/tarsier-vmdb/dmv/group/queryGroupList";
       JSONObject param = new JSONObject();
       param.put("groupName", groupName);
       return doRest(url, param.toString(), "POST");
	}
   
}
