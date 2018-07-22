package com.uinnova.test.step_definitions.api.dmv.ciRlt;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiUtil;
import com.uinnova.test.step_definitions.utils.base.RltClassUtil;

/**
 * 编写时间:2017-12-11
 * 编写人:sunsl
 * 功能介绍:dmv创建CI关系
 */
public class SaveOrUpdate extends RestApi{
	
	public JSONObject saveOrUpdate(String rltClassName,String sourceCiCode,String targetCiCode){
		String url = ":1511/tarsier-vmdb/dmv/ciRlt/saveOrUpdate";
		RltClassUtil rltClassUtil = new RltClassUtil();
		BigDecimal rltClassId = rltClassUtil.getRltClassId(rltClassName);
		CiUtil ciUtil = new CiUtil();
		BigDecimal sourcID = ciUtil.getCiId(sourceCiCode);
		BigDecimal targerID = ciUtil.getCiId(targetCiCode);
		JSONObject param = new JSONObject();
		JSONObject attrs = new JSONObject();

		param.put("classId",rltClassId );
		param.put("sourceCiId",sourcID );
		param.put("targetCiId",targerID );

		param.put("attrs",attrs );
		return doRest(url, param.toString(), "POST");
	}
}
