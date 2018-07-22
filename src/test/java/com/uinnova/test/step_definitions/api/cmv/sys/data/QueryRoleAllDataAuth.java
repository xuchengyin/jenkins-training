package com.uinnova.test.step_definitions.api.cmv.sys.data;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.AuthUtil;

/**
 * 编写时间:2018-04-11
 * 编写人:sunsl
 * 功能介绍:给角色查询数据区分类
 */
public class QueryRoleAllDataAuth extends RestApi{
   public JSONObject queryRoleAllDataAuth(String roleName){
	   String url =":1511/tarsier-vmdb/cmv/sys/data/queryRoleAllDataAuth";
	   AuthUtil authUtil = new AuthUtil();
	   String roleId = authUtil.getRoleId(roleName);
	   return doRest(url,roleId,"POST");
   }
}
