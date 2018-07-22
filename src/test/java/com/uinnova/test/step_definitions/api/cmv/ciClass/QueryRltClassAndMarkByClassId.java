package com.uinnova.test.step_definitions.api.cmv.ciClass;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
/*
 * 根据两个分类ID不分关系方向查询关系分类信息以及关系相关的目标和源分类标记
 * 
 * 
 * */
public class QueryRltClassAndMarkByClassId extends RestApi{
	public JSONObject queryRltClassAndMarkByClassId(String sourceClassName, String targetClassName, String orders){
		String url = ":1511/tarsier-vmdb/cmv/ciClass/queryRltClassAndMarkByClassId";
		JSONObject param = new JSONObject();
		JSONObject cdt = new JSONObject();
		CiClassUtil ccu = new CiClassUtil();
		BigDecimal sourceClassId = ccu.getCiClassId(sourceClassName);
		BigDecimal targetClassId = ccu.getCiClassId(targetClassName);
		cdt.put("sourceClassId", sourceClassId);
		cdt.put("targetClassId", targetClassId);
		param.put("cdt", cdt);
		param.put("orders", orders);//可选
		return doRest(url, param.toString(), "POST");
	}
	
	public JSONObject queryRltClassAndMarkByClassIdFailed(String sourceClassName, String targetClassName, String orders, String kw){
		String url = ":1511/tarsier-vmdb/cmv/ciClass/queryRltClassAndMarkByClassId";
		JSONObject param = new JSONObject();
		JSONObject cdt = new JSONObject();
		CiClassUtil ccu = new CiClassUtil();
		BigDecimal sourceClassId = ccu.getCiClassId(sourceClassName);
		BigDecimal targetClassId = ccu.getCiClassId(targetClassName);
		cdt.put("sourceClassId", sourceClassId);
		cdt.put("targetClassId", targetClassId);
		param.put("cdt", cdt);
		param.put("orders", orders);//可选
		return doFailRest(url, param.toString(), "POST", kw);
	}
}
