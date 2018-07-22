package com.uinnova.test.step_definitions.api.emv.event;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编辑时间:2018-05-10
 * 编辑人:sunsl
 * 功能介绍:查询时间所属应用分类统计
 */
public class QueryCiApplicationGroup extends RestApi{
   public JSONObject queryCiApplicationGroup(){
	   String url =":1516/monitor-web/event/queryCiApplicationGroup";
	   return doRest(url, "{}","POST");
   }
}
