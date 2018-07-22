package com.uinnova.test.step_definitions.api.dmv.navigationbar;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-12-21 
 * 编写人:sunsl
 * 功能介绍:获取模块跳转码
 */
public class Projects extends RestApi{
    public JSONObject projects(){
    	String url =":1511/tarsier-vmdb/dmv/navigationbar/projects ";
    	return doRest(url, "", "POST");
    }
}
