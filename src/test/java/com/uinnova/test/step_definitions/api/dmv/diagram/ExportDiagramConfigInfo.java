package com.uinnova.test.step_definitions.api.dmv.diagram;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.utils.base.CiUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;
import com.uinnova.test.step_definitions.utils.dmv.DiagramUtil;

/**
 * 编写时间:2017-12-08
 * 编写人:sunsl
 * 功能介绍:DMV我的查看视图导出配置信息类
 */
public class ExportDiagramConfigInfo {
	public String exportDiagramConfigInfo(String diagramName,String ciCode){
		String url = ":1511/tarsier-vmdb/dmv/diagram/ExportDiagramConfigInfo";
		JSONObject param = new JSONObject();
		CiUtil ciUtil = new CiUtil();
		JSONArray ids = new JSONArray();
		ids.put(ciUtil.getCiId(ciCode));
		DiagramUtil diagramUtil = new DiagramUtil();
		param.put("id", diagramUtil.getDiagramIdByName(diagramName));
		param.put("ids", ids);
		String result = QaUtil.loadRest(url, param.toString(), "POST");
		return result;	   
	}
}
