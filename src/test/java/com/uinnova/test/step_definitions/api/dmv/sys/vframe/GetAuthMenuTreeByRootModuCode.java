package com.uinnova.test.step_definitions.api.dmv.sys.vframe;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2018-02-27
 * 编写人:sunsl
 * 功能介绍:获取权限功能
 */
public class GetAuthMenuTreeByRootModuCode extends RestApi{
   public JSONObject getAuthMenuTreeByRootModuCode(String rootCode){
	   String url = ":1511/tarsier-vmdb/dmv/sys/vframe/getAuthMenuTreeByRootModuCode";
	   JSONObject param = new JSONObject();
	   param.put("rootCode", rootCode);
	   return doRest(url,param.toString(),"POST");
   }
}
