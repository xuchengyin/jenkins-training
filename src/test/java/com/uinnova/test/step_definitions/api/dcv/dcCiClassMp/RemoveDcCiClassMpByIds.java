package com.uinnova.test.step_definitions.api.dcv.dcCiClassMp;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class RemoveDcCiClassMpByIds extends RestApi{
	public JSONObject removeDcCiClassMpByIds(ArrayList classMpIds){
		String url = "1511/tarsier-vmdb/dcv/dcCiClassMp/removeDcCiClassMpById";
		JSONObject parameter = new JSONObject();
		JSONArray ids = new JSONArray();
		
		for(int i = 0; i < classMpIds.size(); i++){
			ids.put(classMpIds.get(i));
		}
		return doRest(url, parameter.toString(), "POST");
	}

}
