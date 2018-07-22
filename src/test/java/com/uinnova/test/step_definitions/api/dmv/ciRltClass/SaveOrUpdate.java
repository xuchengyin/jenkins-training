package com.uinnova.test.step_definitions.api.dmv.ciRltClass;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.dmv.ThemeUtil;

/**
 * 编写时间:2018-01-12
 * 编写人:sunsl
 * 功能介绍:修改关系的更新类
 */
public class SaveOrUpdate extends RestApi{
	public JSONObject saveOrUpdate(String rltName,String lineAnime,String lineType,String lineBorder,String lineDirect,String lineColor){
		String url = ":1511/tarsier-vmdb/dmv/ciRltClass/saveOrUpdate";
		ThemeUtil themUtil = new ThemeUtil();
		BigDecimal id = themUtil.getRltClassId(rltName);
		JSONObject param = new JSONObject();
		JSONObject ciClass = new JSONObject();
		ciClass.put("ciType", 2);
		ciClass.put("classCode", rltName);
		ciClass.put("classLvl", 1);
		ciClass.put("className", rltName);
		ciClass.put("classPath", "#10275#");
		ciClass.put("classStdCode","APPRLT");
		ciClass.put("costType",1);
		ciClass.put("dataStatus","1");
		ciClass.put("id",id);
		ciClass.put("isLeaf",1);
		ciClass.put("lineAnime",lineAnime);
		ciClass.put("lineBorder", lineBorder);
		ciClass.put("lineColor", lineColor);
		ciClass.put("lineDirect", lineDirect);
		ciClass.put("lineDispType",1);
		ciClass.put("lineLabelAlign", 3);
		ciClass.put("lineType", lineType);
		ciClass.put("parentId",0);
		param.put("ciClass", ciClass);
		return doRest(url, param.toString(), "POST");
	}
}
