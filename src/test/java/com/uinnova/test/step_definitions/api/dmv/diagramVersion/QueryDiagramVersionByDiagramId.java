package com.uinnova.test.step_definitions.api.dmv.diagramVersion;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.dmv.DiagramUtil;

/**
 * 编写时间:2017-12-07
 * 编写人:sunsl
 * 功能介绍:根据视图Id查询视图历史版本
 */
public class QueryDiagramVersionByDiagramId extends RestApi{
	public JSONObject queryDiagramVersionByDiagramId(String diagramName){
         String url=":1511/tarsier-vmdb/dmv/diagramVersion/queryDiagramVersionByDiagramId";
         DiagramUtil diagramUtil = new DiagramUtil();
         BigDecimal diagramId = diagramUtil.getDiagramIdByName(diagramName);
         return doRest(url, String.valueOf(diagramId), "POST");
	}
     
}
