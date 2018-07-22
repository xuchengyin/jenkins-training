package com.uinnova.test.step_definitions.api.dmv.diagram;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.dmv.DiagramUtil;

/**
 * 编写时间:2017-12-13
 * 编写人:sunsl
 * 功能介绍：DMV我的查看视图查询用户类
 */
public class QueryUserEditAuthByDiagramId extends RestApi{
    public JSONObject queryUserEditAuthByDiagramId(String diagramName){
    	String url = ":1511/tarsier-vmdb/dmv/diagram/queryUserEditAuthByDiagramId";
    	DiagramUtil diagramUtil = new DiagramUtil();
    	return doRest(url, String.valueOf(diagramUtil.getDiagramIdByName(diagramName)), "POST");
    }
    
}
