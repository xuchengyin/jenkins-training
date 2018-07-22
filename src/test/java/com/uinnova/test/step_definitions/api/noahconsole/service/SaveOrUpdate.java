package com.uinnova.test.step_definitions.api.noahconsole.service;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2018-03-13
 * 编写人:sunsl
 * 功能介绍:加载Pmv服务的接口
 */
public class SaveOrUpdate extends RestApi{
   public JSONObject saveOrUpdate(String svcName,String svcPath,String isAsync,String file,String svcType,String code){
	   String url=":1530/noah-console/service/saveOrUpdate";
	   JSONObject param = new JSONObject();
	   param.put("svcName", svcName);
	   param.put("svcPath", svcPath);
	   param.put("isAsync", isAsync);
	   param.put("file", file);
	   param.put("svcType", svcType);
	   param.put("code", code);
	   return doRest(url,param.toString(),"POST");
   }
}
