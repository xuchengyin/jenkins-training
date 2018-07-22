package com.uinnova.test.step_definitions.api.cmv.ciRlt;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.base.ClassRltUtil;
import com.uinnova.test.step_definitions.utils.base.RltClassUtil;

public class QueryCiRltRuleList extends RestApi{
//不分页查询自动构建关系数据的规则信息
	public JSONObject queryCiRltRuleList(String rltClassName, List<String> sourceCiClassNames, List<String> targetCiClassNames, int type){
		
		String url = ":1511/tarsier-vmdb/cmv/ciRlt/queryCiRltRuleList";
//		{
//			rltClassId: 关系分类的ID,
//			sourceCiClassIds: 源CI分类ID是数组,
//			targetCiClassIds: 目标CI分类ID数组
//
//			或者
//			classRltId: 分类关系线ID
//
//			}
		CiClassUtil ccu = new CiClassUtil();
		RltClassUtil rcu = new RltClassUtil();
		ClassRltUtil cru = new ClassRltUtil();
		JSONObject param = new JSONObject();
		if (type == 0) {
			if(sourceCiClassNames.size() != targetCiClassNames.size())return null;
			param.put("rltClassId", rcu.getRltClassId(rltClassName));
			ArrayList sourceCiClassIds = new ArrayList();
			ArrayList targetCiClassIds = new ArrayList();
			for (int i = 0; i < sourceCiClassNames.size(); i++) {
				sourceCiClassIds.add(ccu.getCiClassId(sourceCiClassNames.get(i)));
				targetCiClassIds.add(ccu.getCiClassId(targetCiClassNames.get(i)));
			}
			param.put("sourceCiClassIds", sourceCiClassIds);
			param.put("targetCiClassIds", targetCiClassIds);
		} else {
			param.put("classRltId", cru.getClassRltId(sourceCiClassNames.get(0), targetCiClassNames.get(0), rltClassName));
		}
		return doRest(url, param.toString(), "POST");
	}
}
