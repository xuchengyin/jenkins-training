package com.uinnova.test.step_definitions.api.emv.severity;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.EMV.EmvSeverityUtil;
import com.uinnova.test.step_definitions.utils.base.SeverityUtil;

public class DelSeverity extends RestApi {
//	public JSONObject delSeverity(ArrayList severityList){
//		  SeverityUtil severityUtil = new SeverityUtil();
//		  JSONArray ids = new JSONArray();
//		  for(int i = 0; i < severityList.size(); i++){
//				String severity = (String)severityList.get(i);
//				BigDecimal id = severityUtil.getIdByServerity(severity);
//				ids.put(id);
//			}
//		  String url =":1511/tarsier-vmdb/cmv/monitor/severity/removeSeverityByIds";
//		  JSONObject param = new JSONObject();
//		  param.put("ids", ids);
//		  return doRest(url, param.toString(), "POST");
//	  }
	//[100000000004027]
	  public JSONObject delSeverity(String severity){
		  String url =":1516/monitor-web/severity/delSeverity";
		  EmvSeverityUtil severityUtil = new EmvSeverityUtil();
		  BigDecimal id = severityUtil.getIdByServerity(severity);
		  JSONArray param = new JSONArray();
		  param.put(id);
		  //System.out.println(param);
		  return doRest(url, param.toString(), "POST");
	  }
	

}
