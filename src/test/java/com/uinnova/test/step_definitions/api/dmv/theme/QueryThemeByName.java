package com.uinnova.test.step_definitions.api.dmv.theme;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-12-12
 * 编写人:sunsl
 * 功能介绍:DMV我的查看视图查看配置接口类
 */
public class QueryThemeByName extends RestApi{
    public JSONObject queryThemeByName(String  themeName){
    	String url = ":1511/tarsier-vmdb/dmv/theme/queryThemeByName";
    	JSONObject param = new JSONObject();
	    param.put("themeName", themeName);
    	return doRest(url, param.toString(), "POST");
    }
}
