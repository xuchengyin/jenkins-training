package com.uinnova.test.step_definitions.api.dmv.ciClass;

import java.util.HashMap;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.dmv.ThemeUtil;

/**
 *编写时间:2017-12-04
 *编写人:sunsl
 *功能介绍: 创建动态链接的类
 */
public class SaveOrUpdatePluginAttr extends RestApi{
	public JSONObject saveOrUpdatePluginAttr(String proName,String proVal1,String className,String width,String height){
		String url = ":1511/tarsier-vmdb/dmv/ciClass/saveOrUpdatePluginAttr";
		ThemeUtil themeUtil = new ThemeUtil();
		JSONObject param = new JSONObject();
		String proVal2 = "{\"width\":"+width+ ",\"height\":"+ height +"}";
		param.put("proName", proName);
		param.put("proVal1", proVal1);
		param.put("classId", themeUtil.getClassId(className));
		param.put("proVal2", proVal2);
		return doRest(url, param.toString(), "POST");
	}
	public JSONObject saveOrUpdatePluginAttr(String proName,String updateProName,String proVal1,String className,String updateWidth,String updateHeight){
		String url = ":1511/tarsier-vmdb/dmv/ciClass/saveOrUpdatePluginAttr";
		JSONObject param = new JSONObject();
		ThemeUtil themeUtil = new ThemeUtil();
		HashMap pluginHashMap = themeUtil.getPluginAttr(proName);
		String proVal2 = "{\"width\":"+updateWidth+ ",\"height\":"+ updateHeight +"}";
		param.put("id", pluginHashMap.get("ID"));
		param.put("proName", updateProName);
		param.put("proVal1", proVal1);
		param.put("classId", themeUtil.getClassId(className));
		param.put("proVal2", proVal2);
		return doRest(url, param.toString(), "POST");
	}
}
