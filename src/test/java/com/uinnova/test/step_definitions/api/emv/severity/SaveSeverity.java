package com.uinnova.test.step_definitions.api.emv.severity;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.EMV.EmvSeverityUtil;
import com.uinnova.test.step_definitions.utils.base.SeverityUtil;

public class SaveSeverity extends RestApi {
	public JSONObject saveSeverity(String severity,String color,String chineseName,String voiceName,String voiceUrl,String englishName,String updateSeverity){
		String url =":1516/monitor-web/severity/saveSeverity";
		JSONObject param = new JSONObject();
		//{"readFlag":false,"flag":true,"severity":"5","isShowColor":false,"color":"#eeece1","chineseName":"5级","voiceName":"5.mp3","voiceUrl":"/122/20180528/100000000014793.mp3","englishName":"five","save":true}
		BigDecimal id = new BigDecimal(0);
		   if(!updateSeverity.equals("")){
			   EmvSeverityUtil emvseverityUtil = new EmvSeverityUtil();
			   id = emvseverityUtil.getIdByServerity(severity);
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
