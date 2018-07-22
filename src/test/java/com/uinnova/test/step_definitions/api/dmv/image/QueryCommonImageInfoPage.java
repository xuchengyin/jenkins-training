package com.uinnova.test.step_definitions.api.dmv.image;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * @author wsl
 * 查询常用图标
 * 2017-12-14
 *
 */
public class QueryCommonImageInfoPage extends RestApi{
	
	public JSONObject queryCommonImageInfoPage(String imgName, int imgGroups, int pagNum, int pageSize){
		JSONObject param = new JSONObject();
		JSONObject cdt = new JSONObject();
		cdt.put("imgName", imgName);
		cdt.put("imgGroups", imgGroups);
		param.put("cdt", cdt);
		param.put("pageNum", pagNum);
		param.put("pageSize", pageSize);
		String url=":1511/tarsier-vmdb/dmv/image/queryCommonImageInfoPage";
		return doRest(url, param.toString(), "POST");
	}
}
