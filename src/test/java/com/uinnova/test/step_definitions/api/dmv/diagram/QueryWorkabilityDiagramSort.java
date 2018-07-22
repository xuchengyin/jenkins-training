package com.uinnova.test.step_definitions.api.dmv.diagram;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 *编写时间:2017-12-25
 *编写人:sunsl 
 *功能介绍:绘图-新建视图-ci画图：关系右键菜单添加下钻视图查询类
 */
public class QueryWorkabilityDiagramSort extends RestApi{
     public JSONObject queryWorkabilityDiagramSort(){
    	 String url = ":1511/tarsier-vmdb/dmv/diagram/queryWorkabilityDiagramSort";
    	 JSONObject param = new JSONObject();
    	 JSONArray ids = new JSONArray();
    	 param.put("pageNum", 1);
    	 param.put("pageSize", 10000);
    	 param.put("orders", "modify_Time desc");
    	 param.put("ids", ids);
    	 return doRest(url, param.toString(), "POST");
     }
}
