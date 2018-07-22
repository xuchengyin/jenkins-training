package com.uinnova.test.step_definitions.api.dmv.theme;

import java.math.BigDecimal;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-11-24
 * 编写人:sunsl
 * 功能介绍:DMV设置里添加属性类
 */
public class SaveOrUpdateThemeField extends RestApi{
	public JSONObject saveOrUpdateThemeField(Integer themeId,Integer fieldType,BigDecimal classId,JSONArray themeFields){
		String url=":1511/tarsier-vmdb/dmv/theme/saveOrUpdateThemeField";
		JSONObject param = new JSONObject();
		param.put("themeId", themeId);
		param.put("fieldType", fieldType);
		param.put("classId", classId);
		param.put("themeFields", themeFields);
		return doRest(url, param.toString(), "POST");
	}
}
