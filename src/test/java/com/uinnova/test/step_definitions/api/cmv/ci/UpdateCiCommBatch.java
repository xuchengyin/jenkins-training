package com.uinnova.test.step_definitions.api.cmv.ci;

import java.math.BigDecimal;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.base.CiUtil;

import cucumber.api.DataTable;

public class UpdateCiCommBatch extends RestApi{

	public JSONObject updateCiCommBatch(DataTable dt){
		String url = ":1511/tarsier-vmdb/cmv/ci/updateCiCommBatch";
		List<List<String>> list = dt.raw();
		CiUtil cu = new CiUtil();
		CiClassUtil ccu = new CiClassUtil();
		BigDecimal classId = ccu.getCiClassId(list.get(1).get(0));
		String ciCodes = list.get(1).get(1);
		String attrsString = list.get(1).get(2);
		String [] ciCodeArray = ciCodes.split(":");
		JSONArray ciIds = new JSONArray();
		for(int i = 0; i < ciCodeArray.length; i++){
			ciIds.put(cu.getCiId(ciCodeArray[i]));
		}
		String [] attrsArray = attrsString.split(",");
		JSONObject attrs = new JSONObject();
		for(int i = 0; i < attrsArray.length; i++){
			String[] tempKV = attrsArray[i].split(":");
			attrs.put(tempKV[0], tempKV[1]);
		}
		JSONObject param = new JSONObject();
		param.put("classId", classId);
		param.put("ciIds", ciIds);
		param.put("attrs", attrs);
		return doRest(url, param.toString(), "POST");
	}
}
