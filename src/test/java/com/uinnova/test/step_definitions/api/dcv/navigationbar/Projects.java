package com.uinnova.test.step_definitions.api.dcv.navigationbar;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
/**
 * 
 * @author wjx
 * 获取顶部菜单列表
 *
 */
public class Projects extends RestApi{
	public JSONObject projects(){
		String url = ":1511/tarsier-vmdb/dcv/navigationbar/projects";
		return doRest(url, null, "POST");
	}
}
