package com.uinnova.test.step_definitions.api.dcv.dcFile;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class QueryImportHisByCdt extends RestApi{
	public JSONObject queryImportHisByCdt(){
		String url = "1511/tarsier-vmdb/dcv/dcFile/queryImportHisByCdt";
		JSONObject parameter = new JSONObject();
		parameter.put("fileKinds", "[10]");
		return doRest(url, parameter.toString(), "POST");
	}

}
