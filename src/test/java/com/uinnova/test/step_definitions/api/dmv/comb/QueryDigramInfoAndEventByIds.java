package com.uinnova.test.step_definitions.api.dmv.comb;

import java.math.BigDecimal;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.dmv.DiagramUtil;

/**
 * 编写时间:2018-04-10
 * 编写人:sunsl
 * 功能介绍:DMV查询告警类
 */
public class QueryDigramInfoAndEventByIds extends RestApi{
    public JSONObject queryDigramInfoAndEventByIds(List diagramNameList){
    	String url = ":1511/tarsier-vmdb/dmv/comb/queryDiagramInfoAndEventByIds";
    	
    	DiagramUtil diagramUtil = new DiagramUtil();
    	JSONObject param = new JSONObject();
    	JSONArray ids = new JSONArray();
    	for(int i = 0; i < diagramNameList.size(); i++){
    	   String diagramName = (String)diagramNameList.get(i);
    	   BigDecimal diagramId = diagramUtil.getDiagramIdByName(diagramName);
    	   ids.put(diagramId);
    	}
    	param.put("ids", ids);
    	return doRest(url,param.toString(),"POST");
    }
}
