package com.uinnova.test.step_definitions.api.cmv.monitor.severity;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.SeverityUtil;

/**
 * 编写时间:2018-01-19
 * 编写人:sunsl
 * 功能介绍:删除事件级别定义类
 */
public class RemoveSeverityByIds extends RestApi{
  public JSONObject removeSeverityByIds(ArrayList severityList){
	  SeverityUtil severityUtil = new SeverityUtil();
	  JSONArray ids = new JSONArray();
	  for(int i = 0; i < severityList.size(); i++){
			String severity = (String)severityList.get(i);
			BigDecimal id = severityUtil.getIdByServerity(severity);
			ids.put(id);
		}
	  String url =":1511/tarsier-vmdb/cmv/monitor/severity/removeSeverityByIds";
	  JSONObject param = new JSONObject();
	  param.put("ids", ids);
	  return doRest(url, param.toString(), "POST");
  }
  
  public JSONObject removeSeverityByIds(String severity){
	  String url =":1511/tarsier-vmdb/cmv/monitor/severity/removeSeverityByIds";
	  SeverityUtil severityUtil = new SeverityUtil();
	  BigDecimal id = severityUtil.getIdByServerity(severity);
	  JSONArray ids = new JSONArray();
	  ids.put(id);
	  JSONObject param = new JSONObject();
	  param.put("ids", ids);
	  return doRest(url, param.toString(), "POST");
  }
}
