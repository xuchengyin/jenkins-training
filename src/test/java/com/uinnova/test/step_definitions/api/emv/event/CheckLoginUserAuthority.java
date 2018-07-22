package com.uinnova.test.step_definitions.api.emv.event;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.EMV.EmvUtil;

import cucumber.api.DataTable;
/*
 * 编写时间:2018-05-14
 * 编写人:yll
 * 功能介绍:判断当前登陆用户所属角色里面的操作权限（确认、关闭及派单）,因为没有传的参数，所以写成
 */
public class CheckLoginUserAuthority extends RestApi{
	public JSONObject checkLoginUserAuthority(){
		String url =":1516/monitor-web/event/checkLoginUserAuthority";
		return doRest(url,"{}", "POST");
	}
}
