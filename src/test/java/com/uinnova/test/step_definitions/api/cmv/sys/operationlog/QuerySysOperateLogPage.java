package com.uinnova.test.step_definitions.api.cmv.sys.operationlog;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2018-01-25
 * 编写人:孙双利
 * 功能介绍:操作日志的查询类
 */
public class QuerySysOperateLogPage extends RestApi{
  public JSONObject querySysOperateLogPage(String startLogTime,String endLogTime,String userName,String opDesc,List opNames){
	  String url = ":1511/tarsier-vmdb/cmv/sys/operationlog/querySysOperateLogPage";
	  JSONObject param = new JSONObject();
	  JSONObject cdt = new JSONObject();
	  JSONArray opNamesA = new JSONArray();
	  if(opNames != null && opNames.size() > 0){
	     for(int i = 0; i< opNames.size();i++){
		     opNamesA.put(opNames.get(i));
	      }
	     cdt.put("opNames", opNamesA);
	  }
	  if(!startLogTime.isEmpty() && !endLogTime.isEmpty()){
	       cdt.put("startLogTime", startLogTime);
	       cdt.put("endLogTime", endLogTime);
	  }else{
		   cdt.put("startLogTime", "");
	       cdt.put("endLogTime", "");
	  }
	  if(!opDesc.isEmpty()){
		  cdt.put("opDesc", opDesc);
	  }
	  if(!userName.isEmpty()){
		  cdt.put("userName", userName);
	  }
	  param.put("cdt", cdt);
	  param.put("orders", "LOG_TIME DESC");
	  param.put("pageNum", 1);
	  param.put("pageSize", 30);
	  return doRest(url, param.toString(), "POST");
  }
}
