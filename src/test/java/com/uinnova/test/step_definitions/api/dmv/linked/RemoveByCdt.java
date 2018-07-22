package com.uinnova.test.step_definitions.api.dmv.linked;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.dmv.DiagramUtil;

/**
 * 编写时间:2018-01-08
 * 编写人:sunsl
 * 功能介绍:删除生成链路类
 */
public class RemoveByCdt extends RestApi{
    public JSONObject removeByCdt(String diagramName){
    	String url = ":1511/tarsier-vmdb/dmv/linked/removeByCdt";
    	DiagramUtil diagramUtil = new DiagramUtil();
    	BigDecimal sourId = diagramUtil.getDiagramIdByName(diagramName);
    	JSONObject param = new JSONObject();
    	param.put("sourId", sourId);
    	return doRest(url, param.toString(), "POST");
    }
}
