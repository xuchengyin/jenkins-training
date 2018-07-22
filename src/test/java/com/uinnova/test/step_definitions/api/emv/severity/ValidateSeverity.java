package com.uinnova.test.step_definitions.api.emv.severity;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class ValidateSeverity extends RestApi{
	public JSONObject validateSeverity(String severity){
		  String url =":1516/monitor-web/severity/validateSeverity";
		  JSONObject param = new JSONObject();
		  param.put("severity", severity);
		  return doRest(url, param.toString(), "POST");
	  }
	
	public JSONObject validateSeverityAgain(String severity, String kw){
		  String url =":1516/monitor-web/severity/validateSeverity";
		  JSONObject param = new JSONObject();
		  param.put("severity", severity);
		  return doFailRest(url, param.toString(), "POST", kw);
	  }

}
