package com.uinnova.test.step_definitions.api.dmv.sys.vframe;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-12-22
 * 编写人:sunsl
 * 功能介绍：获取菜单信息
 */
public class GetAuthMenuTree extends RestApi{
   public JSONObject getAuthMenuTree(){
	   String url =":1511/tarsier-vmdb/dmv/sys/vframe/getAuthMenuTree";
	   return doRest(url, "{}", "POST");
   }
}
