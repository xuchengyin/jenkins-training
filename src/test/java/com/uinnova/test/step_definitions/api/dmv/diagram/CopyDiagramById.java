package com.uinnova.test.step_definitions.api.dmv.diagram;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.dmv.DiagramUtil;

/**
 * 编写时间:2017-12-07
 * 编写人:sunsl
 * 功能介绍:DMV我的克隆视图类
 */
public class CopyDiagramById extends RestApi{
	public JSONObject copyDiagramById(String newName,String srcName){
		String url = ":1511/tarsier-vmdb/dmv/diagram/copyDiagramById";
		JSONObject param = new JSONObject();
		DiagramUtil diagramUtil = new DiagramUtil();
		param.put("newName", newName);
		param.put("newDirId", 0);
		param.put("diagramId", diagramUtil.getDiagramIdByName(srcName));
		return doRest(url, param.toString(), "POST");
	}
}
