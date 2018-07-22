package com.uinnova.test.step_definitions.api.dmv.file;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2018-05-16
 * 编写人:sunsl
 * 功能介绍:查询根目录的id
 */
public class QueryFileRootDir extends RestApi{
   public JSONObject queryFileRootDir(){
	   String url =":1511/tarsier-vmdb/dmv/file/queryFileRootDir";
	   return doRest(url,"","POST");
   }
}
