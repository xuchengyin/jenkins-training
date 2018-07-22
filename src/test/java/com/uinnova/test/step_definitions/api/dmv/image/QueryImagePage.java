package com.uinnova.test.step_definitions.api.dmv.image;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2018-01-02
 * 编写人:sunsl
 * 功能介绍:新建视图内查询视图类
 */
public class QueryImagePage extends RestApi{
  public JSONObject queryImagePage(){
	  String url = ":1511/tarsier-vmdb/dmv/image/queryImagePage";
	  JSONObject param = new JSONObject();
	  param.put("pageNum", 1);
	  param.put("pageSize", 20);
	  JSONObject cdt = new JSONObject();
	  cdt.put("imgGroup", 2);
	  cdt.put("imgName", "");
	  cdt.put("imgGroups", 2);
	  cdt.put("dirId", 0);
	  param.put("cdt", cdt);
	  return doRest(url, param.toString(), "POST");
  }
}
