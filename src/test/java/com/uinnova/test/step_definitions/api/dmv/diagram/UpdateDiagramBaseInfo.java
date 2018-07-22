package com.uinnova.test.step_definitions.api.dmv.diagram;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.dmv.DiagramUtil;

/**
 * 编写时间:2018-02-01
 * 编写人:sunsl
 * 功能介绍:看图更新视图描述类
 */
public class UpdateDiagramBaseInfo extends RestApi{
     public JSONObject updateDiagramBaseInfo(String diagramName,String diagramDesc){
    	 String url = ":1511/tarsier-vmdb/dmv/diagram/updateDiagramBaseInfo";
    	 DiagramUtil  diagramUtil = new DiagramUtil();
    	 BigDecimal id = diagramUtil.getDiagramIdByName(diagramName);
    	 JSONObject param = new JSONObject();
    	 param.put("id", id);
    	 param.put("diagramDesc", diagramDesc);
    	 return doRest(url,param.toString(),"POST");
     }
}
