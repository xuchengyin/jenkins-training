package com.uinnova.test.step_definitions.api.emv.group;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.EMV.EmvUtil;

import cucumber.api.DataTable;
/*
 * 编写时间:2018-04-10
 * 编写人:yll
 * 功能介绍:判断该用户所在的团队是否有对某些事件源的告警进行确认或关闭的权限
 */
public class Validate extends RestApi{
	public JSONObject validate(int type,  boolean multi, DataTable table){
		   String url =":1516/monitor-web/group/validate";
		  // {"type":1,"multi":true,"serial":["HW1010-2526"]}
		   JSONObject param = new JSONObject();
		   param.put("type", type);//1为单条告警，2为故障
		   param.put("multi", multi);
		   JSONArray serialArr = new JSONArray();
		   EmvUtil emvUtil = new EmvUtil();
		   for (int i =1; i<table.raw().size(); i++){
				 List<String> row = table.raw().get(i);
				 String serial = emvUtil.getSerial(row.get(0), row.get(1));
				 serialArr.put(serial);
			 }
		   param.put("serial", serialArr);
		   return doRest(url, param.toString(), "POST");
	   }
}
