package com.uinnova.test.step_definitions.api.cmv.ciRlt;

import java.math.BigDecimal;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.base.ClassRltUtil;
import com.uinnova.test.step_definitions.utils.base.RltClassUtil;

public class AutosaveOrUpdateBatchRlt extends RestApi{

	/**
	 * 根据规则自动构建CI关系,同时保存规则(异步构建：获取进程进度key:autosaveRlt)
	 * 注：防止数据量过大超时，另开进程，保存结果需要调用查询进程结果的接口获取，定时查询知道有结果为止，或者可以跳过
	 * 
	 * @author ldw
	 * @param 与AutosaveOrUpdateBatchByRules相同
	 * @return null
	 * 
	 * @category
	 * */
	
	public JSONObject autosaveOrUpdateBatchRlt(String rltClassName, String sourceClassName, String targetClassName, List<String> sourceDefName, List<String> targetDefName){
		String url = ":1511/tarsier-vmdb/cmv/ciRlt/autosaveOrUpdateBatchRlt";
		RltClassUtil rcu = new RltClassUtil();
		ClassRltUtil cru = new ClassRltUtil();
		CiClassUtil ccu = new CiClassUtil();
		BigDecimal rltClassId = rcu.getRltClassId(rltClassName);
		BigDecimal classRltId = cru.getClassRltId(sourceClassName, targetClassName, rltClassName);
		JSONObject param = new JSONObject();
		param.put("classRltId", classRltId);
		param.put("rltClassId", rltClassId);
		JSONArray autoRules = new JSONArray();
		if(sourceDefName.size() != targetDefName.size()) return null;
		for (int i = 0; i < sourceDefName.size(); i++) {
			JSONObject temp = new JSONObject();
			temp.put("sourceDefId", ccu.getAttrIdByAttrName(sourceClassName, sourceDefName.get(i)));
			temp.put("targetDefId", ccu.getAttrIdByAttrName(targetClassName, targetDefName.get(i)));
			autoRules.put(temp);
		}
		param.put("autoRules", autoRules);
		
		return doRest(url, param.toString(), "POST");
	}
}
