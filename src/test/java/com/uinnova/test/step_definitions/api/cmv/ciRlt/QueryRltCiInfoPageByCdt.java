package com.uinnova.test.step_definitions.api.cmv.ciRlt;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.base.CiUtil;
import com.uinnova.test.step_definitions.utils.base.RltClassUtil;

public class QueryRltCiInfoPageByCdt extends RestApi{
/**
 * @author ldw
 * 此接口替代/cmv/ciRlt/queryRltClassCiInfoPage
 * 
 * */
	public JSONObject queryRltCiInfoPageByCdt(String rltClassName, String ciCode, String className, String orders){
		RltClassUtil rcu = new RltClassUtil();
		CiClassUtil ccu = new CiClassUtil();
		CiUtil cu = new CiUtil();
		JSONObject param = new JSONObject();
		String url = ":1511/tarsier-vmdb/cmv/ciRlt/queryRltCiInfoPageByCdt";
//		{
//			cdt{ rltClassId:关系分类ID, ciId:ci-id, classId: CI分类ID},
//			pageNum:查询页码,
//			pageSize: 每页显示数,
//			orders: 排序字段,可选
//			}
		BigDecimal rltClassId = rcu.getRltClassId(rltClassName);
		BigDecimal ciId = cu.getCiId(ciCode);
		BigDecimal classId = ccu.getCiClassId(className);
		JSONObject cdt = new JSONObject();
		cdt.put("rltClassId", rltClassId);
		cdt.put("ciId", ciId);
		cdt.put("classId", classId);
		param.put("cdt", cdt);
		param.put("pageNum", 1);
		param.put("pageSize", 10000);
		param.put("orders", orders);
		return doRest(url, param.toString(), "POST");
	}
	
	
	public JSONObject queryRltCiInfoPageByCdtFailed(String rltClassName, String ciCode, String className, String orders, String kw){
		RltClassUtil rcu = new RltClassUtil();
		CiClassUtil ccu = new CiClassUtil();
		CiUtil cu = new CiUtil();
		JSONObject param = new JSONObject();
		String url = ":1511/tarsier-vmdb/cmv/ciRlt/queryRltCiInfoPageByCdt";
		BigDecimal rltClassId = rcu.getRltClassId(rltClassName);
		BigDecimal ciId = cu.getCiId(ciCode);
		BigDecimal classId = ccu.getCiClassId(className);
		JSONObject cdt = new JSONObject();
		cdt.put("rltClassId", rltClassId);
		cdt.put("ciId", ciId);
		cdt.put("classId", classId);
		param.put("cdt", cdt);
		param.put("pageNum", 1);
		param.put("pageSize", 10000);
		param.put("orders", orders);
		return doFailRest(url, param.toString(), "POST", kw);
	}
}
