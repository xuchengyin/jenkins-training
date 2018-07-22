package com.uinnova.test.step_definitions.api.cmv.dynamicClass;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class RemoveDefaultTpl extends RestApi{

	/**
	 * @author ldw
	 * 
	 * 删除默认动态分类配置
	 * @return 删除记录数
	 * */
	public JSONObject removeDefaultTpl(){
		String url = ":1511/tarsier-vmdb/cmv/dynamicClass/removeDefaultTpl";
		
		return doRest(url, "", "POST");
	}
}
