package com.uinnova.test.step_definitions.api.dmv.sys.data;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2018-05-14
 * 编写人:sunsl
 * 功能介绍:查询系统logo的接口  
 */
public class QuerySysLogos extends RestApi{
   public JSONObject querySysLogos(){
	   String url = ":1511/tarsier-vmdb/dmv/sys/data/querySysLogos";
	   return doRest(url,"","POST");
   }
}
