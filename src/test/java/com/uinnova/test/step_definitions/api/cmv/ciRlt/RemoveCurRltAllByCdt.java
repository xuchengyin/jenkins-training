package com.uinnova.test.step_definitions.api.cmv.ciRlt;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.base.CiUtil;
import com.uinnova.test.step_definitions.utils.base.RltClassUtil;

public class RemoveCurRltAllByCdt extends RestApi{
/**
 * @author ldw
 * 
 * 通过源和目标条件删除当前关系下所有关系数据(不分关系方向,双向删除)
 * */
	public JSONObject removeCurRltAllByCdt(String rltClassName, String sourceCiCode, String targetClassName){
		String url = ":1511/tarsier-vmdb/cmv/ciRlt/removeCurRltAllByCdt";
		RltClassUtil rcu = new RltClassUtil();
		CiUtil cu = new CiUtil();
		CiClassUtil ccu = new CiClassUtil();
		JSONObject param = new JSONObject();
		BigDecimal rltClassId = rcu.getRltClassId(rltClassName);
		BigDecimal sourceCiId = cu.getCiId(sourceCiCode);
		BigDecimal targetClassId = ccu.getCiClassId(targetClassName);
		param.put("rltClassId", rltClassId);
		param.put("sourceCiId", sourceCiId);
		param.put("targetClassId", targetClassId);
		return doRest(url, param.toString(), "POST");
	}
	
	
	public JSONObject removeCurRltAllByCdtFailed(String rltClassName, String sourceCiCode, String targetClassName, String kw){
		String url = ":1511/tarsier-vmdb/cmv/ciRlt/removeCurRltAllByCdt";
		RltClassUtil rcu = new RltClassUtil();
		CiUtil cu = new CiUtil();
		CiClassUtil ccu = new CiClassUtil();
		JSONObject param = new JSONObject();
		BigDecimal rltClassId = rcu.getRltClassId(rltClassName);
		BigDecimal sourceCiId = cu.getCiId(sourceCiCode);
		BigDecimal targetClassId = ccu.getCiClassId(targetClassName);
		param.put("rltClassId", rltClassId);
		param.put("sourceCiId", sourceCiId);
		param.put("targetClassId", targetClassId);
		return doFailRest(url, param.toString(), "POST", kw);
	}
}
