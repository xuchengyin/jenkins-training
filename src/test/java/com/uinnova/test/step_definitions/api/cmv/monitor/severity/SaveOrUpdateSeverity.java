package com.uinnova.test.step_definitions.api.cmv.monitor.severity;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.SeverityUtil;

/**
 * 编写时间:2018-01-19
 * 编写人:sunsl
 * 功能介绍:新建事件级别定义类
 */
public class SaveOrUpdateSeverity extends RestApi{
   public JSONObject saveOrUpdateSeverity(String severity,String color,String voiceName,String chineseName,String englishName,String voiceUrl,String updateSeverity){
	   String url = ":1511/tarsier-vmdb/cmv/monitor/severity/saveOrUpdateSeverity";
	   JSONObject param = new JSONObject();
	   BigDecimal id = new BigDecimal(0);
	   if(!updateSeverity.equals("")){
		   SeverityUtil severityUtil = new SeverityUtil();
		   id = severityUtil.getIdByServerity(severity);
		   param.put("severity", updateSeverity);
		   param.put("id", id.toString());
	   }else{
		  param.put("severity", severity);
	   }
	   param.put("color", color);
	   param.put("voiceName", voiceName);
	   param.put("chineseName", chineseName);
	   param.put("englishName", englishName);
	   param.put("voiceUrl", voiceUrl);
	   return doRest(url, param.toString(), "POST");
   }
}
