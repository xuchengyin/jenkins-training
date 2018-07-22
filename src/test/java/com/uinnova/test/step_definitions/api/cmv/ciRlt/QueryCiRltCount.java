package com.uinnova.test.step_definitions.api.cmv.ciRlt;

import java.math.BigDecimal;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.base.CiUtil;

public class QueryCiRltCount extends RestApi{

	/**
	 * @author ldw
	 *此接口替代/cmv/ciRlt/queryCount，原接口可继续使用
	 * */
	
	public JSONObject queryCiRltCount(List<String> ciCode, List<String> className){
		String url = ":1511/tarsier-vmdb/cmv/ciRlt/queryCiRltCount";
//		[
//			{
//				ciId: 源或者目标CI-ID, classId:源或者目标分类ID
//			}
//		]
		JSONArray param = new JSONArray();
		CiClassUtil ccu = new CiClassUtil();
		CiUtil cu = new CiUtil();
		if(ciCode.size() != className.size())return null;
		for (int i = 0; i < ciCode.size(); i++) {
			JSONObject temp = new JSONObject();
			BigDecimal ciId = cu.getCiId(ciCode.get(i));
			BigDecimal classId = ccu.getCiClassId(className.get(i));
			temp.put("ciId", ciId);
			temp.put("classId", classId);
			param.put(temp);
		}
		return doRest(url, param.toString(), "POST");
		
	}
	
	
	public JSONObject queryCiRltCountFailed(List<String> ciCode, List<String> className, String kw){
		String url = ":1511/tarsier-vmdb/cmv/ciRlt/queryCiRltCount";
//		[
//			{
//				ciId: 源或者目标CI-ID, classId:源或者目标分类ID
//			}
//		]
		JSONArray param = new JSONArray();
		CiClassUtil ccu = new CiClassUtil();
		CiUtil cu = new CiUtil();
		if(ciCode.size() != className.size())return null;
		for (int i = 0; i < ciCode.size(); i++) {
			JSONObject temp = new JSONObject();
			BigDecimal ciId = cu.getCiId(ciCode.get(i));
			BigDecimal classId = ccu.getCiClassId(className.get(i));
			temp.put("ciId", ciId);
			temp.put("classId", classId);
			param.put(temp);
		}
		return doFailRest(url, param.toString(), "POST", kw);
		
	}
}
