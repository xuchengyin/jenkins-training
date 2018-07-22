package com.uinnova.test.step_definitions.api.base.ci;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;

public class QueryPageByIndex extends RestApi{
	
	/**
	 * 按页索引查询
	 * @param className
	 * @param pageNum
	 * @return
	 */
	public JSONObject queryPageByIndex(String className,int pageNum){
		String url = ":1511/tarsier-vmdb/cmv/ci/queryPageByIndex";
		CiClassUtil cc = new CiClassUtil();
		BigDecimal classId = cc.getCiClassId(className);
		JSONObject queryPageObj = (new QueryPage()).queryPage(className);
		JSONObject param = new JSONObject();
		JSONObject cdt = new JSONObject();
		cdt.put("classId", classId);
		String[] ciQ = {"ATTR"};
		cdt.put("ciQ", ciQ);
		param.put("cdt", cdt);
		param.put("orders", "");
		param.put("pageNum", pageNum);
		param.put("pageSize", 10000);
		param.put("totalPages", queryPageObj.getJSONObject("data").getInt("totalPages"));
		param.put("totalRows", queryPageObj.getJSONObject("data").getInt("totalRows"));
		return doRest(url, param.toString(), "POST");
	}
	
	/**
	 * 按关键字查询ci
	 * @param className
	 * @param keyword
	 * @return
	 */
	public JSONObject queryPageByIndex_ciByKeyword(String className,String keyword){
		String url = ":1511/tarsier-vmdb/cmv/ci/queryPageByIndex";
		CiClassUtil cc = new CiClassUtil();
		BigDecimal classId = cc.getCiClassId(className);
		JSONObject param = new JSONObject();
		JSONObject cdt = new JSONObject();
		cdt.put("classId", classId);
		cdt.put("like", keyword);
		cdt.put("queryClass", 0);
		param.put("cdt", cdt);
		param.put("pageNum", 1);
		param.put("pageSize", 10000);
		return doRest(url, param.toString(), "POST");
	}
	
	
}
