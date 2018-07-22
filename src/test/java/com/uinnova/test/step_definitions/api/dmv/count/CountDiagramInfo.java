package com.uinnova.test.step_definitions.api.dmv.count;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2018-03-21
 * 编写人:sunsl
 * 功能介绍:DMV统计视图信息类
 */
public class CountDiagramInfo extends RestApi{
   public JSONObject countDiagramInfo(){
	   String url =":1511/tarsier-vmdb/dmv/count/countDiagramInfo";
	   return doRest(url,"","POST");
   }
}
