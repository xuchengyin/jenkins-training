package com.uinnova.test.step_definitions.api.dmv.diagram;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.dmv.MyUtil;

/**
 * 编写时间:2017/12/5
 * 编写人:sunsl
 * 功能介绍:DMV我的将视图移动到文件夹类
 */
public class MoveDirAndDiagram extends RestApi{
	public JSONObject moveDirAndDiagram(JSONArray dirIds, JSONArray diagramIds,String dirName){
		String url =":1511/tarsier-vmdb/dmv/diagram/moveDirAndDiagram";
		JSONObject param = new JSONObject();
		MyUtil myUtil = new MyUtil();
		param.put("targetDirId", myUtil.getDirIdByName(dirName));
		param.put("dirIds", dirIds);
		param.put("diagramIds", diagramIds);
		return doRest(url, param.toString(), "POST");
	}
}
