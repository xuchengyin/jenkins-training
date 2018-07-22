package com.uinnova.test.step_definitions.api.emv.incident;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/*
 * 编写时间:2018-04-09
 * 编写人:sunsl
 * 功能介绍:EMV已关注事件台全部故障活动故障查询类
 */
public class SelectIncidentPageList extends RestApi{
	//查询活动故障,确认故障,根据输入内容查询
    public JSONObject selectIncidentPageList(int status, int acknowledged,String searchKey){
    	String url =":1516/monitor-web/incident/selectIncidentPageList";
    	JSONObject cdt = new JSONObject();
    	JSONObject param = new JSONObject();
    	cdt.put("status", status);
    	cdt.put("acknowledged", acknowledged);
    	cdt.put("incidentName", searchKey);
    	param.put("cdt", cdt);
    	param.put("pageNum", 1);
    	param.put("pageSize", 50);
    	return doRest(url,param.toString(),"POST");
    }
    //查询活动故障,确认故障的全部查询
    public JSONObject selectIncidentPageList(int status, int acknowledged){
    	String url =":1516/monitor-web/incident/selectIncidentPageList";
    	JSONObject cdt = new JSONObject();
    	JSONObject param = new JSONObject();
    	cdt.put("status", status);
    	cdt.put("acknowledged", acknowledged);
    	param.put("cdt", cdt);
    	param.put("pageNum", 1);
    	param.put("pageSize", 50);
    	return doRest(url,param.toString(),"POST");
    }
    //查询关闭故障的全部查询
    public JSONObject selectIncidentPageList(int status){
    	String url =":1516/monitor-web/incident/selectIncidentPageList";
    	JSONObject cdt = new JSONObject();
    	JSONObject param = new JSONObject();
    	cdt.put("status", status);
    	param.put("cdt", cdt);
    	param.put("pageNum", 1);
    	param.put("pageSize", 50);
    	return doRest(url,param.toString(),"POST");
    }
}
