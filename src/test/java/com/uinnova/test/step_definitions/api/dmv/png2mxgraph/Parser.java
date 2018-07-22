package com.uinnova.test.step_definitions.api.dmv.png2mxgraph;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * @author wsl
 * 解析图片的xml
 *
 */
public class Parser extends RestApi{
	public JSONObject parser(JSONObject param){
		String url =":1511/tarsier-vmdb/dmv/png2mxgraph/parser";
		return doRest(url, param.toString(), "POST");
	}
}
