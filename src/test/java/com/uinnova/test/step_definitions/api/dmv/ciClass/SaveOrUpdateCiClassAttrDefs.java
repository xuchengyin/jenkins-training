package com.uinnova.test.step_definitions.api.dmv.ciClass;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.dmv.ThemeUtil;

/**
 * 编写时间:2017-11-24
 * 编写人:sunsl
 * 功能介绍:移动分类属性顺序
 */
public class SaveOrUpdateCiClassAttrDefs extends RestApi{
	public JSONObject saveOrUpdateCiClassAttrDefs(String className,JSONArray attrDefs){
		String url=":1511/tarsier-vmdb/dmv/ciClass/saveOrUpdateCiClassAttrDefs";
		ThemeUtil themeUtil = new ThemeUtil();
		JSONObject param = new JSONObject();
		param.put("classId", themeUtil.getClassId(className));
		param.put("attrDefs", attrDefs);
		return doRest(url, param.toString(), "POST");
	}
}
