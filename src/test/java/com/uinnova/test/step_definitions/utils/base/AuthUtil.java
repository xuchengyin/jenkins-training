package com.uinnova.test.step_definitions.utils.base;

import java.util.HashMap;
import java.util.List;

import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

/**
 * 编写时间:2017-12-18
 * 编写人:sunsl
 * 功能介绍:根据角色名称取得角色ID
 */
public class AuthUtil {
	/**
	 * 根据角色名称获取ID
	 * @param roleName
	 * @return
	 */
	public String getRoleId(String roleName){
		String sql = "SELECT ID FROM sys_role WHERE ROLE_CODE = '" + roleName + "' AND DATA_STATUS = 1 and USER_DOMAIN_ID = " + QaUtil.domain_id;
		//String sql = "SELECT ID FROM sys_role WHERE ROLE_NAME = '" + roleName + "' AND DATA_STATUS = 1 and USER_DOMAIN_ID = 1";
		List roleList = JdbcUtil.executeQuery(sql);
		HashMap roleHashMap = (HashMap)roleList.get(0);
		String roleId = (roleHashMap.get("ID")).toString();
		return roleId;
	}
}
