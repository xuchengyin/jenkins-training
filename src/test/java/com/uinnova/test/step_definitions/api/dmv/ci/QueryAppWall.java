package com.uinnova.test.step_definitions.api.dmv.ci;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2018-04-18
 * 编写人:sunsl
 * 功能介绍:查询应用墙上的应用
 */
public class QueryAppWall extends RestApi{
   public JSONObject queryAppWall(){
	   String url = ":1511/tarsier-vmdb/dmv/ci/queryAppWall";
	   return doRest(url,"","POST");
   }
}
