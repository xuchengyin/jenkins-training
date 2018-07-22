package com.uinnova.test.step_definitions.api.base.sys.updatelog;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 获取日志菜单树
 * @author wsl
 * 2018-3-7
 */
public class QueryUpdateLogTree extends RestApi{
	
	/**
	 * 获取日志菜单树
	 * @return
	 */
	public JSONObject queryUpdateLogTree(){
		String url =":1511/tarsier-vmdb/cmv/sys/updatelog/queryUpdateLogTree";
		return this.doRest(url, "", "POST");
	}

}
