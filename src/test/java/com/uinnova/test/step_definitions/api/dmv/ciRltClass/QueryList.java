package com.uinnova.test.step_definitions.api.dmv.ciRltClass;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-12-11
 * 编写人:sunsl
 * 功能介绍:dmv 查询所有关系
 */
public class QueryList extends RestApi{
   public JSONObject queryList(){
	   String url=":1511/tarsier-vmdb/dmv/ciRltClass/queryList";
	   return doRest(url, "{}", "POST");
   }
}
