package com.uinnova.test.step_definitions.api.dcv.cframe;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class IsFileExist extends RestApi{
	public JSONObject isFileExist(String fileName){
		String url = ":1511/tarsier-vmdb/dcv/cframe/isFileExist";
		JSONObject parameter = new JSONObject();
		parameter.put("path", "/projects/resources/"+fileName);
		return doRest(url, parameter.toString(), "POST");
		
	}

}
