package com.uinnova.test.step_definitions.api.noah.rest;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-12-12
 * 编写人:sunsl
 * 功能介绍:NOAH推送告警接口类
 */
public class EventImport extends RestApi{
   public JSONObject eventImport(String param){
	   String url =":1532/rest/tarsier/rest/event/import";
	   return doRest(url, param, "POST");
   }
}
