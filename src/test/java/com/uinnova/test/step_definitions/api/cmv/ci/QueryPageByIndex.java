package com.uinnova.test.step_definitions.api.cmv.ci;

import java.util.ArrayList;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;

public class QueryPageByIndex extends RestApi{

	public JSONObject queryPageByIndex(String ciClassName,String keyWord){
		String url = ":1511/tarsier-vmdb/cmv/ci/queryPageByIndex";
		JSONObject param = new JSONObject();
		CiClassUtil ccu = new CiClassUtil();
		JSONObject cdt = new JSONObject();
		param.put("totalRows", 31);
		param.put("totalPages", 2);
		param.put("pageSize", 30);
		param.put("pageNum", 1);
		cdt.put("classId", ccu.getCiClassId(ciClassName));
		ArrayList ciQ = new ArrayList();
		cdt.put("ciQ",ciQ.add("ATTR"));
		cdt.put("like", keyWord);
		param.put("cdt", cdt);
		return doRest(url, param.toString(), "POST");
		
	}
}
