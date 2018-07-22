package com.uinnova.test.step_definitions.api.cmv.sys.data;

import java.math.BigDecimal;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.AuthUtil;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;

/**
 * 编写时间:2018-04-11
 * 编写人:sunsl
 * 功能介绍:给角色的数据设置编辑权限
 */
public class SaveRoleDataAuth extends RestApi{
   public JSONObject saveRoleDataAuth(String roleName, String className,JSONArray dataAuths){
	   String url =":1511/tarsier-vmdb/cmv/sys/data/saveRoleDataAuth";
	   AuthUtil  authUtil = new AuthUtil();
	   String roleId = authUtil.getRoleId(roleName);
	   CiClassUtil  ciClassUtil = new CiClassUtil();
	   BigDecimal classId = ciClassUtil.getCiClassId(className);
	   JSONObject param = new JSONObject();
	   param.put("roleId", roleId);
	   JSONArray dataAuthsParam = new JSONArray();
	   for(int i = 0; i < dataAuths.length(); i++){
		   JSONObject obj = (JSONObject)dataAuths.get(i);
		   JSONObject paramObj = new JSONObject();
		   paramObj.put("id", obj.getBigDecimal("id"));
		   paramObj.put("dataType", obj.getInt("dataType"));
		   paramObj.put("see", 1);
		   paramObj.put("delete", 0);
		  // if(classId.compareTo(obj.getBigDecimal("id"))==0){
			  paramObj.put("edit", 1);
		  // }else{
		   //   paramObj.put("edit", 0);
		   //}		  
		   dataAuthsParam.put(paramObj);
	   }
	   
	   param.put("dataAuths", dataAuthsParam);
	   
	   return doRest(url,param.toString(),"POST");
   }
   
   public JSONObject saveRoleDataAuth(String roleName, List<String> classNameList,JSONArray dataAuths){
	   String url =":1511/tarsier-vmdb/cmv/sys/data/saveRoleDataAuth";
	   AuthUtil  authUtil = new AuthUtil();
	   String roleId = authUtil.getRoleId(roleName);
	   CiClassUtil  ciClassUtil = new CiClassUtil();
	   
	   JSONObject param = new JSONObject();
	   param.put("roleId", roleId);
	   JSONArray dataAuthsParam = new JSONArray();
	   for(int i = 0; i < dataAuths.length(); i++){
		   JSONObject obj = (JSONObject)dataAuths.get(i);
		   JSONObject paramObj = new JSONObject();
		   paramObj.put("id", obj.getBigDecimal("id"));
		   paramObj.put("dataType", obj.getInt("dataType"));
		   paramObj.put("delete", false);
		   for(int j = 0; j < classNameList.size(); j++){
			  String className = classNameList.get(j);
			  BigDecimal classId = ciClassUtil.getCiClassId(className);
		     if(classId.compareTo(obj.getBigDecimal("id"))==0){
			    paramObj.put("see", true);
			    paramObj.put("add", true);
			    break;
		     }else{
		        paramObj.put("add", false);
		        paramObj.put("see", false);
		   }
		   }
		   dataAuthsParam.put(paramObj);
	   }
	   
	   param.put("dataAuths", dataAuthsParam);
	   
	   return doRest(url,param.toString(),"POST");
   }
}
