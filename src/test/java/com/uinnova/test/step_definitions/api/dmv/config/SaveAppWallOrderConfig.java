package com.uinnova.test.step_definitions.api.dmv.config;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2018-05-23
 * 编写人:sunsl
 * 功能介绍:保存应用墙排序信息的接口
 */
public class SaveAppWallOrderConfig extends RestApi{
   public JSONObject saveAppWallOrderConfig(String param){
	   String url = ":1511/tarsier-vmdb/dmv/config/saveAppWallOrderConfig";
	   return doRest(url,param,"POST");
   }
}
