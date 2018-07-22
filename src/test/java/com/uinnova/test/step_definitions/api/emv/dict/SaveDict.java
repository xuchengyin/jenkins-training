package com.uinnova.test.step_definitions.api.emv.dict;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class SaveDict extends RestApi{
	//{"groupId":1,"id":"","code":"9","chineseName":"6","englishName":"6"}
	public JSONObject saveDict(int groupId,int id,String code,String chineseName,String englishName ){
		String url =":1516/monitor-web/dict/saveDict";
		JSONObject param = new JSONObject();
		param.put("groupId", groupId);//所属字典表组的ID
		return doRest(url, param.toString(), "POST");
	}
}
