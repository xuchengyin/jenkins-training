package com.uinnova.test.step_definitions.api.dmv.image;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-12-26
 * 编写人:wsl
 * 功能介绍:根据关键字搜索图标
 */
public class QueryImageInfoListByCdt extends RestApi{
	public JSONObject queryImageInfoListByCdt(String searchKey){
		JSONObject param = new JSONObject();
		JSONObject cdt = new JSONObject();
		JSONArray dirIds = new JSONArray();
		dirIds.put(1);
		dirIds.put(10001);
		dirIds.put(10111);
		dirIds.put(11003);
		cdt.put("imgName", searchKey);
		cdt.put("dirIds", dirIds);
		param.put("cdt", cdt);
		String url=":1511/tarsier-vmdb/dmv/image/queryImageInfoListByCdt";
		return doRest(url, param.toString(), "POST");
	}
}
