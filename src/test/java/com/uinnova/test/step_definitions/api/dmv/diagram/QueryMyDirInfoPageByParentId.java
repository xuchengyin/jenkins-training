package com.uinnova.test.step_definitions.api.dmv.diagram;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-12-04
 * 编写人:sunsl
 * 功能介绍:查询文件夹的视图类
 */
public class QueryMyDirInfoPageByParentId extends RestApi{
	public JSONObject queryMyDirInfoPageByParentId(JSONObject cdt){
		String url = ":1511/tarsier-vmdb/dmv/diagram/queryMyDirInfoPageByParentId";
		JSONObject param = new JSONObject();	
		param.put("cdt", cdt);
		param.put("pageSize", 24);
		param.put("pageNum", 1);
		return doRest(url, param.toString(), "POST");
	}
}
