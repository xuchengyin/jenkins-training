package com.uinnova.test.step_definitions.api.dmv.diagram;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.dmv.DiagramUtil;

/**
 * 编写时间:2017-12-05
 * 编写人:sunsl
 * 功能介绍:DMV视图重命名类
 */
public class UpdateDiagramNameAndDirById extends RestApi{
	public JSONObject updateDiagramNameAndDirById(String rename,String diagramName){
		String  url = ":1511/tarsier-vmdb/dmv/diagram/updateDiagramNameAndDirById";
		JSONObject param = new JSONObject();
		DiagramUtil diagramUtil = new DiagramUtil();
		param.put("newName", rename);
		param.put("newDirId", 0);
		param.put("diagramId", diagramUtil.getDiagramIdByName(diagramName));
		return doRest(url, param.toString(), "POST");
	}
}
