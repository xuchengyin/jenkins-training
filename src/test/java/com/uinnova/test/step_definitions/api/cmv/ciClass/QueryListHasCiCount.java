package com.uinnova.test.step_definitions.api.cmv.ciClass;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2018-01-30
 * 编写人:sunsl
 * 功能介绍:搜索分类的类
 */
public class QueryListHasCiCount extends RestApi{
	/**
	 * @param classStdCode
	 * @return
	 */
	public JSONObject queryListHasCiCount(String classStdCode){
		String url = ":1511/tarsier-vmdb/cmv/ciClass/queryListHasCiCount";
		JSONObject param = new JSONObject();
		param.put("classStdCode", "%" + classStdCode +"%");
		return doRest(url,param.toString(),"POST");
	}
}
