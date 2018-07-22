package com.uinnova.test.step_definitions.api.base.sys.loginlog;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 分页查询登录日志数据
 * @author wsl
 *
 */
public class QuerySysLoginLogPage extends RestApi{
	
	public JSONObject querySysLoginLogPage (String userName, String startLoginTime, String endLoginTime, int pageNum, int pageSize){
		String url =":1511/tarsier-vmdb/cmv/sys/loginlog/querySysLoginLogPage";
		JSONObject param = new JSONObject();
		JSONObject cdt = new JSONObject();
		cdt.put("userName", "%"+userName+"%");
		cdt.put("startLoginTime", startLoginTime);
		cdt.put("endLoginTime", endLoginTime);
		param.put("cdt", cdt);
		param.put("orders", "ID");
		param.put("pageNum", pageNum);
		param.put("pageSize", pageSize);
		return this.doRest(url, param.toString(), "POST");
	}
	
}
