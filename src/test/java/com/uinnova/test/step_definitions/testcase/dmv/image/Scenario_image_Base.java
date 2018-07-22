package com.uinnova.test.step_definitions.testcase.dmv.image;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.dmv.diagram.QueryDiagramInfoById;

/**
 * @author wsl
 * 新建视图积累
 *
 */
public class Scenario_image_Base {
	
	/**
	 * @param diagramName
	 * @return
	 * 根据名称查看视图
	 */
	protected JSONObject getDiagramInfoByName(String diagramName){
		QueryDiagramInfoById queryDiagramInfoById  = new QueryDiagramInfoById();
		JSONObject result = queryDiagramInfoById.queryDiagramInfoById(diagramName,true);
		return result;
	}
}
