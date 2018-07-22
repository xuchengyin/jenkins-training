package com.uinnova.test.step_definitions.api.cmv.ciRlt;

import java.math.BigDecimal;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.base.ClassRltUtil;
import com.uinnova.test.step_definitions.utils.base.RltClassUtil;

public class AutosaveOrUpdateBatchByRules extends RestApi{
//根据规则自动构建CI关系
	
	public JSONObject autosaveOrUpdateBatchByRules(String rltClassName, String sourceClassName, String targetClassName, List<String> sourceDefName, List<String> targetDefName){
//		{
//			classRltId: 分类关系线ID,
//			rltClassId: 关系分类的ID,	
//			autoRules:[{  //规则集合
//				sourceDefId: 源CI分类属性定义ID,
//				targetDefId: 目标CI分类属性定义ID
//			}]
//		}
		String url = ":1511/tarsier-vmdb/cmv/ciRlt/autosaveOrUpdateBatchByRules";
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
