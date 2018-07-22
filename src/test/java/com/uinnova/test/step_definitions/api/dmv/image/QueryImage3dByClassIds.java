package com.uinnova.test.step_definitions.api.dmv.image;

import java.math.BigDecimal;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;

/**
 * 编写时间:2017-12-08
 * 编写人:sunsl
 * 功能介绍:DMV我的查看视图根据分类ID查询图片
 */
public class QueryImage3dByClassIds extends RestApi{
    public JSONObject queryImage3dByClassIds(String className){
    	String url=":1511/tarsier-vmdb/dmv/image/queryImage3dByClassIds";
    	JSONObject param = new JSONObject();
    	CiClassUtil ciClassUtil = new CiClassUtil();
    	BigDecimal classId = ciClassUtil.getCiClassId(className);
		JSONArray classIds = new JSONArray();
		classIds.put(classId);
    	param.put("classIds", classIds);
    	return doRest(url, param.toString(), "POST");
    }
}
