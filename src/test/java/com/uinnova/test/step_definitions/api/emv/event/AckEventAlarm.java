package com.uinnova.test.step_definitions.api.emv.event;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.EMV.EmvUtil;

import cucumber.api.DataTable;
/*
 * 编写时间:2018-04-10
 * 编写人:yll
 * 功能介绍:确认单条告警
 */
public class AckEventAlarm extends RestApi{
	public JSONObject ackEventAlarm(String ackInfo,DataTable table){
		String url =":1516/monitor-web/event/ackEventAlarm";
		JSONObject param = new JSONObject();
		param.put("ackInfo", ackInfo);//确认描述信息
		param.put("continued",0);//该参数已经不用，写死0
		JSONArray serialArr = new JSONArray();
		EmvUtil emvUtil = new EmvUtil();
		for (int i =1; i<table.raw().size(); i++){
			List<String> row = table.raw().get(i);
			String serial = emvUtil.getSerial(row.get(0), row.get(1));
			serialArr.put(serial);
		}
		param.put("serials", serialArr);
		return doRest(url, param.toString(), "POST");
	}

}

