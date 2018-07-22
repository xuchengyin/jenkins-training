package com.uinnova.test.step_definitions.api.dmv.diagram;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2018-01-09
 * 编写人:sunsl
 * 功能介绍:DMV我的收藏夹接口类
 */
public class CollectDiagramByIds extends RestApi{
   public JSONObject collectDiagramByIds(JSONArray diagramIds){
	   String url = ":1511/tarsier-vmdb/dmv/diagram/collectDiagramByIds";
	   JSONObject param = new JSONObject();
	   param.put("diagramIds", diagramIds);
	   return doRest(url, param.toString(), "POST");
   }
}
