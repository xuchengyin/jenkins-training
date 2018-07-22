package com.uinnova.test.step_definitions.api.cmv.ciRlt;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.base.CiUtil;
import com.uinnova.test.step_definitions.utils.base.RltClassUtil;

public class QueryFuzzyMatchPathInCis extends RestApi{
	
	public JSONObject queryFuzzyMatchPathInCis(List<String> startCiCodes,List<String> endCiCodes,List<String> filterCiClassNames,List<String> rltLevels,int depth){
//		{
//			  sCiIds:[200000000000006,100000000000002],	//起点CI的ids
//			  tCiIds:[100000000000019,100000000000021], //终点CI的ids
//			  filterCiClassIds:[200000000000008,100000000000001,100000000000002], //必须经过的分类,没有则全部分类都可以
//			  rltLvls:[2,1], //关系的级别
//			  depth:1 //假设以a为起点 1 : a-b ,2 : a-b-c ,3 : a-b-c-d
//			}	
		String url = ":1511/tarsier-vmdb/cmv/ciRlt/queryFuzzyMatchPathInCis";
		JSONObject param = new JSONObject();
		CiClassUtil ccu = new CiClassUtil();
//		RltClassUtil rcu = new RltClassUtil();
		CiUtil cu = new CiUtil();
		ArrayList<BigDecimal> startCiIds = new ArrayList();
		ArrayList<BigDecimal> endCiIds = new ArrayList();
		ArrayList<BigDecimal> filterCiClassIds = new ArrayList();
		ArrayList rltLevel = new ArrayList();
		for(int i = 0; i < startCiCodes.size(); i++){
			startCiIds.add(cu.getCiId(startCiCodes.get(i)));
		}
		for(int i = 0; i < endCiCodes.size(); i++){
			endCiIds.add(cu.getCiId(endCiCodes.get(i)));
		}
		for(int i = 0; i < filterCiClassNames.size(); i++){
			filterCiClassIds.add(ccu.getCiClassId(filterCiClassNames.get(i)));
		}
		for(int i = 0; i < rltLevels.size(); i++){
			rltLevel.add(Integer.parseInt(rltLevels.get(i)));
		}
		param.put("sCiIds", startCiIds);
		param.put("tCiIds", endCiIds);
		param.put("filterCiClassIds", filterCiClassIds);
		param.put("rltLvls", rltLevel);
		param.put("depth", depth);
		return doRest(url, param.toString(), "POST");
	}
}
