package com.uinnova.test.step_definitions.api.dmv.ciClass;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.dmv.ThemeUtil;

/**
 * 编写时间:2017-12-04
 * 编写人:sunsl
 * 功能介绍:DMV动态链接删除类
 */
public class RemovePluginAttrByIds extends RestApi{

	public JSONObject removePluginAttrByIds(String updateProName){
		String url = ":1511/tarsier-vmdb/dmv/ciClass/removePluginAttrByIds";
		JSONObject param = new JSONObject();
		JSONArray ids =  new JSONArray();
		ThemeUtil themeUtil = new ThemeUtil();
		HashMap retHashMap = themeUtil.getPluginAttr(updateProName);
		ids.put(retHashMap.get("ID"));
		param.put("ids", ids);
		return doRest(url, param.toString(), "POST");
	}
}
