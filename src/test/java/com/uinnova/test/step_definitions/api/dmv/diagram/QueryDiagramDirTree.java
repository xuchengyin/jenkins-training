package com.uinnova.test.step_definitions.api.dmv.diagram;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-12-28
 * 编写人:sunsl
 * 功能介绍:查询文件夹类
 */
public class QueryDiagramDirTree extends RestApi{
   public JSONObject queryDiagramDirTree(){
	   String url = ":1511/tarsier-vmdb/dmv/diagram/queryDiagramDirTree";
	   return doRest(url, "{}", "POST");
   }
}
