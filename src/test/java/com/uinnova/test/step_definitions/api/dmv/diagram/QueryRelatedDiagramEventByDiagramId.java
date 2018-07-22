package com.uinnova.test.step_definitions.api.dmv.diagram;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.dmv.DiagramUtil;

/**
 * 编写时间:2018-05-29
 * 编写人:sunsl
 * 功能介绍:查询下钻视图告警
 */
public class QueryRelatedDiagramEventByDiagramId extends RestApi{
   public JSONObject queryRelatedDiagramEventByDiagramId(String diagramName){
	   String url =":1511/tarsier-vmdb/dmv/diagram/queryRelatedDiagramEventByDiagramId";
	   DiagramUtil  diagramUtil = new DiagramUtil();
	   BigDecimal diagramId = diagramUtil.getDiagramIdByName(diagramName);
	   return doRest(url,diagramId.toString(),"POST");
   }
}
