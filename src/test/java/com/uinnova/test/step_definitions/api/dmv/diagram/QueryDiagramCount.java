package com.uinnova.test.step_definitions.api.dmv.diagram;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2018-04-24
 * 编写人:sunsl
 * 功能介绍:广场查询视图数 
 */
public class QueryDiagramCount extends RestApi{
    public JSONObject queryDiagramCount(){
    	String url = ":1511/tarsier-vmdb/dmv/diagram/queryDiagramCount";
    	JSONObject param = new JSONObject();
    	JSONArray diagramTypes = new JSONArray();
    	diagramTypes.put(1);
    	diagramTypes.put(3);
    	param.put("diagramTypes", diagramTypes);
    	return doRest(url,param.toString(),"POST");
    }
}
