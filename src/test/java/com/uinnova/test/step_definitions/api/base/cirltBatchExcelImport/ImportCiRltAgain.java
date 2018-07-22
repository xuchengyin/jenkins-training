package com.uinnova.test.step_definitions.api.base.cirltBatchExcelImport;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.RltClassUtil;

public class ImportCiRltAgain extends RestApi{

	
	public JSONObject importCiRltAgain(String rltClassName, List<String> sourceCiCode, List<String> targetCiCode){
		String url = ":1511/tarsier-vmdb/cmv/cirltBatchExcelImport/importCiRltAgain";
		JSONObject param = new JSONObject();
		RltClassUtil rcu = new RltClassUtil();
		JSONArray datas = new JSONArray();
		if(sourceCiCode.size() != targetCiCode.size())return null;
		for (int i = 0; i < sourceCiCode.size(); i++) {
			JSONObject temp = new JSONObject();
			temp.put("源ci_code", sourceCiCode.get(i));
			temp.put("目标ci_code", targetCiCode.get(i));
			datas.put(temp);
		}
		param.put("datas", datas);
		param.put("classId", rcu.getRltClassId(rltClassName));
		return doRest(url, param.toString(), "POST");
	}
	
	public JSONObject importCiRltAgainFailed(String rltClassName, List<String> sourceCiCode, List<String> targetCiCode, String kw){
		String url = ":1511/tarsier-vmdb/cmv/cirltBatchExcelImport/importCiRltAgain";
		JSONObject param = new JSONObject();
		RltClassUtil rcu = new RltClassUtil();
		JSONArray datas = new JSONArray();
		if(sourceCiCode.size() != targetCiCode.size())return null;
		for (int i = 0; i < sourceCiCode.size(); i++) {
			JSONObject temp = new JSONObject();
			temp.put("源ci_code", sourceCiCode.get(i));
			temp.put("目标ci_code", targetCiCode.get(i));
			datas.put(temp);
		}
		param.put("datas", datas);
		param.put("classId", rcu.getRltClassId(rltClassName));
		return doFailRest(url, param.toString(), "POST", kw);
	}
	
}
