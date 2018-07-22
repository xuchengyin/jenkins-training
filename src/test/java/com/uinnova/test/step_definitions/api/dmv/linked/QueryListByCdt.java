package com.uinnova.test.step_definitions.api.dmv.linked;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.dmv.DiagramUtil;

/**
 * 编写时间:2018-01-08
 * 编写人:sunsl
 * 功能介绍:查询生成链路类
 */
public class QueryListByCdt extends RestApi{
  public JSONObject queryListByCdt(String diagramName){
	  String url =":1511/tarsier-vmdb/dmv/linked/queryListByCdt";
	  DiagramUtil diagramUtil = new DiagramUtil();
	  BigDecimal sourceId = diagramUtil.getDiagramIdByName(diagramName);
	  JSONObject param = new JSONObject();
	  JSONObject cdt = new JSONObject();
	  cdt.put("sourceId", sourceId);
	  param.put("cdt", cdt);
	  return doRest(url, param.toString(), "POST");
  }
}
