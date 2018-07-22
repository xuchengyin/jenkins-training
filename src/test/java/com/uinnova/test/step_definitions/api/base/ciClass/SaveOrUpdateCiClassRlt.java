package com.uinnova.test.step_definitions.api.base.ciClass;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.base.RltClassUtil;

/**
 * @author wsl
 * 可视化建模保存
 */
public class SaveOrUpdateCiClassRlt extends RestApi{
	public JSONObject saveOrUpdateCiClassRlt(String sourceClassName, String targetClassName, String rltClassName) {
		CiClassUtil ciClassUtil = new CiClassUtil();
		BigDecimal sourceClassId = ciClassUtil.getCiClassId(sourceClassName);
		BigDecimal targetClassId = ciClassUtil.getCiClassId(targetClassName);
		RltClassUtil rltClassUtil = new RltClassUtil();
		BigDecimal rltClassId = rltClassUtil.getRltClassId(rltClassName);
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("sourceClassId", sourceClassId);
		jsonObj.put("targetClassId", targetClassId);
		jsonObj.put("classId", rltClassId);
		String url = ":1511/tarsier-vmdb/cmv/ciClass/saveOrUpdateCiClassRlt";
		return doRest(url, jsonObj.toString(), "POST");
	}

	public JSONObject saveOrUpdateCiClassRltFail(String sourceClassName, String targetClassName, String rltClassName, String kw) {
		CiClassUtil ciClassUtil = new CiClassUtil();
		BigDecimal sourceClassId = ciClassUtil.getCiClassId(sourceClassName);
		BigDecimal targetClassId = ciClassUtil.getCiClassId(targetClassName);
		RltClassUtil rltClassUtil = new RltClassUtil();
		BigDecimal rltClassId = rltClassUtil.getRltClassId(rltClassName);
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("sourceClassId", sourceClassId);
		jsonObj.put("targetClassId", targetClassId);
		jsonObj.put("classId", rltClassId);
		String url = ":1511/tarsier-vmdb/cmv/ciClass/saveOrUpdateCiClassRlt";
		return doFailRest(url, jsonObj.toString(), "POST", kw);
	}
}
