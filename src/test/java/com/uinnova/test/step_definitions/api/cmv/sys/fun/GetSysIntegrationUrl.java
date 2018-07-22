package com.uinnova.test.step_definitions.api.cmv.sys.fun;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class GetSysIntegrationUrl extends RestApi{
/**
 * @author ldw
 * 获取系统集成页面外部地址
 * */
	public JSONObject getSysIntegrationUrl(){
		String url = ":1511/tarsier-vmdb/cmv/sys/fun/getSysIntegrationUrl";
		//{"data":"http://192.168.1.124:1511/tarsier-vmdb/vmdb/base/xi/xi.html","code":-1,"success":true}
		//返回地址是在cmv的配置文件中project.cmv.ext.integration配置的，死的。
		return doRest(url, "", "POST");
		
	}
}
