package com.uinnova.test.step_definitions.api.dmv.diagram;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;
import com.uinnova.test.step_definitions.utils.dmv.DiagramUtil;

/**
 * 编写时间:2017-12-06
 * 编写人:sunsl
 * 功能介绍:DMV回收站还原视图类
 */
public class RestoreRecycleBinDiagram extends RestApi{
	public JSONObject restoreRecycleBinDiagram(String diagramName){
		String url = ":1511/tarsier-vmdb/dmv/diagram/restoreRecycleBinDiagram";
		JSONObject param = new JSONObject();
		String sql = "select ID from vc_diagram where DATA_STATUS=1 and STATUS=0  and NAME = '"+diagramName+"'  and DOMAIN_ID = "+ QaUtil.domain_id;
		List idList = JdbcUtil.executeQuery(sql);
		BigDecimal id = new BigDecimal(0);
		if (idList!=null && idList.size()>0){
			HashMap map = (HashMap) idList.get(0);
			id = (BigDecimal) map.get("ID");

		}
		DiagramUtil diagramUtil = new DiagramUtil();
		param.put("diagramId", id);
		return doRest(url, param.toString(), "POST");
	}
}
