package com.uinnova.test.step_definitions.api.dmv.image;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-12-26
 * 编写人:wsl
 * 功能介绍:图标管理搜索功能
 */
public class QueryImageInfoPage extends RestApi{
	public JSONObject queryImageInfoPage(String searchKey){
		JSONObject param = new JSONObject();
		JSONObject cdt = new JSONObject();
		cdt.put("imgName", searchKey);
		cdt.put("imgGroups", 1);
		cdt.put("dirId", 2);
		param.put("cdt", cdt);
		param.put("pageNum", 1);
		param.put("pageSize", 1000);
		param.put("orders", "DIR_ID,ORDER_NO,IMG_NAME");
		String url=":1511/tarsier-vmdb/dmv/image/queryImageInfoPage";
		return doRest(url, param.toString(), "POST");
	}
}
