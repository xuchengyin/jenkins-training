package com.uinnova.test.step_definitions.api.dmv.diagram;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.dmv.DiagramUtil;

/**
 * 编写时间:2017-12-23
 * 编写人:sunsl
 * 功能介绍:将视图从一个小组移到另一个小组
 */
public class RemoveDiagramGroupByDiagramID extends RestApi{
   public JSONObject removeDiagramGroupByDiagramID(String diagramName){
	   String url =":1511/tarsier-vmdb/dmv/diagram/removeDiagramGroupByDiagramID";
	   DiagramUtil diagramUtil = new DiagramUtil();
	   
	   JSONObject param = new JSONObject();
	   param.put("diagramId", diagramUtil.getDiagramIdByName(diagramName));
	   return doRest(url, param.toString(), "POST");
   }
}
