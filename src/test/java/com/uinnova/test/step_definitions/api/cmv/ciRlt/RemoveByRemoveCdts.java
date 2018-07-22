package com.uinnova.test.step_definitions.api.cmv.ciRlt;

import java.math.BigDecimal;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiUtil;
import com.uinnova.test.step_definitions.utils.base.RltClassUtil;

public class RemoveByRemoveCdts extends RestApi{
/**
 * @author ldw
 * 
 * 通过源和目标CI-ID删除当前关系下关系数据(不分关系方向,双向删除)
 * */
	public JSONObject removeByRemoveCdts(List<String> rltClassName, List<String> sourceCiCode, List<String> targetCiCode){
		
		if(rltClassName.size() != sourceCiCode.size() && rltClassName.size() != targetCiCode.size())return null;
		String url = ":1511/tarsier-vmdb/cmv/ciRlt/removeByRemoveCdts";
		RltClassUtil rcu = new RltClassUtil();
		CiUtil cu = new CiUtil();
		JSONArray param = new JSONArray();

		for (int i = 0; i < rltClassName.size(); i++) {
			JSONObject temp = new JSONObject();
			BigDecimal rltClassId = rcu.getRltClassId(rltClassName.get(i));
			BigDecimal sourceCiId = cu.getCiId(sourceCiCode.get(i));
			BigDecimal targetCiId = cu.getCiId(targetCiCode.get(i));
			temp.put("rltClassId", rltClassId);
			temp.put("sourceCiId", sourceCiId);
			temp.put("targetCiId", targetCiId);
			param.put(temp);
		}
		//返回值为删除记录数
		return doRest(url, param.toString(), "POST");
		
	}
	
	
	
	public JSONObject removeByRemoveCdtsFailed(List<String> rltClassName, List<String> sourceCiCode, List<String> targetCiCode, String kw){
		
		if(rltClassName.size() != sourceCiCode.size() && rltClassName.size() != targetCiCode.size())return null;
		String url = ":1511/tarsier-vmdb/cmv/ciRlt/removeByRemoveCdts";
		RltClassUtil rcu = new RltClassUtil();
		CiUtil cu = new CiUtil();
		JSONArray param = new JSONArray();

		for (int i = 0; i < rltClassName.size(); i++) {
			JSONObject temp = new JSONObject();
			BigDecimal rltClassId = rcu.getRltClassId(rltClassName.get(i));
			BigDecimal sourceCiId = cu.getCiId(sourceCiCode.get(i));
			BigDecimal targetCiId = cu.getCiId(targetCiCode.get(i));
			temp.put("rltClassId", rltClassId);
			temp.put("sourceCiId", sourceCiId);
			temp.put("targetCiId", targetCiId);
			param.put(temp);
		}
		//返回值为删除记录数
		return doFailRest(url, param.toString(), "POST", kw);
		
	}
}
