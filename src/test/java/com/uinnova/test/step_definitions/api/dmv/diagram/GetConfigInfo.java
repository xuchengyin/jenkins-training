package com.uinnova.test.step_definitions.api.dmv.diagram;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-12-21
 * 编写人:sunsl
 * 功能介绍：获取配置信息
 */
public class GetConfigInfo extends RestApi{
   public JSONObject getConfigInfo(){
	   String url =":1511/tarsier-vmdb/dmv/diagram/getConfigInfo";
	   return doRest(url, "", "POST");
   }
}
