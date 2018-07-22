package com.uinnova.test.step_definitions.api.base.sys.user;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.UserUtil;

/**
 * 编写时间:2017-11-05
 * 编写人:sunsl
 * 功能介绍:根据用户ID删除用户类
 */
public class RemoveOpById extends RestApi{
	/**
	 * @param opCode
	 * @return
	 */
	public JSONObject removeOpById(String opCode){
		String url = ":1511/tarsier-vmdb/cmv/sys/user/removeOpById";
		UserUtil userUtil = new UserUtil();
		return doRest(url, userUtil.getUserId(opCode), "POST");	  
	}
}
