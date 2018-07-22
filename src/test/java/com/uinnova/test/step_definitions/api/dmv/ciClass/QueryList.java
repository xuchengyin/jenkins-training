package com.uinnova.test.step_definitions.api.dmv.ciClass;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.dmv.ThemeUtil;

/**
 * 编写时间:2017-11-24
 * 编写人:sunsl
 * 功能介绍:DMV视图查看CI类
 */
public class QueryList extends RestApi{
	public JSONObject queryList(Integer ciType,String className){
		String url=":1511/tarsier-vmdb/dmv/ciClass/queryList";
		ThemeUtil themeUtil = new ThemeUtil();
		JSONObject param = new JSONObject();
		param.put("ciType", ciType);
		param.put("id", themeUtil.getClassId(className));
		return doRest(url, param.toString(), "POST");
	}

	public JSONObject queryList(Integer ciType){
		String url=":1511/tarsier-vmdb/dmv/ciClass/queryList";
		JSONObject param = new JSONObject();
		param.put("ciType", ciType);
		return doRest(url, param.toString(), "POST");
	}

	/**
	 * @return
	 * 查询所有的
	 */
	public JSONObject queryList(){
		String url=":1511/tarsier-vmdb/dmv/ciClass/queryList";
		return doRest(url,"{}", "POST");
	}
}
