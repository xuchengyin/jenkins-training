package com.uinnova.test.step_definitions.api.dmv.theme;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.dmv.ThemeUtil;

/**
 * 编写时间:2017-11-24
 * 编写人:sunsl
 * 功能介绍:DMV删除配置类
 */
public class RemoveThemeField extends RestApi{
	public JSONObject removeThemeField(Integer themeId,Integer fieldType,String className){
		String url = ":1511/tarsier-vmdb/dmv/theme/removeThemeField";
		ThemeUtil themeUtil = new ThemeUtil();
		JSONObject param = new JSONObject();
		param.put("themeId",themeId);
		param.put("classId", themeUtil.getClassId(className));
		param.put("fieldType",fieldType);
		return doRest(url, param.toString(), "POST");
	}
}
