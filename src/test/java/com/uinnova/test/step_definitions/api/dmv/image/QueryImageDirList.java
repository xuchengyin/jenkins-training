package com.uinnova.test.step_definitions.api.dmv.image;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * @author wsl
 * 查询图标目录list
 * 2017-12-6
 *
 */
public class QueryImageDirList extends RestApi{
	public JSONObject queryImageDirList(){
		JSONObject param = new JSONObject();
		param.put("orders", "DIR_NAME");
		String url=":1511/tarsier-vmdb/dmv/image/queryImageDirList";
		return doRest(url, param.toString(), "POST");
	}
}
