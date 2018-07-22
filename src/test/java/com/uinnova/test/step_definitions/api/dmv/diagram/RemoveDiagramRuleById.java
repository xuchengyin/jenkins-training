package com.uinnova.test.step_definitions.api.dmv.diagram;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.dmv.DiagramUtil;

/**
 * @author wsl
 * 删除影响关系规则
 *
 */
public class RemoveDiagramRuleById extends RestApi{
	
	public JSONObject removeDiagramRuleById(String diagramName){
		String url = ":1511/tarsier-vmdb/dmv/diagram/removeDiagramRuleById";
		DiagramUtil du = new DiagramUtil();
		BigDecimal diagramId = du.getDiagramIdByName(diagramName);
		return doRest(url, String.valueOf(diagramId), "POST");
	}
}
