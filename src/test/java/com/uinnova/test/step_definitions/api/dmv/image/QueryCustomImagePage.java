package com.uinnova.test.step_definitions.api.dmv.image;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * @author wsl
 * 2017-12-6
 * 分页查询自定义形状
 *
 */
public class QueryCustomImagePage extends RestApi{
	public JSONObject queryCustomImagePage(int pagNum, int pageSize){
		JSONObject param = new JSONObject();
		JSONObject cdt = new JSONObject();
		param.put("pageNum", pagNum);
		param.put("pageSize", pageSize);
		String url=":1511/tarsier-vmdb/dmv/image/queryCustomImagePage";
		return doRest(url, param.toString(), "POST");
	}
}


