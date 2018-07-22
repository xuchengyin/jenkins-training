package com.uinnova.test.step_definitions.api.emv.group;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.EMV.EmvUtil;

import cucumber.api.DataTable;

/*
 * 编写时间:2018-05-22
 * 编写人:yll
 * 功能介绍:判断该团队是否已经存在列表中，传的两个参数id和name ,但是id都是为空的
 */
public class IsGroupAlreadyExist extends RestApi {
	public JSONObject isGroupAlreadyExist(String name){
		   String url =":1516/monitor-web/group/isGroupAlreadyExist";
		   JSONObject param = new JSONObject();
		  // param.put("id",id);//id都为空
		   param.put("name",name);
		   return doRest(url, param.toString(), "POST");
	   }

}
