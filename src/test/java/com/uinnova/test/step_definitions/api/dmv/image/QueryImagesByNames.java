package com.uinnova.test.step_definitions.api.dmv.image;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-12-22
 * 编写人:sunsl
 * 功能介绍:根据名称查询图片
 */
public class QueryImagesByNames extends RestApi{
  public JSONObject queryImagesByNames(String imageName){
	  String url =":1511/tarsier-vmdb/dmv/image/queryImagesByNames";
	  JSONObject param = new JSONObject();
	  JSONArray imageNames = new JSONArray();
	  imageNames.put(imageName);
	  param.put("imageNames", imageName);
	  return doRest(url, param.toString(), "POST");
  }
}
