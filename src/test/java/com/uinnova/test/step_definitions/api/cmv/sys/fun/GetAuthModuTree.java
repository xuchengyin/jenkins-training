package com.uinnova.test.step_definitions.api.cmv.sys.fun;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2018-01-10
 * 编写人:sunsl
 * 功能介绍:取得所有的功能权限
 */
public class GetAuthModuTree extends RestApi{
   public JSONObject getAuthModuTree(){
	   String url = ":1511/tarsier-vmdb/cmv/sys/fun/getAuthModuTree";
	   return doRest(url, "", "POST");
   }
}
