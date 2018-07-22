package com.uinnova.test.step_definitions.api.dmv.ci;

import java.math.BigDecimal;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;

/**
 * @author wsl
 *
 */
public class QueryPageByIndex extends RestApi {
	/**
	 * @param queryClass
	 * @param pageNum
	 * @return 查询类信息
	 */
	public JSONObject queryClsPageByIndex(int pageNum, int pageSize) {
		String url = ":1511/tarsier-vmdb/dmv/ci/queryPageByIndex";
		JSONObject param = new JSONObject();
		JSONObject cdt = new JSONObject();
		cdt.put("queryClass", 1);
		String[] ciQs = { "ATTR_DEF" };
		cdt.put("ciQs", ciQs);
		param.put("cdt", cdt);
		param.put("pageNum", pageNum);
		param.put("pageSize", pageSize);
		return doRest(url, param.toString(), "POST");
	}

	/**
	 * @param clsName
	 * @param pageNum
	 * @return 根据类名查询CI的属性定义和值
	 */
	public JSONObject queryCIPageByIndex(String clsName, int pageNum, int pageSize) {
		String url = ":1511/tarsier-vmdb/dmv/ci/queryPageByIndex";
		JSONObject param = new JSONObject();
		JSONObject cdt = new JSONObject();
		CiClassUtil ciClsUtil = new CiClassUtil();
		BigDecimal classId = ciClsUtil.getCiClassId(clsName);
		cdt.put("classId", classId);
		cdt.put("queryClass", 0);
		String[] ciQs = { "ATTR", "ATTR_DEF" };
		cdt.put("ciQs", ciQs);
		param.put("pageNum", pageNum);
		param.put("pageSize", pageSize);
		param.put("cdt", cdt);
		return doRest(url, param.toString(), "POST");
	}

	public JSONObject queryPageByIndex(String searchKey) {
		String url = ":1511/tarsier-vmdb/dmv/ci/queryPageByIndex";
		JSONObject param = new JSONObject();
		JSONObject cdt = new JSONObject();
		JSONArray ciQs = new JSONArray();
		ciQs.put("ATTR_DEF");
		cdt.put("like", searchKey);
		cdt.put("queryClass", 1);
		cdt.put("ciQs", ciQs);
		param.put("pageNum", 1);
		param.put("pageSize", 30);
		param.put("cdt", cdt);
		return doRest(url, param.toString(), "POST");
	}

}
