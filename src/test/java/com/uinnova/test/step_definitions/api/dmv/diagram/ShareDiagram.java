package com.uinnova.test.step_definitions.api.dmv.diagram;

import java.math.BigDecimal;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.dmv.DiagramUtil;
import com.uinnova.test.step_definitions.utils.dmv.GroupUtil;

/**
 * 编写时间:2017-11-15
 * 编写人:sunsl
 * 功能介绍:将视图发布到广场,分享到小组类
 */
public class ShareDiagram extends RestApi{
	/**
	 * @param diagramName
	 * @return 
	 */
	public JSONObject shareDiagram(String diagramName){
		String url =":1511/tarsier-vmdb/dmv/diagram/shareDiagram";
		JSONObject param = new JSONObject();
		DiagramUtil diagramUtil = new DiagramUtil();
		BigDecimal diagramId = diagramUtil.getDiagramIdByName(diagramName);
		param.put("diagramIds", diagramId);
		param.put("isOpen", 1);
		return doRest(url, param.toString(), "POST");
	}

	public JSONObject shareDiagram(String diagramName,String groupName){
		String url = ":1511/tarsier-vmdb/dmv/diagram/shareDiagram";
		DiagramUtil diagramUtil = new DiagramUtil();
		JSONObject param = new JSONObject();
		JSONArray groupIds = new JSONArray();
		GroupUtil groupUtil = new GroupUtil();
		//groupIds.put(Scenario_group.groupId.intValue());
		groupIds.put(groupUtil.getGroupId(groupName));
		param.put("diagramId", diagramUtil.getDiagramIdByName(diagramName));
		param.put("groupIds", groupIds);
		return doRest(url, param.toString(), "POST");
	}
	public JSONObject shareDiagram(String diagramName,JSONArray groupIdArray){
		String url = ":1511/tarsier-vmdb/dmv/diagram/shareDiagram";
		DiagramUtil diagramUtil = new DiagramUtil();
		JSONObject param = new JSONObject();
		GroupUtil groupUtil = new GroupUtil();
		param.put("diagramId", diagramUtil.getDiagramIdByName(diagramName));
		param.put("groupIds", groupIdArray);
		return doRest(url, param.toString(), "POST");
	}
}
