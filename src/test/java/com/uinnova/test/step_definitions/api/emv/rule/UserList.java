package com.uinnova.test.step_definitions.api.emv.rule;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
/*
 * 编写时间:2018-04-10
 * 编写人:yll
 * 功能介绍:查询用户列表信息
 */
public class UserList extends RestApi{
	public JSONObject userList(String name){
		String url =":1516/monitor-web/rule/userList";
		JSONObject param = new JSONObject();
		param.put("name", name);
		param.put("team", true);//team为true时是用户团队管理里面进行查询用户的列表信息，若不传该参数时，用于查询规则里面的列表信息
		return doRest(url, param.toString(), "POST");
	}


}
