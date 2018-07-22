package com.uinnova.test.step_definitions.api.dmv.group;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-12-08 编写人:sunsl 功能介绍:DMV我的查看视图详情查询小组信息类
 */
public class QueryGroupUserInfoList extends RestApi {
	public JSONObject queryGroupUserInfoList(JSONArray groupIdArray) {
		String url = ":1511/tarsier-vmdb/dmv/group/queryGroupUserInfoList";
		JSONObject param = new JSONObject();
		param.put("ids", groupIdArray);
		return doRest(url, param.toString(), "POST");
	}
}
