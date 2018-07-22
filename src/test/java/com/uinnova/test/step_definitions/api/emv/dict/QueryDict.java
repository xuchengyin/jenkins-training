package com.uinnova.test.step_definitions.api.emv.dict;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.EMV.EmvUtil;

import cucumber.api.DataTable;
/*
 * 编写时间:2018-04-10
 * 编写人:yll
 * 功能介绍:根据字典表组的ID查询对应的字典表列表信息
 */
public class QueryDict extends RestApi{
	public JSONObject queryDict(int groupId){
		String url =":1516/monitor-web/dict/queryDict";
		JSONObject param = new JSONObject();
		param.put("groupId", groupId);//所属字典表组的ID
		return doRest(url, param.toString(), "POST");
	}

}
