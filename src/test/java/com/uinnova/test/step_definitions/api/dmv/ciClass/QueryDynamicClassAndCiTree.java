package com.uinnova.test.step_definitions.api.dmv.ciClass;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编辑时间:2018-04-25
 * 编辑人:sunsl
 * 功能介绍:DMV树状图管理查看CI
 */
public class QueryDynamicClassAndCiTree extends RestApi{
   public JSONObject queryDynamicClassAndCiTree(){
	   String url = ":1511/tarsier-vmdb/dmv/ciClass/queryDynamicClassAndCiTree";
	   return doRest(url,"{}","POST");
   }
}
