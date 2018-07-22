package com.uinnova.test.step_definitions.api.base.ciRltClass;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.testcase.base.rlt.rltClass.Scenario_rltClass;
import com.uinnova.test.step_definitions.utils.common.TxtUtil;

public class QueryList extends RestApi{

	/**
	 * @param searchKey
	 * @return
	 */
	public JSONObject queryList(String searchKey){
		String url = ":1511/tarsier-vmdb/cmv/ciRltClass/queryList";
		if(searchKey.indexOf(".txt")>0){
			String filePath = Scenario_rltClass.class.getResource("/").getPath()+"testData/rlt/"+searchKey;
			searchKey = (new TxtUtil()).readTxt(filePath);
		}
		JSONObject  classStdCode = new JSONObject();
		classStdCode.put("classStdCode", "%"+searchKey.trim().toUpperCase()+"%");
		return doRest(url, classStdCode.toString(), "POST");
	}
}
