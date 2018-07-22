package com.uinnova.test.step_definitions.api.dmv.sys.data;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编辑时间:2018-04-11
 * 编辑人:sunsl
 * 功能介绍:DMV查询角色所有数据权限
 */
public class QueryUserAllDataAuth extends RestApi{
   public JSONObject queryUserAllDataAuth(){
	   String url=":1511/tarsier-vmdb/dmv/sys/data/queryUserAllDataAuth";
	   return doRest(url,"","POST");
   }
}
