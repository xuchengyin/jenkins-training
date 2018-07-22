package com.uinnova.test.step_definitions.api.dmv.theme;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
/**
 * 编写时间:2017/11/24
 * 编写人:sunsl
 * 功能介绍:查询属性列表
 */
public class QueryThemeInfoList extends RestApi{
	public JSONObject queryThemeInfoList(Integer themeId, Integer fieldType,String searchKey){
		String url=":1511/tarsier-vmdb/dmv/theme/queryThemeInfoList";
		JSONObject param = new JSONObject();		
		JSONObject fieldCdt = new JSONObject();
		JSONObject classCdt = new JSONObject();
		fieldCdt.put("themeId", themeId);
		fieldCdt.put("fieldType",fieldType);
		classCdt.put("className", "%" + searchKey + "%");
		param.put("fieldCdt", fieldCdt);
		param.put("classCdt", classCdt);
		return doRest(url, param.toString(), "POST");
	}
}
