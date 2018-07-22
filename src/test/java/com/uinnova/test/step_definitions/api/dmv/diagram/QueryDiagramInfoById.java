package com.uinnova.test.step_definitions.api.dmv.diagram;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.dmv.DiagramUtil;

/**
 * 编写时间:2017-11-15
 * 编写人:sunsl
 * 功能介绍:根据视图ID查询视图详情类
 */
public class QueryDiagramInfoById extends RestApi{
   public JSONObject queryDiagramInfoById(String diagramName, boolean retEles){
	   String url =":1511/tarsier-vmdb/dmv/diagram/queryDiagramInfoById";
	   JSONObject param = new JSONObject();
	   DiagramUtil diagramUtil = new DiagramUtil();
	   BigDecimal diagramId = diagramUtil.getDiagramIdByName(diagramName);
	   param.put("id", String.valueOf(diagramId));
	   param.put("retEles", retEles);
	   return doRest(url, param.toString(), "POST");
   }
}
