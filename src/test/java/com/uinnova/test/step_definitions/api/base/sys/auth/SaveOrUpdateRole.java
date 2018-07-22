package com.uinnova.test.step_definitions.api.base.sys.auth;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.AuthUtil;

/**
 * 编写时间:2017-11-06
 * 编写人:sunsl
 * 功能介绍:角色管理的添加修改类
 */
public class SaveOrUpdateRole extends RestApi{
	/**
	 * @param roleName
	 * @param roleCode
	 * @param roleDesc
	 * @param oldRoleName
	 * @return
	 */
	public JSONObject saveOrUpdateRole(String roleName,String roleCode,String roleDesc,String oldRoleName){
		String url = ":1511/tarsier-vmdb/cmv/sys/auth/saveOrUpdateRole";
		JSONObject param = new JSONObject();
		JSONObject role = new JSONObject();
		role.put("roleName", roleName);
		role.put("roleCode", roleCode);
		role.put("roleDesc", roleDesc);
		if(oldRoleName != "" ){
			AuthUtil authUtil = new AuthUtil();
			role.put("id", authUtil.getRoleId(oldRoleName));
		}
		param.put("role", role);
		return doRest(url, param.toString(), "POST");
	}
}
