package com.uinnova.test.step_definitions.api.base.ciRlt;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.RltClassUtil;

public class QueryPageByIndex extends RestApi{

	/**
	 * 按关键字查询rlt
	 * @param rltClsName
	 * @param keyword
	 * @return
	 */
	public JSONObject queryPageByIndex_rltByKeyword(String rltClsName,String keyword){
		String url = ":1511/tarsier-vmdb/cmv/ciRlt/queryPageByIndex";
		BigDecimal rltClsId = (new RltClassUtil()).getRltClassId(rltClsName);
		JSONObject param = new JSONObject();
		JSONObject cdt = new JSONObject();
		cdt.put("classId", rltClsId);
		cdt.put("like", keyword);
		param.put("cdt", cdt);
		param.put("pageNum", 1);
		param.put("pageSize", 30);
		return doRest(url, param.toString(), "POST");
	}

}
