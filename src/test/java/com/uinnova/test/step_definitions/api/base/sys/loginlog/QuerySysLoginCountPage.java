package com.uinnova.test.step_definitions.api.base.sys.loginlog;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 分页查询登录日志数据
 * @author wsl
 *
 */
public class QuerySysLoginCountPage extends RestApi{
	
	public JSONObject querySysLoginCountPage (String userName, String startLoginTime, String endLoginTime, int pageNum, int pageSize){
		String url =":1511/tarsier-vmdb/cmv/sys/loginlog/querySysLoginCountPage";
		JSONObject param = new JSONObject();
		JSONObject cdt = new JSONObject();
		cdt.put("userName", "%"+userName+"%");
		cdt.put("startLoginTime", startLoginTime);
		cdt.put("endLoginTime", endLoginTime);
		param.put("cdt", cdt);
		param.put("pageNum", pageNum);
		param.put("pageSize", pageSize);
		//param.put("orders", "USER_NAME");//"排序字段默认是按照 用户名排序"
		return this.doRest(url, param.toString(), "POST");
	}
	
}
