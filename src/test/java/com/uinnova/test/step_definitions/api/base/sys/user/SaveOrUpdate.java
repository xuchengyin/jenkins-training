package com.uinnova.test.step_definitions.api.base.sys.user;

import java.math.BigDecimal;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.api.base.sys.auth.QueryRoleList;
import com.uinnova.test.step_definitions.utils.base.UserUtil;

/**
 * 编写时间:2017-11-05 编写人:sunsl 功能介绍:用户管理添加,更改用户类
 */
public class SaveOrUpdate extends RestApi{
	public JSONObject saveOrUpdate(String opCode, String opName,String custom1,String loginPasswd,String emailAdress, String mobileNo,String notes,int opration) {
		String url = ":1511/tarsier-vmdb/cmv/sys/user/saveOrUpdate";
		JSONObject param = new JSONObject();
		JSONObject op = new JSONObject();
		JSONArray roles = new JSONArray();
		QueryRoleList qr = new QueryRoleList();
		JSONObject queryResult = qr.queryRoleList();
		JSONArray data = queryResult.getJSONArray("data");
		int k = 0;
		if (data.length() < 2){
			k = data.length();
		}else{
			k = 2;
		}
		if (opration == 0){
		  for (int i = 0; i < data.length(); i++) {
		 	JSONObject role = new JSONObject();
			JSONObject dataObj = (JSONObject) data.get(i);
			BigDecimal id = dataObj.getBigDecimal("id");
			String roleName = dataObj.getString("roleName");
			role.put("id", id);
			role.put("roleName", roleName);
			roles.put(role);
		}
		  op.put("loginPasswd", loginPasswd);
		}else{
			for (int i = 2; i < 3; i++) {
				JSONObject role = new JSONObject();
				JSONObject dataObj = (JSONObject) data.get(i);
				BigDecimal id = dataObj.getBigDecimal("id");
				String roleName = dataObj.getString("roleName");
				role.put("id", id);
				role.put("roleName", roleName);
				roles.put(role);
			}
			UserUtil userUtil = new UserUtil();
			op.put("id", userUtil.getUserId(opCode));
			
		}
		op.put("custom1", custom1);
		op.put("emailAdress", emailAdress);
		op.put("loginCode", opCode);
		op.put("mobileNo", mobileNo);
		op.put("notes", notes);
		op.put("opCode", opCode);
		op.put("opName", opName);
		op.put("opKind", 2);
		op.put("shortName", opCode);
		param.put("op", op);
		param.put("roles", roles);
		return doRest(url, param.toString(), "POST");
	}
}
