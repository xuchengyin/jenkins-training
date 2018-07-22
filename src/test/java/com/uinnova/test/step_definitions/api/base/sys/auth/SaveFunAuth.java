package com.uinnova.test.step_definitions.api.base.sys.auth;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.AuthUtil;

import cucumber.api.DataTable;

/**
 * 编写时间:2017-11-07 编写人:sunsl 功能介绍:给角色添加功能权限类
 */

/**
 * 修改时间:2018-3-20 编写人:lidw 功能介绍:增加子菜单功能权限
 */
public class SaveFunAuth extends RestApi{
	/**
	 * @param roleName
	 * @param table
	 * @return
	 */
	public JSONObject saveFunAuth(String roleName, DataTable table) {
		String url = ":1511/tarsier-vmdb/cmv/sys/auth/saveFunAuth";
		JSONObject param = new JSONObject();
		AuthUtil authUtil = new AuthUtil();
		JSONObject role = new JSONObject();
		JSONArray modus = new JSONArray();
		role.put("id", authUtil.getRoleId(roleName));
		for (int i = 1; i < table.raw().size(); i++) {
			List<String> moduList = table.raw().get(i);
			JSONObject moduObj = new JSONObject();
			moduObj.put("id", moduList.get(1));
			if(moduList.size() == 3 && (!moduList.get(2).trim().equals("")) && moduList.get(2) != null){	
				String [] tempOpt = moduList.get(2).split(":");
				moduObj.put("optCodeList", tempOpt);
			}
			modus.put(moduObj);
		}
		param.put("role", role);
		param.put("modus", modus);
		return doRest(url, param.toString(), "POST");
	}
}
