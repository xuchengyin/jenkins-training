package com.uinnova.test.step_definitions.api.base.navigationbar;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * @author wsl
 * 读取导航栏
 * 2017-12-7
 *
 */
public class Projects extends RestApi{
	/**
	 * @return
	 */
	public JSONObject projects() {
		String url = ":1511/tarsier-vmdb/cmv/navigationbar/projects";
		return doRest(url, "{}", "POST");
	}
}
