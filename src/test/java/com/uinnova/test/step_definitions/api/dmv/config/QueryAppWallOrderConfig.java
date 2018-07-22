package com.uinnova.test.step_definitions.api.dmv.config;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2018-05-23
 * 编写人:sunsl
 * 功能介绍：查询应用墙排序信息的接口
 */
public class QueryAppWallOrderConfig  extends RestApi{
    public JSONObject queryAppWallOrderConfig(){
    	String url = ":1511/tarsier-vmdb/dmv/config/queryAppWallOrderConfig";
    	return doRest(url,"","POST");
    }
}
