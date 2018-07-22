package com.uinnova.test.step_definitions.api.dmv.image;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-12-08
 * 编写人:sunsl
 * 功能介绍：DMV我的查看视图3D显示类
 */
public class QueryImageList extends RestApi{
   public JSONObject queryImageList(){
	   JSONObject param = new JSONObject();
	   JSONObject cdt = new JSONObject();
	   JSONArray ids = new JSONArray();
	   cdt.put("ids", ids);
	   param.put("cdt", cdt);
	   
	   String url = ":1511/tarsier-vmdb/dmv/image/queryImageList";
	   return doRest(url, param.toString(), "POST");
   }
}
