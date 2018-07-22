package com.uinnova.test.step_definitions.api.base.sys.user;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.UserUtil;

/**
 * 编写时间:2017-11-05
 * 编写人:sunsl
 * 功能介绍:重新设置用户密码
 */
public class ResetUserPasswdByAdmin extends RestApi{
	/**
	 * @param opCode
	 * @param newPassword
	 * @return
	 */
	public JSONObject resetUserPasswdByAdmin(String opCode,String newPassword){
		String url = ":1511/tarsier-vmdb/cmv/sys/user/resetUserPasswdByAdmin";
		UserUtil usrUtil = new UserUtil();
		JSONObject param = new JSONObject();
		param.put("userId", usrUtil.getUserId(opCode));
		param.put("newPasswd", newPassword);
		return doRest(url, param.toString(), "POST");
	}
}
