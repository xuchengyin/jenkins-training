package com.uinnova.test.step_definitions.api.base.sys.updatelog;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 获取日志正文
 * @author wsl
 * 2018-3-7
 */
public class QueryUpdateLogText extends RestApi{
	
	/**
	 * 获取日志正文
	 * @return
	 */
	public JSONObject queryUpdateLogText(String name, String moduName){
		String url =":1511/tarsier-vmdb/cmv/sys/updatelog/queryUpdateLogText";
		JSONObject param = new JSONObject();
		param.put("name", name);
		param.put("moduName", moduName);
		return this.doRest(url, param.toString(), "POST");
	}

}
