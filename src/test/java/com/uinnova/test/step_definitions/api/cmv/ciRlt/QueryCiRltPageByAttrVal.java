package com.uinnova.test.step_definitions.api.cmv.ciRlt;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;

public class QueryCiRltPageByAttrVal extends RestApi{

	public JSONObject queryCiRltPageByAttrVal(String className, List<String> attrNames, String val){
		String url = ":1511/tarsier-vmdb/cmv/ciRlt/queryCiRltPageByAttrVal";
		int pageSize = 1000;
		int pageNum = 1;
		JSONObject param = new JSONObject();
		CiClassUtil ccu = new CiClassUtil();
		JSONArray attrIds = new JSONArray();
		for (int i = 0; i < attrNames.size(); i++) {
			attrIds.put(ccu.getAttrIdByAttrName(className, attrNames.get(i)));
		}
		param.put("pageSize", pageSize);
		param.put("pageNum", pageNum);
		param.put("attrIds", attrIds);
		param.put("val", val);
		
		return doRest(url, param.toString(), "POST");
	}
}
